package me.codetalk.flow.fnd.service;

import java.util.List;

import me.codetalk.flow.fnd.pojo.TagVO;

public interface ITagService {

//	public List<TagVO> getAllTags(); 
	
	public List<TagVO> getTagsByPosType(Integer posType);
	
	public List<Integer> getTagIdByText(List<String> tagList, boolean ignoreCase);
	
	public List<String> getTagNamesByIdList(List<Integer> tagIdList);
	
}
