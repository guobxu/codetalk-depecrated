package me.codetalk.flow.solv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import me.codetalk.flow.solv.pojo.Quest;
import me.codetalk.flow.solv.pojo.QuestVO;

public interface QuestMapper {

	public void insertQuestion(Quest quest);

	public Integer selectOne(@Param("qid") Long qid);
	
	public QuestVO selectQuestVOById(@Param("qid") Long qid);
	
	public List<QuestVO> listQuest(@Param("begin") int begin, @Param("count") int count);
	
	public List<QuestVO> listQuestByTag(@Param("tagList") List<Integer> tagList, @Param("begin") int begin, @Param("count") int count);
	
	public void updateStatus(@Param("id") Long questId, @Param("status") Integer status);
	
}
