package me.codetalk.flow.solv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import me.codetalk.flow.solv.pojo.Comment;
import me.codetalk.flow.solv.pojo.CommentVO;

public interface QuestCmntMapper {

	public int insertComment(Comment cmnt);

	public Comment selectCommentById(Long cmntId);
	
	public List<CommentVO> selectCommentVOByQuest(@Param("questId") Long questId);
	
	public List<CommentVO> selectCommentVOByReply(@Param("replyId") Long replyId);
	
}
