package me.codetalk.flow.solv.service;

import java.util.List;

import me.codetalk.flow.solv.exception.SolvServiceException;
import me.codetalk.flow.solv.pojo.Comment;
import me.codetalk.flow.solv.pojo.CommentVO;

public interface ICommentService {

	public Comment getCommentById(Long cmntId);

	public List<CommentVO> listCommentVOByQuest(Long questId);
	
	public List<CommentVO> listCommentVOByReply(Long replyId);
	
	// 问题评论
	public Comment addQuestComment(Comment cmnt) throws SolvServiceException ;
	
	// 答复评论
	public Comment addReplyComment(Comment cmnt) throws SolvServiceException;
	
	/**
	 * 评论的评论
	 * 
	 * @param cmnt
	 * @param isQuest true 问题评论 false 答复评论
	 * @return
	 */
	public Comment addCoComment(Comment cmnt, boolean isQuest) throws SolvServiceException ;
	
	
}
