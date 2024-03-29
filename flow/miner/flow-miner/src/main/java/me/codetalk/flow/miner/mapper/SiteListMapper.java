package me.codetalk.flow.miner.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import me.codetalk.flow.miner.vo.SiteListVO;

public interface SiteListMapper {

	public SiteListVO selectFirstActive();
	
	public void updateListDisabled(@Param("listId") Integer listId, @Param("errorMsg") String errorMsg);

	public void incrLastPage(@Param("listId") Integer listId);
	
	public List<SiteListVO> selectFirstActiveListBySite();
	
	public List<SiteListVO> selectActiveLists();
	
	public Integer selectListEnabled(@Param("listId") Integer listId);
	
}
