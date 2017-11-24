package me.codetalk.webmine.main;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.codetalk.webmine.page.ListPage;
import me.codetalk.webmine.page.Page;
import me.codetalk.webmine.page.PageAttr;
import me.codetalk.webmine.page.impl.HttpClientHtmlListPage;

public class Main {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) throws Exception {
		
		/**************************** StackOverflow ****************************/
		
//		ListPage listPage = new JsoupHtmlListPage("https://stackoverflow.com/search?tab=relevance&q=java%20concurrency&page=1");
//		
//		PageAttr attr = new PageAttr("div.search-results div.question-summary div.result-link a", "href");
//		List<Page> pages = listPage.fetchPages(attr);
//		
//		pages.forEach(p -> {
//			LOGGER.info(p.getUrl());
//		});
		
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
//		Page page = new JsoupHtmlPage("https://stackoverflow.com/questions/40836811/why-arrayblockingqueue-use-local-variable-lock");
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
		
		/**************************** mkyong ****************************/
//		ListPage listPage = new HttpClientHtmlListPage("http://www.mkyong.com/tutorials/spring-tutorials/");
//		
//		PageAttr attr = new PageAttr("article div.post-content > ul li a", "href");
//		List<Page> pages = listPage.fetchPages(attr);
//		
//		pages.forEach(p -> {
//			LOGGER.info(p.getUrl());
//		});
		
//		Map<String, PageAttr> attrMap = new HashMap<>();
//		attrMap.put("article_title", new PageAttr("div#post-container article h1", null, 4));
//		attrMap.put("article_summary", new PageAttr("div#post-container article div.post-content > p", null, 3));
//		attrMap.put("article_content", new PageAttr("div#post-container article div.post-content ", null, 1));
//		attrMap.put("article_tags", new PageAttr("div#post-container article span.post-tag", null, 1));
//		
//		Page page = new HttpClientHtmlPage("http://www.mkyong.com/spring-boot/spring-boot-hello-world-example-thymeleaf/");
//		WebEntity entity = page.fetchEntity(attrMap);
//		LOGGER.info(entity.toString());
		
//		String s = "<code style=\"language-java\">&lt;div style=\"navbar-header\"&gt;</code>";
//		s = s.replaceAll("(<.*?)style=\".*?\"(.*?>)", "$1$2");
//		System.out.println(s);
		
		/**************************** infoq ****************************/
//		Map<String, PageAttr> attrMap = new HashMap<>();
//		attrMap.put("article_title", new PageAttr("div#site div#content h1.general", null, 3));
//		attrMap.put("article_summary", new PageAttr("div#site div#content div.text_info > p", null, 3));
//		attrMap.put("article_content", new PageAttr("div#site div#content div.text_info", null, 1));
//		attrMap.put("article_tags", new PageAttr("div#site div#content div.text_info div.related ul li a.followable", null, 1));
//		
//		Page page = new HttpClientHtmlPage("https://www.infoq.com/news/2012/12/netflix-hystrix-fault-tolerance/");
//		WebEntity entity = page.fetchEntity(attrMap);
//		LOGGER.info(entity.toString());
		
		/**************************** thenewstack ****************************/
//		Map<String, PageAttr> attrMap = new HashMap<>();
//		attrMap.put("article_title", new PageAttr("#main article header.entry-header h1", null, 3));
//		attrMap.put("article_summary", new PageAttr("#main article div.entry-content div.post-content > p", null, 3));
//		attrMap.put("article_content", new PageAttr("#main article div.entry-content div.post-content", null, 1));
//		attrMap.put("article_tags", new PageAttr("#main article footer.entry-footer div.newtags", null, 1));
//		
//		Page page = new HttpClientHtmlPage("https://thenewstack.io/grpc-lean-mean-communication-protocol-microservices/");
//		WebEntity entity = page.fetchEntity(attrMap);
//		LOGGER.info(entity.toString());
		
		/**************************** jvns ****************************/
//		Map<String, PageAttr> attrMap = new HashMap<>();
//		attrMap.put("article_title", new PageAttr("#main article header h1.entry-title", null, 3));
//		attrMap.put("article_summary", new PageAttr("#main article div.entry-content > p", null, 3));
//		attrMap.put("article_content", new PageAttr("#main article div.entry-content", null, 1));
//		attrMap.put("article_tags", new PageAttr("#main article header div.post-tags", null, 1));
//		
//		Page page = new HttpClientHtmlPage("https://jvns.ca/blog/2016/11/21/things-to-learn-about-linux/");
//		WebEntity entity = page.fetchEntity(attrMap);
//		LOGGER.info(entity.toString());
		
		ListPage listPage = new HttpClientHtmlListPage("https://jvns.ca/");
		
		PageAttr attr = new PageAttr("div#main div#blog-archives table td a", "href");
		List<Page> pages = listPage.fetchPages(attr);
		
		pages.forEach(p -> {
			LOGGER.info("insert into site_pages values ('" + UUID.randomUUID().toString() + "', '" + p.getUrl() + "', 2, 3, 8, 801, null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);");
		});
		
//		for(int i = 0; i < 10; i++) {
//			System.out.println(UUID.randomUUID().toString());
//		}
		
	}
	
}


























