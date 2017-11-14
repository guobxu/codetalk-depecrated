package me.codetalk.flow.press.mapper;

import org.apache.ibatis.annotations.Param;

import me.codetalk.flow.press.pojo.Article;

public interface ArticleMapper {

	public void insertArticle(Article article);
	
	public Integer selectOneByUuid(@Param("uuid") String uuid);
	
	public Integer selectOneByUrl(@Param("url") String url);
	
}
