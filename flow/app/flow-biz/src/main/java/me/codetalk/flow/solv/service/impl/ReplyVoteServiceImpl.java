package me.codetalk.flow.solv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.codetalk.flow.solv.mapper.ReplyVoteMapper;
import me.codetalk.flow.solv.pojo.ReplyVote;
import me.codetalk.flow.solv.service.IReplyVoteService;

@Service("replyVoteService")
public class ReplyVoteServiceImpl implements IReplyVoteService {

	@Autowired 
	private ReplyVoteMapper rvMapper;
	
	@Override
	public void addVote(ReplyVote vote) {
		rvMapper.insertVote(vote);
	}

	@Override
	public void markDelete(Long voteId) {
		rvMapper.markDelete(voteId);
	}

	@Override
	public ReplyVote getReplyVote(Integer userId, Long replyId) {
		return rvMapper.selectUserVoteByReply(userId, replyId);
	}

}
