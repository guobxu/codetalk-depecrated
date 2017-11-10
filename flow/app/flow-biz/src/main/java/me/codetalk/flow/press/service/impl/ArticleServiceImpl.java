package me.codetalk.flow.press.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.codetalk.flow.press.elastic.DocArticle;
import me.codetalk.flow.press.elastic.repos.ArticleRepository;
import me.codetalk.flow.press.mapper.ArticleMapper;
import me.codetalk.flow.press.pojo.Article;
import me.codetalk.flow.press.service.IArticleService;
import me.codetalk.flow.solv.Constants;
import me.codetalk.messaging.Message;
import me.codetalk.util.JsonUtils;
import me.codetalk.util.ListUtils;
import me.codetalk.util.StringUtils;

@Service("articleService")
public class ArticleServiceImpl implements IArticleService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ArticleServiceImpl.class);
	
	@Autowired
	private ArticleMapper articleMapper;
	
	@Autowired
	private ArticleRepository articleRepo;
	
	@Override
	@Transactional
	public void createArticle(Article article) {
		LOGGER.info("=======================> Create article, uuid=" + article.getUuid());
		
		String uuid = article.getUuid();
		if(articleMapper.selectOne(uuid) != null) {
			LOGGER.info("Article with [uuid = " + uuid + "] already exists!");
			
			return;
		}
		
		article.setIndexed(Constants.CONST_YES);
		articleMapper.insertArticle(article);

		articleRepo.save(article2Doc(article));
	}
	

	
	// KAFKA
	
	@KafkaListener(topics = "webminer-dzone-article", groupId = "flow-article-dzone-article-migrate")
	public void dzArticleMigrate(String msgstr) {
		LOGGER.info("In dzArticleMigrate...Receive mesg data = " + msgstr);
		
		Message mesg = (Message)JsonUtils.fromJson(msgstr, Message.class);
		Map<String, Object> data = (Map<String, Object>)mesg.getData();
		
		// attr list -> map
		List attrs = (List)data.get("attrs");
		Map<String, String> attrsMap = new HashMap<>();
		for(Object attrObj : attrs) {
			Map<String, Object> attrMap = (Map<String, Object>)attrObj;
			
			String key = (String)attrMap.get("key"), val = (String)attrMap.get("val");
			if("article_content".equals(key) && StringUtils.isNull(val)) return;
			
			attrsMap.put(key, val);
		}
		
		Article article = new Article();
		article.setUuid(mesg.getKey());
		article.setUrl((String)data.get("pageUrl"));
		article.setSite((String)data.get("site"));
		article.setTitle(attrsMap.get("article_title"));
		article.setSummary(attrsMap.get("article_summary"));
		
		String articleContent = attrsMap.get("article_content");
		articleContent = articleContent.replaceAll("style=\".*?\"", "");
		articleContent = articleContent.replaceAll("class=\".*?\"", "");
		article.setContent(articleContent);
		
		String tagstr = StringUtils.toString(attrsMap.get("article_tags"), true).trim();
		if(StringUtils.isNull(tagstr)) {
			article.setTags("");
		} else {
			String[] tagarr = tagstr.split(" *, *");
			for(int i = 0; i < tagarr.length; i++) {
				tagarr[i] = tagarr[i].replaceAll(" ", "-");
			}
			article.setTags(ListUtils.concat(tagarr, " "));
		}
		
		createArticle(article);
	}
	
	@KafkaListener(topics = "webminer-javacodegeeks-article", groupId = "flow-article-javacodegeeks-article-migrate")
	public void jcgArticleMigrate(String msgstr) {
		LOGGER.info("In jcgArticleMigrate...Receive mesg data = " + msgstr);
		
		Message mesg = (Message)JsonUtils.fromJson(msgstr, Message.class);
		Map<String, Object> data = (Map<String, Object>)mesg.getData();
		
		// attr list -> map
		Map<String, String> attrsMap = attrsToMap((List)data.get("attrs"));
		
		Article article = new Article();
		article.setUuid(mesg.getKey());
		article.setUrl((String)data.get("pageUrl"));
		article.setSite((String)data.get("site"));
		article.setTitle(attrsMap.get("article_title"));
		
		String summary = attrsMap.get("article_summary");
		article.setSummary(summary.length() > 300 ? summary.substring(0, 300) : summary);
		
		String articleContent = attrsMap.get("article_content");
		Document doc = Jsoup.parse(articleContent);
		articleContent = removeHtmlEls(doc, new String[]{
				"div.e3lan-post",
				"form#mc4wp-form-1",
				"twitterwidget",
				"div.attribution"
		});
		articleContent = articleContent.replaceAll("style=\".*?\"", "");
		articleContent = articleContent.replaceAll("class=\".*?\"", "");
		articleContent = articleContent.replaceAll("’", "'");
		article.setContent(articleContent);
		
		String tagstr = StringUtils.toString(attrsMap.get("article_tags"), true).trim();
		if(StringUtils.isNull(tagstr)) {
			article.setTags("");
		} else {
			article.setTags(extractCommonArticleTag(tagstr));
		}
		
		createArticle(article);
	}
	
	@KafkaListener(topics = "webminer-baeldung-article", groupId = "flow-article-baeldung-article-migrate")
	public void baelArticleMigrate(String msgstr) {
		LOGGER.info("In baelArticleMigrate...Receive mesg data = " + msgstr);
		
		Message mesg = (Message)JsonUtils.fromJson(msgstr, Message.class);
		Map<String, Object> data = (Map<String, Object>)mesg.getData();
		
		// attr list -> map
		Map<String, String> attrsMap = attrsToMap((List)data.get("attrs"));
		
		Article article = new Article();
		article.setUuid(mesg.getKey());
		article.setUrl((String)data.get("pageUrl"));
		article.setSite((String)data.get("site"));
		article.setTitle(attrsMap.get("article_title"));
		
		String articleContent = attrsMap.get("article_content");
		Document doc = Jsoup.parse(articleContent);
		articleContent = removeHtmlEls(doc, new String[]{
				"p.lead",
				"div.short_box.short_start",
				"div.short_box.short_end",
				"span#tho-end-content",
				"span#tve_leads_end_content",
				"div.tve-leads-post-footer"
		});
		articleContent = articleContent.replaceAll("style=\".*?\"", "");
		articleContent = articleContent.replaceAll("class=\".*?\"", "");
		articleContent = articleContent.replaceAll("’", "'");
		article.setContent(articleContent);
		
		// summary 
		String summary = null;
		
		Element ovEl = doc.select("h2#overview").first();
		if(ovEl != null) {
			Element sumEl = ovEl.nextElementSibling();
			if(sumEl != null) summary = sumEl.html();
		}
		
		if(summary != null) article.setSummary(summary);
		
		// tags
		String tagstr = StringUtils.toString(attrsMap.get("article_tags"), true).trim();
		if(StringUtils.isNull(tagstr)) {
			article.setTags("");
		} else {
			article.setTags(extractCommonArticleTag(tagstr));
		}
		
		createArticle(article);
	}
	
	private String extractCommonArticleTag(String rawstr) {
		Pattern ptrn = Pattern.compile("<a .*?>(.*?)</a>");
		Matcher m = ptrn.matcher(rawstr);
		
		StringBuffer tagbuf = new StringBuffer();
		while(m.find()) {
			String tag = m.group(1).trim().replaceAll(" +", "-");
			tagbuf.append(tagbuf.length() > 0 ? " " + tag : tag);
		}
		
		return tagbuf.toString();
	}
	
	private String removeHtmlEls(Document doc, String[] els) {
		for(String el : els) {
			Elements elements = doc.select(el);
			elements.remove();
		}
		
		return doc.select("body").html();
	}
	
	private Map<String, String> attrsToMap(List attrs) {
		Map<String, String> attrsMap = new HashMap<>();
		for(Object attrObj : attrs) {
			Map<String, Object> attrMap = (Map<String, Object>)attrObj;
			
			String key = (String)attrMap.get("key"), val = (String)attrMap.get("val");
			attrsMap.put(key, val);
		}
		
		return attrsMap;
	}
	
	private DocArticle article2Doc(Article article) {
		DocArticle da = new DocArticle();
		da.setArticleId(article.getId());
		da.setUuid(article.getUuid());
		da.setTitle(article.getTitle());
		da.setSite(article.getSite());
		da.setUrl(article.getUrl());
		da.setSummary(article.getSummary());
		da.setContent(article.getContent());
		da.setTags(article.getTags());
		
		da.setCreateDate(new Timestamp(System.currentTimeMillis()));
		
		return da;
	}



	
}