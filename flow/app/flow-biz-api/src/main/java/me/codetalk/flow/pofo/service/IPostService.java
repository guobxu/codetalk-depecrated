package me.codetalk.flow.pofo.service;

import java.util.List;
import java.util.Map;

import me.codetalk.flow.pofo.pojo.Post;
import me.codetalk.flow.pofo.pojo.PostVO;

public interface IPostService {

	public Post addPost(Post post);

	public List<PostVO> listUserPost(Integer userId, int begin, int count);

	// 帖子聚合列表
	public List<PostVO> listReadByUser(Integer userId, int begin, int count);
	
	public Post getPostById(Long postId);
	
	public PostVO getPostVOById(Long postId);
	
	// 点赞
	public void incrLike(Long postId);
	public void decrLike(Long postId);
	
	// 转发
	public void incrRefer(Long postId);
	public void decrRefer(Long postId);
	
	// 评论
	public void incrComment(Long postId);
	public void decrComment(Long postId);
	
	// 查看
	public void incrView(Long postId);
	
	// 批量获取post的点赞数
	public Map<Long, Long> getLikes(List<Long> postIdList);
	
	// 批量获取post的转发数
	public Map<Long, Long> getRefers(List<Long> postIdList);
	
	// 批量获取post的评论数
	public Map<Long, Long> getComments(List<Long> postIdList);
	
	// 批量获取post的查看数
	public Map<Long, Long> getViews(List<Long> postIdList);
	
}
