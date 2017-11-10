package me.codetalk.webmine.page.impl;

import java.io.IOException;

import org.jsoup.nodes.Document;

import me.codetalk.webmine.util.Utils;

/**
 * 基于HTTP client实现
 * 
 * @author guobxu
 *
 */
public class HttpClientHtmlListPage extends HtmlListPage {

	public HttpClientHtmlListPage(String url) {
		super(url);
	}
	
	@Override
	protected Document getDocument(String url) throws IOException {
		return Utils.getDocument(url);
	}
	
	@Override
	protected HtmlPage getSubPage(String pageUrl) {
		return new HttpClientHtmlPage(pageUrl);
	}

}



















