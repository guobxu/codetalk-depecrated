package me.codetalk.flow.press.mapper;

import org.apache.ibatis.annotations.Param;

import me.codetalk.flow.press.pojo.Article;

public interface ArticleMapper {

	public void insertArticle(Article article);
	
	public Integer selectOne(@Param("uuid") String uuid);
	
}
