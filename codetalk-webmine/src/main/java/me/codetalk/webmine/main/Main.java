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
import me.codetalk.webmine.page.impl.HtmlPage;
import me.codetalk.webmine.page.impl.JsonListPage;

public class Main {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) throws Exception {
		
		/**************************** StackOverflow ****************************/
		
//		ListPage listPage = new HtmlListPage("https://stackoverflow.com/questions/tagged/java?sort=votes&pageSize=15");
		
//		PageAttr attr = new PageAttr("div#questions div.summary h3 a", "href");
//		List<Page> pages = listPage.fetchPages(attr);
		
//		Map<String, PageAttr> attrMap = new HashMap<>();
//		attrMap.put("quest_title", new PageAttr("#question-header h1 a", null));
//		attrMap.put("quest_votes", new PageAttr("#question span.vote-count-post", null));
//		attrMap.put("quest_content", new PageAttr("#question div.post-text", null));
//		attrMap.put("quest_accepted", new PageAttr("#answers span.vote-accepted-on",null));
//		attrMap.put("quest_answer", new PageAttr("#answers div.accepted-answer div.post-text",null));
		
//		pages.forEach(p -> {
//			LOGGER.info(p.getUrl());
//		});
		
//		Page page = new HtmlPage("https://stackoverflow.com/questions/88235/dealing-with-java-lang-outofmemoryerror-permgen-space-error");
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
		
		Map<String, PageAttr> attrMap = new HashMap<>();
		attrMap.put("article_title", new PageAttr("article h1.article-title", null));
		attrMap.put("article_summary", new PageAttr("article div.subhead h3", null));
		attrMap.put("article_content", new PageAttr("article div.content-html", null));
		
		Page page = new HtmlPage("https://dzone.com/articles/spring-boot-with-embedded-mongodb");
		WebEntity entity = page.fetchEntity(attrMap);
		LOGGER.info(entity.toString());
	}
	
}
