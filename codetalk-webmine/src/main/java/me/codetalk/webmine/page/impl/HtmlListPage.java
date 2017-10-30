package me.codetalk.webmine.page.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import me.codetalk.webmine.page.Page;
import me.codetalk.webmine.page.PageAttr;

/**
 * HTML列表页面
 * @author guobxu
 *
 */
public class HtmlListPage extends AbstractListPage {

	public HtmlListPage(String url) {
		super(url);
	}
	
	public List<Page> fetchPages(PageAttr attr) throws IOException {
		List<Page> pages = new ArrayList<Page>();
		
		Document doc = Jsoup.connect(url).get();
		Elements subPages = doc.select(attr.getEl());
		subPages.forEach(el -> {
			Page subPage = new HtmlPage(el.absUrl(attr.getName()));
			pages.add(subPage);
		});
		
		return pages;
	}

}
