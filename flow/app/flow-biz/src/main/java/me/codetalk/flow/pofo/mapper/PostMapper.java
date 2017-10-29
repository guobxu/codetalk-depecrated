package me.codetalk.flow.pofo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import me.codetalk.flow.pofo.pojo.Post;
import me.codetalk.flow.pofo.pojo.PostVO;

public interface PostMapper {

	// 用户帖子聚合列表
	public List<PostVO> listReadByUser(@Param("userId") Integer userId, @Param("begin") Integer begin, @Param("count") Integer count);
	
	public void insertPost(Post post);
	
	public List<PostVO> selectUserPost(@Param("userId") Integer userId, @Param("begin") Integer begin, @Param("count") Integer count);
	
	public List<PostVO> selectReadList(@Param("userId") Integer userId, @Param("begin") Integer begin, @Param("count") Integer count);
	
	public Post selectPostById(@Param("postId") Long postId);
	
	public PostVO selectPostVOById(@Param("postId") Long postId);
	
	
	
}


