package me.codetalk.flow.miner.pojo;

import java.sql.Timestamp;

public class SiteList {

	private Integer id;
	private Integer siteId;
	private Integer entityTypeId;
	private String url;
	private Integer type;
	private String pagesEl;
	private String pagesAttr;
	
	private Integer lastPage;
	private String pageParam;
	private Integer enabled;
	
	private Timestamp createDate;

	public String getDerivedUrl() {
		int cp = lastPage + 1;
		
		return pageParam == null ? url : url.replaceAll("\\{" + pageParam + "\\}", String.valueOf(cp));
	}
	
	public String getDerivedEl() {
		int cp = lastPage + 1;
		
		return pageParam == null ? pagesEl : pagesEl.replaceAll("\\{" + pageParam + "\\}", String.valueOf(cp));
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPagesEl() {
		return pagesEl;
	}

	public void setPagesEl(String pagesEl) {
		this.pagesEl = pagesEl;
	}

	public String getPagesAttr() {
		return pagesAttr;
	}

	public void setPagesAttr(String pagesAttr) {
		this.pagesAttr = pagesAttr;
	}

	public Integer getLastPage() {
		return lastPage;
	}

	public void setLastPage(Integer lastPage) {
		this.lastPage = lastPage;
	}

	public String getPageParam() {
		return pageParam;
	}

	public void setPageParam(String pageParam) {
		this.pageParam = pageParam;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Integer getEntityTypeId() {
		return entityTypeId;
	}

	public void setEntityTypeId(Integer entityTypeId) {
		this.entityTypeId = entityTypeId;
	}
	
	
	
}
