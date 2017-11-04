package me.codetalk.flow.solv.mapper;

import org.apache.ibatis.annotations.Param;

import me.codetalk.flow.solv.pojo.ExtQuest;

public interface ExtQuestMapper {

	public void insertExtQuest(ExtQuest quest);
	
	public void updateExtQuestIndexed(@Param("id") String id);
	
	public Integer selectOne(@Param("id") String id);
	
}
