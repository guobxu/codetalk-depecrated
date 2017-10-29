package me.codetalk.flow.pofo.mapper;

import org.apache.ibatis.annotations.Param;

import me.codetalk.flow.pofo.pojo.PostLike;

public interface PostLikeMapper {

	public void insertPostLike(PostLike like);
	
	// 查找delete_mark = 0的记录
	public PostLike selectPostLike(@Param("postId") Long postId, @Param("userId") Integer userId);
	
	public void markDelete(@Param("likeId") Long likeId);
	
}
