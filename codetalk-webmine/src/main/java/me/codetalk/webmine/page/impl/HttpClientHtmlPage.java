package me.codetalk.webmine.page.impl;

import java.io.IOException;

import org.jsoup.nodes.Document;

import me.codetalk.webmine.util.HttpClientUtils;

public class HttpClientHtmlPage extends HtmlPage {

	public HttpClientHtmlPage(String url) {
		super(url);
	}

	@Override
	protected Document getDocument() throws IOException {
		return HttpClientUtils.getDocument(url);
	}

}
