package me.codetalk.flow.solv.mapper;

import org.apache.ibatis.annotations.Param;

import me.codetalk.flow.solv.pojo.Reply;
import me.codetalk.flow.solv.pojo.ReplyVO;

public interface ReplyMapper {

	public void insertReply(Reply reply);

	public Reply selectReplyById(Long id);
	
	public ReplyVO selectReplyVOById(@Param("id") Long id);
	
	public void updateAccepted(@Param("id") Long replyId);
	
}
