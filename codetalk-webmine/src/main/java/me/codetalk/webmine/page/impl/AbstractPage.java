package me.codetalk.webmine.page.impl;

import me.codetalk.webmine.page.Page;

public abstract class AbstractPage implements Page {

	protected String url;
	
	protected AbstractPage(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}
	
}
