package me.codetalk.flow.fnd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserTagMapper {

	public void deleteUserTags(@Param("userId") Integer userId);
	
	public void insertUserTags(@Param("userId") Integer userId, @Param("tagList") List<Integer> tagList);
	
}
