package me.codetalk.flow.miner.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import me.codetalk.flow.miner.pojo.WebEntity;
import me.codetalk.flow.miner.pojo.WebEntityVO;

public interface WebEntityMapper {

	public void insertEntity(WebEntity entity);
	
	public List<WebEntityVO> selectEntity(@Param("begin") Integer begin, @Param("count") Integer count);
	
}
