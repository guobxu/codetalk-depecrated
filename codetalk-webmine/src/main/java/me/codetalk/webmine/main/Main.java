package me.codetalk.webmine.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.codetalk.webmine.data.WebEntity;
import me.codetalk.webmine.page.ListPage;
import me.codetalk.webmine.page.Page;
import me.codetalk.webmine.page.PageAttr;
import me.codetalk.webmine.page.impl.JsoupHtmlListPage;
import me.codetalk.webmine.page.impl.JsoupHtmlPage;

public class Main {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) throws Exception {
		
		/**************************** StackOverflow ****************************/
		
		ListPage listPage = new JsoupHtmlListPage("https://stackoverflow.com/search?tab=relevance&q=java%20concurrency&page=1");
		
		PageAttr attr = new PageAttr("div.search-results div.question-summary div.result-link a", "href");
		List<Page> pages = listPage.fetchPages(attr);
		
		pages.forEach(p -> {
			LOGGER.info(p.getUrl());
		});
		
//		Map<String, PageAttr> attrMap = new HashMap<>();
//		attrMap.put("quest_title", new PageAttr("#question-header h1 a", null, 1));
//		attrMap.put("quest_votes", new PageAttr("#question span.vote-count-post", null, 1));
//		attrMap.put("quest_content", new PageAttr("#question div.post-text", null, 1));
//		attrMap.put("quest_tags", new PageAttr("#question div.post-taglist a",null, 2));
//		attrMap.put("quest_accepted", new PageAttr("#answers span.vote-accepted-on",null, 1));
//		attrMap.put("quest_answer", new PageAttr("#answers div.accepted-answer div.post-text",null, 1));
//		attrMap.put("quest_top_reply", new PageAttr("#answers div.answer div.post-text",null, 3));
//		
//		
//		
//		Page page = new JsoupHtmlPage("https://stackoverflow.com/questions/388242/the-definitive-c-book-guide-and-list/388282#388282");
//		WebEntity entity = page.fetchEntity(attrMap);
//		LOGGER.info(entity.toString());
		
		/**************************** Dzone ****************************/
		
//		ListPage listPage = new JsonListPage("https://dzone.com/services/widget/header-headerV2/nextPage?currentPage=2&maxSize=10&numPages=1&pageSize=50&term=spring+boot&totalItems=0");
//		
//		PageAttr attr = new PageAttr("result data pages newest 2", "url");
//		List<Page> pages = listPage.fetchPages(attr);
//		
//		pages.forEach(p -> {
//			LOGGER.info(p.getUrl());
//		});
		
//		Map<String, PageAttr> attrMap = new HashMap<>();
//		attrMap.put("article_title", new PageAttr("article h1.article-title", null, 1));
//		attrMap.put("article_summary", new PageAttr("article div.subhead h3", null, 1));
//		attrMap.put("article_content", new PageAttr("article div[itemprop=articleBody]", null, 1)); 
//		attrMap.put("article_tags", new PageAttr("div.article-topics span.topics-tag", null, 2));
//		
//		Page page = new HtmlPage("https://dzone.com/articles/spring-boot-with-embedded-mongodb");
//		WebEntity entity = page.fetchEntity(attrMap);
//		LOGGER.info(entity.toString());
		
		/**************************** Baeldung ****************************/
//		ListPage listPage = new HttpClientHtmlListPage("http://www.baeldung.com/rest-with-spring-series/");
//		
//		PageAttr attr = new PageAttr("section.post_content ul li a", "href");
//		List<Page> pages = listPage.fetchPages(attr);
		
//		pages.forEach(p -> {
//			LOGGER.info(p.getUrl());
//		});
		
//		article_title 		article.post div.page-header h1.entry-title 2
//		article_tags 		article.post div.page-header ul.categories li
//		article_content		article.post section.post_content	
		
//		Map<String, PageAttr> attrMap = new HashMap<>();
//		attrMap.put("article_title", new PageAttr("article.post div.page-header h1.entry-title", null, 2));
//		attrMap.put("article_content", new PageAttr("article.post section.post_content", null, 1));
//		attrMap.put("article_tags", new PageAttr("article.post div.page-header ul.categories li", null, 1));
//		
//		Page page = new HttpClientHtmlPage("http://www.baeldung.com/securing-a-restful-web-service-with-spring-security");
//		WebEntity entity = page.fetchEntity(attrMap);
//		LOGGER.info(entity.toString());
			
		/**************************** javacodegeeks ****************************/
//		ListPage listPage = new HttpClientHtmlListPage("https://www.javacodegeeks.com/2015/09/advanced-java.html");
//		
//		PageAttr attr = new PageAttr("article#the-post div.entry h3 a", "href");
//		List<Page> pages = listPage.fetchPages(attr);
//		
//		pages.forEach(p -> {
//			LOGGER.info(p.getUrl());
//		});
        
//		Map<String, PageAttr> attrMap = new HashMap<>();
//		attrMap.put("article_title", new PageAttr("article#the-post h1.post-title span", null, 2));
//		attrMap.put("article_summary", new PageAttr("article#the-post div.entry > p", null, 3));
//		attrMap.put("article_content", new PageAttr("article#the-post div.entry", null, 1));
//		attrMap.put("article_tags", new PageAttr("div#main-content div.content p.post-tag a", null, 2));
//		
//		Page page = new HttpClientHtmlPage("https://www.javacodegeeks.com/2017/11/dockerized-java-ee-8-applications-glassfish-5-0.html");
//		WebEntity entity = page.fetchEntity(attrMap);
//		LOGGER.info(entity.toString());
		
	}
	
}


























