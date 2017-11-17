package me.codetalk.flow.press.service;

import me.codetalk.flow.press.pojo.Article;

public interface IArticleService {

	public void createArticle(Article article);
	
	public void createArticle(Article article, boolean doIndex);
	
}
