package me.codetalk.flow.solv.mapper;

import org.apache.ibatis.annotations.Param;

import me.codetalk.flow.solv.pojo.ReplyVote;

public interface ReplyVoteMapper {

	public void insertVote(ReplyVote vote);
	
	public void markDelete(@Param("vid") Long vid);
	
	public ReplyVote selectUserVoteByReply(@Param("userId") Integer userId, @Param("replyId") Long replyId);
	
}
