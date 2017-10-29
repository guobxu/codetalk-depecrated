package me.codetalk.flow.pofo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.codetalk.flow.pofo.mapper.UserFollowMapper;
import me.codetalk.flow.pofo.pojo.UserFollow;
import me.codetalk.flow.pofo.service.IFollowService;

@Service("followService")
public class FollowServiceImpl implements IFollowService {

	@Autowired
	private UserFollowMapper followMapper;
	
	@Override
	public void follow(Integer userId, Integer userFollow) {
		UserFollow follow = followMapper.selectFollow(userId, userFollow);
		if(follow != null) return;
		
		// TODO: 避免多条delete_mark = 0的重复数据
		UserFollow followAdd = new UserFollow();
		followAdd.setUserId(userId);
		followAdd.setUserFollowed(userFollow);
		
		followMapper.insertFollow(followAdd);
	}

	@Override
	public void unfollow(Integer userId, Integer userFollow) {
		UserFollow follow = followMapper.selectFollow(userId, userFollow);
		if(follow == null) return;
		
		followMapper.markDelete(follow.getId());
	}

}
