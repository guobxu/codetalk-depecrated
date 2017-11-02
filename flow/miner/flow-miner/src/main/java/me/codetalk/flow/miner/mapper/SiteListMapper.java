package me.codetalk.flow.miner.mapper;

import org.apache.ibatis.annotations.Param;

import me.codetalk.flow.miner.vo.SiteListVO;

public interface SiteListMapper {

	public SiteListVO selectFirstActive();
	
	public void updateListDisabled(@Param("listId") Integer listId, @Param("errorMsg") String errorMsg);

	public void incrLastPage(@Param("listId") Integer listId);
	
}
