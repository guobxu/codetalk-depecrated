package me.codetalk.flow.miner.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import me.codetalk.flow.miner.pojo.SitePage;

public interface SitePageMapper {

	public void insertPages(@Param("pageList") List<SitePage> pageList);
	
	public void updatePageStatus(SitePage page);
	
	public List<String> selectUrlIn(@Param("urlList") List<String> urlList);
	
	
	
}
