package me.codetalk.webmine.page.impl;

import java.io.IOException;
import java.util.Map;

import me.codetalk.webmine.data.WebEntity;
import me.codetalk.webmine.page.ListPage;
import me.codetalk.webmine.page.PageAttr;

/**
 * 基础实现, 使用根路径查找所有的子页面
 * @author guobxu
 *
 */
public abstract class AbstractListPage extends AbstractPage implements ListPage {

	protected AbstractListPage(String url) {
		super(url);
	}
	
	@Override
	public WebEntity fetchEntity(Map<String, PageAttr> attrMap) throws IOException {
		throw new UnsupportedOperationException("not implemented!");
	}

	
}
