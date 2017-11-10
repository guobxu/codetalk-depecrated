package me.codetalk.flow.solv.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.codetalk.flow.fnd.service.ITagService;
import me.codetalk.flow.fnd.stat.redis.HashStatSupport;
import me.codetalk.flow.solv.Constants;
import me.codetalk.flow.solv.elastic.DocQuest;
import me.codetalk.flow.solv.elastic.repos.QuestRepository;
import me.codetalk.flow.solv.exception.SolvServiceException;
import me.codetalk.flow.solv.mapper.QuestMapper;
import me.codetalk.flow.solv.mapper.QuestTagMapper;
import me.codetalk.flow.solv.mapper.ReplyMapper;
import me.codetalk.flow.solv.pojo.Quest;
import me.codetalk.flow.solv.pojo.QuestVO;
import me.codetalk.flow.solv.pojo.ReplyVO;
import me.codetalk.flow.solv.service.IQuestService;
import me.codetalk.flow.solv.service.IReplyService;
import me.codetalk.mesg.KeyedMessages;
import me.codetalk.messaging.Message;
import me.codetalk.messaging.kafka.aspect.annotation.KafkaAfter;
import me.codetalk.util.JsonUtils;
import me.codetalk.util.ListUtils;
import me.codetalk.util.StringUtils;

@Service
public class QuestServiceImpl extends HashStatSupport implements IQuestService {

	private static final Logger LOGGER = LoggerFactory.getLogger(QuestServiceImpl.class);
	
	@Autowired 
	private QuestMapper questMapper;
	
	@Autowired
	private QuestTagMapper qtMapper;
	
	@Autowired
	private IReplyService replyService;
	
	@Autowired
	private ReplyMapper replyMapper;
	
	@Autowired
	private ITagService tagService;
	
	@Autowired
	private QuestRepository questRepo;
	
	@Autowired
	private KeyedMessages km;
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	// Stat Cache
	private static final String CACHE_STAT_QUEST_REPLY = "STAT-QUEST-REPLY-";	// 回复
	private static final String CACHE_STAT_QUEST_CMNT = "STAT-QUEST-CMNT-";	// 评论
	
	@Transactional
	public Quest publishQuest(Quest q, List<Integer> tags) {
		q.setUuid(StringUtils.uuid());
		q.setIndexed(Constants.CONST_YES);
		questMapper.insertQuestion(q);
		
		// tags
		qtMapper.insertQuestTags(q.getId(), tags);
		
		// ES index
		DocQuest dq = quest2Doc(q, tags);
		questRepo.save(dq);
		
		// Send message
		Map<String, Object> data = new HashMap<>();
		data.put("quest", q);
		data.put("tags", tags);
		Message mesg = Message.builder().key(StringUtils.uuid())
										.app(Constants.FLOW_APP)
										.module(Constants.FLOW_MOD_SOLV)
										.data(data).build();
		kafkaTemplate.send(Constants.TOPIC_QUEST_CREATE, mesg.getKey(), JsonUtils.toJson(mesg));
		
		return q; 
	}

	@Override
	public boolean existsById(Long qid) {
		return questMapper.selectOne(qid) != null;
	}

	@Override
	public List<QuestVO> listQuest(int begin, int count) {
		List<QuestVO> questList = questMapper.listQuest(begin, count);
		
		if(questList.size() == 0) return questList;
		
		setStats(questList);
		
		return questList;
	}

	@Override
	public List<QuestVO> listQuestByTag(List<Integer> tagList, int begin, int count) {
		List<QuestVO> questList = questMapper.listQuestByTag(tagList, begin, count);
		
		if(questList.size() == 0) return questList;
		
		setStats(questList);
		
		return questList;
	}

	private void setStats(List<QuestVO> questList) {
		// reply nums
		List<Long> questIdList = new ArrayList<Long>();
		for(QuestVO quest : questList) {
			questIdList.add(quest.getId());
		}
		
		Map<Long, Long> replyNums = getReplies(questIdList);
		for(QuestVO quest : questList) {
			quest.setReplyNum(replyNums.get(quest.getId()).intValue());
		}
	}
	
