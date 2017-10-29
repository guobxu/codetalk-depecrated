package me.codetalk.flow.pofo.service;

public interface IFollowService {

	public void follow(Integer userId, Integer userFollow);
	
	public void unfollow(Integer userId, Integer userFollow);
	
}
