package me.codetalk.webmine.page.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import me.codetalk.webmine.data.WebEntity;
import me.codetalk.webmine.page.PageAttr;
import me.codetalk.webmine.util.Utils;

public class HtmlPage extends AbstractPage {

	public HtmlPage(String url) {
		super(url);
	}

	@Override
	public WebEntity fetchEntity(Map<String, PageAttr> attrMap) throws IOException {
		WebEntity entity = new WebEntity();
		entity.setUrl(url);
		
		String[] siteAndRes = Utils.extractUrl(url);
		entity.setSite(siteAndRes[0]);
		entity.setResource(siteAndRes[1]);
		
		Map<String, String> attrs = new HashMap<String, String>();
		
		Document doc = Jsoup.connect(url).get();
		attrMap.forEach((k, v) -> {
			Elements els = doc.select(v.getEl());
			if(els.isEmpty()) {
				attrs.put(k, null);
			} else {
				Element el = els.first();
				
				String attrName = v.getName();
				attrs.put(k, attrName == null ? el.html() : el.attr(attrName));
			}
		});
		
		entity.setAttrs(attrs);
		
		return entity;
	}
	

}
