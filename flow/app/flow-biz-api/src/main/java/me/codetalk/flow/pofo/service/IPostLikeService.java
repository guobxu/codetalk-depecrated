package me.codetalk.flow.pofo.service;

public interface IPostLikeService {

	public void like(Long postId, Integer userId);
	
	public void unlike(Long postId, Integer userId);
	
}
