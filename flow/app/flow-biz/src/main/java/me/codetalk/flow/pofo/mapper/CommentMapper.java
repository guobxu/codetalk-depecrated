package me.codetalk.flow.pofo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import me.codetalk.flow.pofo.pojo.Comment;
import me.codetalk.flow.pofo.pojo.CommentVO;

public interface CommentMapper {

	public int insertComment(Comment cmnt);
	
	public Comment selectComment(@Param("cmntId") Long cmntId);
	
	// 查询帖子下面直接回复的评论
	public List<CommentVO> listCommentByPost(@Param("postId") Long postId, @Param("begin") int begin, @Param("count") int count);
	
	// 查询第一层评论的回复
	public List<CommentVO> listCommentByThread(@Param("thread") String thread, @Param("begin") int begin, @Param("count") int count);
	
}
