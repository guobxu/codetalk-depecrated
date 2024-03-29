package me.codetalk.flow.miner.vo;

import java.util.List;

import me.codetalk.flow.miner.pojo.SiteEntityAttr;
import me.codetalk.flow.miner.pojo.SiteList;

public class SiteListVO extends SiteList {

	private String siteName;
	private String siteHome;
	
	private String entityType;
	
	private List<SiteEntityAttr> entityAttrs;

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteHome() {
		return siteHome;
	}

	public void setSiteHome(String siteHome) {
		this.siteHome = siteHome;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public List<SiteEntityAttr> getEntityAttrs() {
		return entityAttrs;
	}

	public void setEntityAttrs(List<SiteEntityAttr> entityAttrs) {
		this.entityAttrs = entityAttrs;
	}
	
	
}
