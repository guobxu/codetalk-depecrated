package me.codetalk.flow.solv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface QuestTagMapper {

	public void insertQuestTags(@Param("qid") Long qid, @Param("tags") List<Integer> tags);
	
}
