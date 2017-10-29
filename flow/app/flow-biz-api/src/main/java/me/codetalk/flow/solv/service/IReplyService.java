package me.codetalk.flow.solv.service;

import java.util.List;
import java.util.Map;

import me.codetalk.flow.solv.exception.SolvServiceException;
import me.codetalk.flow.solv.pojo.Reply;
import me.codetalk.flow.solv.pojo.ReplyVO;

public interface IReplyService {

	public Reply addReply(Reply reply);
	
	public Reply getReplyById(Long id);
	
	public ReplyVO getReplyVOById(Long id);
	
	/**
	 * 
	 * @param userId 当前用户id
	 * @param replyId 答复id
	 * @param actionType 1 up 2 down 3 取消
	 */
	public void voteReply(Integer userId, Long replyId, Integer actionType) throws SolvServiceException;
	
	// STAT 
	// 增加评论数
	public void incrCmnt(Long rid);
	
	// 增加vote数
	public void incrVoteBy(Long rid, long delta);
	
	public Map<Long, Long> getCmnts(List<Long> replyIdList);
	
	public Map<Long, Long> getVotes(List<Long> replyIdList);
	
}
