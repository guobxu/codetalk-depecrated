package me.codetalk.webmine.page.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import me.codetalk.webmine.Constants;
import me.codetalk.webmine.data.WebEntity;
import me.codetalk.webmine.page.PageAttr;
import me.codetalk.webmine.util.StringUtils;

public abstract class HtmlPage extends AbstractPage {

	public HtmlPage(String url) {
		super(url);
	}

	protected abstract Document getDocument() throws IOException;
	
	@Override
	public WebEntity fetchEntity(Map<String, PageAttr> attrMap) throws IOException {
		WebEntity entity = new WebEntity();
		entity.setUrl(url);
		
		String[] siteAndRes = StringUtils.extractUrl(url);
		entity.setSite(siteAndRes[0]);
		entity.setResource(siteAndRes[1]);
		
		Map<String, String> attrs = new HashMap<String, String>();
		
		Document doc = getDocument();
		attrMap.forEach((k, v) -> {
			Elements els = doc.select(v.getEl());
			if(els.isEmpty()) {
				attrs.put(k, null);
			} else {
				String attrName = v.getName();
				Integer type = v.getType();
				
				String val = null;
				if(attrName != null) {
					val = els.attr(attrName);
				} else if(type == Constants.ATTR_TYPE_HTML) {
					val = els.html();
				} else if(type == Constants.ATTR_TYPE_TEXT) {
					val = els.text();
				} else if(type == Constants.ATTR_TYPE_HTML_FIRST) {
					val = els.first().html();
				} else if(type == Constants.ATTR_TYPE_TEXT_FIRST) {
					val = els.first().text();
				}
				
				attrs.put(k, val);
			}
		});
		
		entity.setAttrs(attrs);
		
		return entity;
	}
	

}









