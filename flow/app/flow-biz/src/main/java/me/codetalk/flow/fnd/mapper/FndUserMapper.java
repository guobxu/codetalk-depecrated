package me.codetalk.flow.fnd.mapper;

import org.apache.ibatis.annotations.Param;

import me.codetalk.flow.fnd.pojo.FndUser;

public interface FndUserMapper {

	public void insertUser(FndUser user);
	
	public FndUser selectUserById(@Param("userId") Integer userId);
	
	public void updateUser(FndUser user);
	
}
