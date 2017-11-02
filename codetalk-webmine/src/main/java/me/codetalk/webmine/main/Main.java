package me.codetalk.webmine.main;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.codetalk.webmine.page.ListPage;
import me.codetalk.webmine.page.Page;
import me.codetalk.webmine.page.PageAttr;
import me.codetalk.webmine.page.impl.HtmlListPage;

public class Main {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) throws Exception {
		
		/**************************** StackOverflow ****************************/
		
		ListPage listPage = new HtmlListPage("https://stackoverflow.com/questions/tagged/java?sort=votes&page=15");
		
		PageAttr attr = new PageAttr("div#questions div.summary h3 a", "href");
		List<Page> pages = listPage.fetchPages(attr);
		
//		Map<String, PageAttr> attrMap = new HashMap<>();
//		attrMap.put("quest_title", new PageAttr("#question-header h1 a", null, 1));
//		attrMap.put("quest_votes", new PageAttr("#question span.vote-count-post", null, 1));
//		attrMap.put("quest_content", new PageAttr("#question div.post-text", null, 1));
//		attrMap.put("quest_tags", new PageAttr("#question div.post-taglist a",null, 2));
//		attrMap.put("quest_accepted", new PageAttr("#answers span.vote-accepted-on",null, 1));
//		attrMap.put("quest_answer", new PageAttr("#answers div.accepted-answer div.post-text",null, 1));
//		attrMap.put("quest_top_reply", new PageAttr("#answers div.answer div.post-text",null, 3));
		
		pages.forEach(p -> {
			LOGGER.info(p.getUrl());
		});
		
//		Page page = new HtmlPage("https://stackoverflow.com/questions/40480/is-java-pass-by-reference-or-pass-by-value");
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
	}
	
}