	@Override
	public QuestVO getQuestVOById(Long questId) {
		QuestVO quest = questMapper.selectQuestVOById(questId);
		
		// quest cmnt num
		if(quest != null) {
			quest.setCmntNum(getCmnt(questId));
		}
		
		// reply cmnt num
		if(quest != null && quest.getReplies() != null) {
			List<ReplyVO> replies = quest.getReplies();

			// sort
			Collections.sort(replies, new Comparator<ReplyVO>() {
				@Override
				public int compare(ReplyVO r1, ReplyVO r2) {
					if(r1.getAccepted() == 1) { // TODO
						return -1;
					}
					if(r2.getAccepted() == 1) {
						return 1;
					}
					
					return r1.getCreateDate().before(r2.getCreateDate()) ? -1 : 1;
				}
			});
			
			List<Long> replyIdList = new ArrayList<Long>();
			for(ReplyVO reply : replies) {
				replyIdList.add(reply.getId());
			}
			
			// Cmnts
			Map<Long, Long> replyCmnts = replyService.getCmnts(replyIdList);
			for(ReplyVO reply : replies) {
				reply.setCmntNum(replyCmnts.get(reply.getId()).intValue());
			}
			
			// Votes
			Map<Long, Long> replyVotes = replyService.getVotes(replyIdList);
			for(ReplyVO reply : replies) {
				reply.setVoteNum(replyVotes.get(reply.getId()).intValue());
			}
		}
		
		return quest;
	}
	
	@Override
	@Transactional
	public void acceptReply(Integer userId, Long replyId) throws SolvServiceException {
		ReplyVO reply = replyService.getReplyVOById(replyId);
		if(reply == null) {
			throw new SolvServiceException(km.get("solv_err_replynotfound"));
		}
		
		if(!userId.equals(reply.getQuestCreateBy())) {
			throw new SolvServiceException(km.get("solv_err_user_accept"));
		}
		
		if(reply.getQuestStatus() == Constants.QUEST_STATUS_SOLVED) {
			throw new SolvServiceException(km.get("solv_err_quest_solved"));
		}

		questMapper.updateStatus(reply.getQuestId(), Constants.QUEST_STATUS_SOLVED);
		replyMapper.updateAccepted(replyId);
	}
	
	@Override
	@Transactional
	public void markSpam(String quuid, Integer markBy, String reason) {
		questMapper.markSpam(quuid, markBy, reason);
		
		questRepo.delete(quuid);
		
		// TODO: notice
	}
	
	/**
	 * 转换为Doc Quest作为索引
	 * 
	 * @param q
	 * @param tags
	 * @return
	 */
	private DocQuest quest2Doc(Quest q, List<Integer> tags) {
		DocQuest dq = new DocQuest();
		dq.setQuestId(q.getId());
		dq.setUuid(q.getUuid());
		dq.setTitle(q.getTitle());
		dq.setContent(q.getContent());
		
		// tags
		String tagstr = ListUtils.concat(tagService.getTagNamesByIdList(tags), " ");
		dq.setTags(tagstr);
		dq.setVotes(0);
		// dq.setExtUrl(q.getUrl());
		dq.setAccepted(Constants.CONST_NO);
		dq.setCreateDate(new Timestamp(System.currentTimeMillis()));
		
		return dq;
	}
	
	// STAT
	@Override
	public Map<Long, Long> getReplies(List<Long> questIdList) {
		assert questIdList != null && questIdList.size() > 0;
		
		return getStats(questIdList, CACHE_STAT_QUEST_REPLY);
	}
	
	@Override
	public void incrReply(Long qid) {
		incrStatBy(qid, CACHE_STAT_QUEST_REPLY, 1);
	}

	@Override
	public void incrCmnt(Long qid) {
		incrStatBy(qid, CACHE_STAT_QUEST_CMNT, 1);
	}
	
//	@Override
//	public Integer getHashRange(String statType) {
//		return Constants.CACHE_STAT_QUEST_RANGE;
//	}

	@Override
	public Integer getCmnt(Long questId) {
		return getStat(questId, CACHE_STAT_QUEST_CMNT).intValue();
	}

	
}



















