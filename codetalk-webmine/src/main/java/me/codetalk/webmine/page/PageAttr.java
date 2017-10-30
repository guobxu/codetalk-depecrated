package me.codetalk.webmine.page;

/**
 * DOM元素属性
 * 
 * @author guobxu
 *
 */
public class PageAttr {

	private String el;		// element 
	private String name;	// attr name	如果为null 取text
	
	public PageAttr(String el, String name) {
		this.el = el;
		this.name = name;
	}
	
	public String getEl() {
		return el;
	}
	
	public void setEl(String el) {
		this.el = el;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
