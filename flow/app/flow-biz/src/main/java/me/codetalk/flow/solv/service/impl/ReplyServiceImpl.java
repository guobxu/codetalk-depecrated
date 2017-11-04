package me.codetalk.flow.solv.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.codetalk.mesg.KeyedMessages;
import me.codetalk.messaging.kafka.aspect.annotation.KafkaAfter;
import me.codetalk.flow.fnd.stat.redis.HashStatSupport;
import me.codetalk.flow.solv.Constants;
import me.codetalk.flow.solv.exception.SolvServiceException;
import me.codetalk.flow.solv.mapper.ReplyMapper;
import me.codetalk.flow.solv.pojo.Reply;
import me.codetalk.flow.solv.pojo.ReplyVO;
import me.codetalk.flow.solv.pojo.ReplyVote;
import me.codetalk.flow.solv.service.IQuestService;
import me.codetalk.flow.solv.service.IReplyService;
import me.codetalk.flow.solv.service.IReplyVoteService;
import me.codetalk.util.StringUtils;

@Service
public class ReplyServiceImpl extends HashStatSupport implements IReplyService {

	@Autowired
	private ReplyMapper replyMapper;
	
	@Autowired
	private IQuestService questService;
	
	@Autowired
	private IReplyVoteService rvService;
	
	@Autowired
	private KeyedMessages km;
	
	// Stat Cache
	private static final String CACHE_STAT_QUEST_REPLY_CMNT = "STAT-QUEST-REPLY-CMNT-";	// 问题回复的评论
	
	private static final String CACHE_STAT_QUEST_REPLY_VOTE = "STAT-QUEST-REPLY-VOTE-";	// 问题回复的vote
	
	@Transactional
	@KafkaAfter(value = "flow-quest-reply-create", app = "flow", module = "solv")
	public Reply addReply(Reply reply) {
		// quest exists TODO
		reply.setCommentThread(StringUtils.uuid());
		
		replyMapper.insertReply(reply);
		
		questService.incrReply(reply.getQuestId());
		
		return reply;
	}

	@Override
	public Reply getReplyById(Long id) {
		return replyMapper.selectReplyById(id);
	}
	
	@Override
	public ReplyVO getReplyVOById(Long id) {
		return replyMapper.selectReplyVOById(id);
	}
	
	@Override
	@Transactional
	public void voteReply(Integer userId, Long replyId, Integer actionType) throws SolvServiceException {
		Reply reply = getReplyById(replyId);
		if(reply == null) {
			throw new SolvServiceException(km.get("solv_err_replynotfound"));
		}
		
		if(userId.equals(reply.getUserId())) {
			throw new SolvServiceException(km.get("solv_err_vote_self"));
		}
		
		ReplyVote vote = rvService.getReplyVote(userId, replyId);
		if(vote == null) {
			vote = new ReplyVote();
			vote.setUserId(userId);
			vote.setReplyId(replyId);
			
			if(actionType == Constants.ACTION_VOTE_UP) {
				vote.setUp(1);
				vote.setDown(0);
			} else if(actionType == Constants.ACTION_VOTE_DOWN) {
				vote.setUp(0);
				vote.setDown(1);
			} else {
				throw new SolvServiceException(km.get("solv_err_vote_action"));
			}
			
			rvService.addVote(vote);
			incrVoteBy(replyId, actionType == Constants.ACTION_VOTE_UP ? 1 : -1);
		} else {
			if(actionType == Constants.ACTION_VOTE_CANCEL) {
				rvService.markDelete(vote.getId());
				
				incrVoteBy(replyId, vote.getUp() == 1 ? -1 : 1);
			} else {
				throw new SolvServiceException(km.get("solv_err_vote_action"));
			}
		}
	}
	
	// STAT
	
	@Override
	public void incrCmnt(Long rid) {
		incrStatBy(rid, CACHE_STAT_QUEST_REPLY_CMNT, 1);
	}

	@Override
	public Map<Long, Long> getCmnts(List<Long> replyIdList) {
		return getStats(replyIdList, CACHE_STAT_QUEST_REPLY_CMNT);
	}

	@Override
	public void incrVoteBy(Long rid, long delta) {
		incrStatBy(rid, CACHE_STAT_QUEST_REPLY_VOTE, delta);
	}

	@Override
	public Map<Long, Long> getVotes(List<Long> replyIdList) {
		return getStats(replyIdList, CACHE_STAT_QUEST_REPLY_VOTE);
	}
	
}















