package me.codetalk.flow.pofo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.codetalk.flow.pofo.mapper.PostLikeMapper;
import me.codetalk.flow.pofo.pojo.PostLike;
import me.codetalk.flow.pofo.service.IPostLikeService;
import me.codetalk.flow.pofo.service.IPostService;

@Service("postLikeService")
public class PostLikeServiceImpl implements IPostLikeService {

	@Autowired
	private PostLikeMapper postLikeMapper;
	
	@Autowired
	private IPostService postService;
	
	@Transactional
	public void like(Long postId, Integer userId) {
		PostLike like = postLikeMapper.selectPostLike(postId, userId);
		if(like != null) return;
		
		// TODO: 避免多条delete_mark = 0的重复数据
		PostLike likeAdd = new PostLike();
		likeAdd.setPostId(postId);
		likeAdd.setUserId(userId);
		
		postLikeMapper.insertPostLike(likeAdd);
		
		postService.incrLike(postId);
	}

	@Transactional
	public void unlike(Long postId, Integer userId) {
		PostLike like = postLikeMapper.selectPostLike(postId, userId);
		if(like == null) return;
		
		postLikeMapper.markDelete(like.getId());
		
		postService.decrLike(postId);
	}

}












