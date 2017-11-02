package me.codetalk.flow.miner.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import me.codetalk.flow.miner.pojo.WebEntityAttr;

public interface WebEntityAttrMapper {

	public void insertAttrList(@Param("attrList") List<WebEntityAttr> attrList);
	
}
