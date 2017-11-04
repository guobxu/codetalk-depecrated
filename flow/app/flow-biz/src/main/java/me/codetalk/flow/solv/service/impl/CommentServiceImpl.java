package me.codetalk.flow.solv.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.codetalk.mesg.KeyedMessages;
import me.codetalk.messaging.kafka.aspect.annotation.KafkaAfter;
import me.codetalk.flow.solv.exception.SolvServiceException;
import me.codetalk.flow.solv.mapper.QuestCmntMapper;
import me.codetalk.flow.solv.pojo.Comment;
import me.codetalk.flow.solv.pojo.CommentVO;
import me.codetalk.flow.solv.pojo.Reply;
import me.codetalk.flow.solv.service.ICommentService;
import me.codetalk.flow.solv.service.IQuestService;
import me.codetalk.flow.solv.service.IReplyService;

@Service
public class CommentServiceImpl implements ICommentService {

	@Autowired
	private QuestCmntMapper cmntMapper;
	
	@Autowired
	private IReplyService replyService;
	
	@Autowired
	private IQuestService questService;
	
	@Autowired
	private KeyedMessages km;
	
	@Override
	public List<CommentVO> listCommentVOByQuest(Long questId) {
		return cmntMapper.selectCommentVOByQuest(questId);
	}

	@Override
	public List<CommentVO> listCommentVOByReply(Long replyId) {
		return cmntMapper.selectCommentVOByReply(replyId);
	}
	
	@Override
	@Transactional
	@KafkaAfter(value = "flow-quest-comment-create", app = "flow", module = "solv")
	public Comment addQuestComment(Comment cmnt) {
		assert cmnt.getQuestId() != null 
				&& cmnt.getReplyId() == null 
				&& cmnt.getCommentReply() == null;
		
		cmntMapper.insertComment(cmnt);
		
		// stat: incr cmnt
		questService.incrCmnt(cmnt.getQuestId());
		
		return cmnt;
	}

	@Override
	@Transactional
	@KafkaAfter(value = "flow-quest-comment-create", app = "flow", module = "solv")
	public Comment addReplyComment(Comment cmnt) throws SolvServiceException {
		Long replyId = cmnt.getReplyId();
		
		// reply
		Reply reply = replyService.getReplyById(replyId);
		if(reply == null) {
			throw new SolvServiceException(km.get("solv_err_replynotfound"));
		}
		
		cmnt.setQuestId(reply.getQuestId());
		cmnt.setThread(reply.getCommentThread());
		
		cmntMapper.insertComment(cmnt);
		
		// stat: incr cmnt
		replyService.incrCmnt(replyId);
		
		return cmnt;
	}

	@Override
	@Transactional
	@KafkaAfter(value = "flow-quest-comment-create", app = "flow", module = "solv")
	public Comment addCoComment(Comment cmnt, boolean isQuest) throws SolvServiceException {
		Long cmntReplyId = cmnt.getCommentReply();
		
		// comment
		Comment cmntReply = getCommentById(cmntReplyId);
		if(cmntReply == null) {
			throw new SolvServiceException(km.get("solv_err_cmntnotfound"));
		}
		
		Long qid = cmntReply.getQuestId();
		cmnt.setQuestId(qid);
		
		if(isQuest) {
			questService.incrCmnt(qid);
		} else {
			cmnt.setThread(cmntReply.getThread());
			replyService.incrCmnt(cmntReply.getReplyId());
		}
		
		cmntMapper.insertComment(cmnt);
		
		return cmnt;
	}

	@Override
	public Comment getCommentById(Long cmntId) {
		return cmntMapper.selectCommentById(cmntId);
	}

}












