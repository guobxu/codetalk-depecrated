package me.codetalk.flow.solv.service;

import me.codetalk.flow.solv.pojo.ReplyVote;

public interface IReplyVoteService {

	public void addVote(ReplyVote vote);
	
	public void markDelete(Long voteId);
	
	public ReplyVote getReplyVote(Integer userId, Long replyId);
	
}
