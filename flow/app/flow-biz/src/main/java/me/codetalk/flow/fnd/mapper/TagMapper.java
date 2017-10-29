package me.codetalk.flow.fnd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import me.codetalk.flow.fnd.pojo.Tag;
import me.codetalk.flow.fnd.pojo.TagVO;

public interface TagMapper {

//	public List<TagVO> selectAllTags();
	
	public List<TagVO> selectTagsByPos(@Param("posType") Integer posType);
	
	public List<Tag> selectAllSimple();
	
}
