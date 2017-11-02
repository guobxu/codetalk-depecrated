package me.codetalk.flow.miner.runner;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import me.codetalk.flow.miner.Constants;
import me.codetalk.flow.miner.mapper.SiteListMapper;
import me.codetalk.flow.miner.mapper.SitePageMapper;
import me.codetalk.flow.miner.mapper.WebEntityAttrMapper;
import me.codetalk.flow.miner.mapper.WebEntityMapper;
import me.codetalk.flow.miner.pojo.SiteEntityAttr;
import me.codetalk.flow.miner.pojo.SitePage;
import me.codetalk.flow.miner.pojo.WebEntity;
import me.codetalk.flow.miner.pojo.WebEntityAttr;
import me.codetalk.flow.miner.pojo.WebEntityVO;
import me.codetalk.flow.miner.vo.SiteListVO;
import me.codetalk.messaging.kafka.IMessagingService;
import me.codetalk.webmine.page.ListPage;
import me.codetalk.webmine.page.Page;
import me.codetalk.webmine.page.PageAttr;
import me.codetalk.webmine.page.impl.HtmlListPage;
import me.codetalk.webmine.page.impl.HtmlPage;
import me.codetalk.webmine.page.impl.JsonListPage;

@Component
public class MinerRunner implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(MinerRunner.class);
	
	@Autowired
	private IMessagingService messagingService;
	
	@Autowired
	private SiteListMapper slmapper;
	
	@Autowired
	private SitePageMapper spmapper;
	
	@Autowired
	private WebEntityMapper wemapper;
	
	@Autowired
	private WebEntityAttrMapper weamapper;
	
	private final long sleepInit = 60 * 1000L;	// 休眠, 避免429 - too many requests
	
	private long sleepMillis = sleepInit;
	
	private Random rand = new Random();
	
	@Override
	public void run(String... args) throws Exception {
		// 1. choose active list
		SiteListVO list = null;
		while(true) {
			list = slmapper.selectFirstActive();
			if(list == null) return;
			
			processList(list);
		}

	}
	
	private void processList(SiteListVO list) {
		while(true) {
			// 2. fetch sub pages
			String url = list.getDerivedUrl(); // derived url
			
			LOGGER.info("===============> Start to process list url: " + url);
			
			Integer type = list.getType();
			ListPage listPage = null;
			if(type == Constants.LIST_TYPE_HTML) {
				listPage = new HtmlListPage(url);
			} else if(type == Constants.LIST_TYPE_JSON) {
				listPage = new JsonListPage(url);
			} else {
				throw new IllegalArgumentException("Err list type");
			}
			
			PageAttr pa = new PageAttr(list.getDerivedEl(), list.getPagesAttr());
			List<Page> pages = null;
			try {
				pages = listPage.fetchPages(pa);
				
				try {
					Thread.sleep(rand.nextInt(30) * 1000);
				} catch(InterruptedException iex) {
					LOGGER.error(iex.getMessage(), iex);
				}
			} catch(IOException ioe) {
				LOGGER.error(ioe.getMessage(), ioe);
				LOGGER.info("===============> Error fetch sub pages of url: " + url);
				
				// retry later
				try {
					Thread.sleep(sleepMillis);
					sleepMillis *= 2;
				} catch(InterruptedException ex) {
					LOGGER.error(ex.getMessage(), ex);
				}
				
				continue;
			}
			
			sleepMillis = sleepInit;
			
			if(pages.isEmpty()) {
				slmapper.updateListDisabled(list.getId(), "No sub page found!");
				
				return;
			}
			
			processPages(pages, list.getSiteId(), list.getSiteName(), list.getEntityTypeId(), list.getEntityType(), list.getEntityAttrs());
			
			if(list.getPageParam() == null) {	// 非分页, 直接返回
				return;
			}  else {
				slmapper.incrLastPage(list.getId());
				list.setLastPage(list.getLastPage() + 1);
			}
		}
		
	}
	
	private void processPages(List<Page> pages, Integer siteId, String siteName, Integer entityTypeId, String entityType, List<SiteEntityAttr> entityAttrs) {
		// 3. insert site pages
		// 3-1. filter 
		List<String> urlList = new ArrayList<String>();
		for(Page page : pages) urlList.add(page.getUrl());
		
		List<String> existingUrls = spmapper.selectUrlIn(urlList);

		List<SitePage> sitePages = new ArrayList<SitePage>();
		for(Page page : pages) {
			if(existingUrls.contains(page.getUrl())) continue;
			
			SitePage sp = new SitePage();
			sp.setId(UUID.randomUUID().toString());
			sp.setUrl(page.getUrl());
			
			sitePages.add(sp);
		}
		
		// 4. fetch page
		if(sitePages.isEmpty()) return;
		
		spmapper.insertPages(sitePages);
		
		// 4.1 conver attr map
		Map<String, PageAttr> attrMap = new HashMap<String, PageAttr>();
		for(SiteEntityAttr attr : entityAttrs) {
			PageAttr pageAttr = new PageAttr(attr.getEl(), attr.getName(), attr.getType());
			attrMap.put(attr.getKey(), pageAttr);
		}
		
		for(SitePage sp : sitePages) {
			String pageUrl = sp.getUrl();
			LOGGER.info("===============> Start to process page url: " + pageUrl);
			
			Page page = new HtmlPage(pageUrl);
			try {
				me.codetalk.webmine.data.WebEntity we = page.fetchEntity(attrMap);
				
				// sleep a while
				try {
					Thread.sleep(rand.nextInt(45) * 1000);
				} catch(InterruptedException iex) {
					LOGGER.error(iex.getMessage(), iex);
				}
				
				// reset sleep millis
				sleepMillis = sleepInit;
				
				// 4.2 convert & insert web entity
				WebEntity entity = new WebEntity();
				entity.setEntityTypeId(entityTypeId);
				entity.setPageUrl(pageUrl);
				entity.setSiteId(siteId);
				
				wemapper.insertEntity(entity);
				
				List<WebEntityAttr> attrs = new ArrayList<WebEntityAttr>();
				we.getAttrs().forEach((k, v) -> {
					WebEntityAttr attr = new WebEntityAttr();
					attr.setKey(k);
					attr.setVal(v);
					attr.setEntityId(entity.getId());
					
					attrs.add(attr);
				});
				
				weamapper.insertAttrList(attrs);
				
				// 4.2 update page status
				sp.setStatus(Constants.PAGE_STATUS_FETCHED);
				spmapper.updatePageStatus(sp);
				
				// 4.3 send message to kafka
				WebEntityVO entityVO = new WebEntityVO();
				entityVO.setAttrs(attrs);
				entityVO.setId(entity.getId());
				entityVO.setEntityType(entityType);
				entityVO.setEntityTypeId(entityTypeId);
				entityVO.setPageUrl(pageUrl);
				entityVO.setSite(siteName);
				entityVO.setSiteId(siteId);
				entityVO.setCreateDate(new Timestamp(System.currentTimeMillis()));
				
				messagingService.sendMessage(getTopic(entityVO), entityVO);
			} catch(Exception ex) {
				LOGGER.error(ex.getMessage(), ex);
				LOGGER.error("===============> Error process page, skip url: " + pageUrl);
				
				sp.setStatus(Constants.PAGE_STATUS_ERR);
				sp.setErrorMsg(ex.getMessage());
				spmapper.updatePageStatus(sp);
				
				// retry later
				if(ex instanceof IOException) {
					try {
						Thread.sleep(sleepMillis);
						sleepMillis *= 2;
					} catch(InterruptedException iex) {
						LOGGER.error(iex.getMessage(), iex);
					}
				}
			}
			
		}
	}
	
	private String getTopic(WebEntityVO entity) {
		return Constants.KAFKA_TOPIC_PREFIX + entity.getSite() + "-" + entity.getEntityType();
	}
	
	
}


























