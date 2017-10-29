package me.codetalk.flow.solv.service;

import java.util.List;
import java.util.Map;

import me.codetalk.flow.solv.exception.SolvServiceException;
import me.codetalk.flow.solv.pojo.Quest;
import me.codetalk.flow.solv.pojo.QuestVO;

public interface IQuestService {

	// 发布问题
	public Quest publishQuest(Quest q, List<Integer> tags);
	
	// 存在与否
	public boolean existsById(Long qid);
	
	// 问题列表 - 无tag
	public List<QuestVO> listQuest(int begin, int count);
	
	// 问题列表 - tag
	public List<QuestVO> listQuestByTag(List<Integer> tagList, int begin, int count);
	
	// 问题详情
	public QuestVO getQuestVOById(Long questId);
	
	/**
	 * 接受答复
	 * 
	 * @param userId 当前用户
	 * @param replyId 答复ID
	 */
	public void acceptReply(Integer userId, Long replyId) throws SolvServiceException;
	
	// STAT 
	
	// 增加回复数
	public void incrReply(Long qid);
	
	// 增加评论数
	public void incrCmnt(Long qid);
	
	public Map<Long, Long> getReplies(List<Long> questIdList);
	
	public Integer getCmnt(Long questId);
	
}
