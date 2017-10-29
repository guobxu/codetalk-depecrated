package me.codetalk.flow.pofo.mapper;

import org.apache.ibatis.annotations.Param;

import me.codetalk.flow.pofo.pojo.UserFollow;

public interface UserFollowMapper {

	public void insertFollow(UserFollow follow);
	
	// 查找delete_mark = 0的记录
	public UserFollow selectFollow(@Param("userId") Integer userId, @Param("userFollow") Integer userFollow);
	
	public void markDelete(@Param("followId") Long followId);
	
}
