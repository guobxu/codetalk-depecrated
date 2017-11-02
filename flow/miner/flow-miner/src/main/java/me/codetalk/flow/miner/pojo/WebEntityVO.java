package me.codetalk.flow.miner.pojo;

import java.util.List;

public class WebEntityVO extends WebEntity {

	private List<WebEntityAttr> attrs;

	private String site;
	
	private String entityType;
	
	public List<WebEntityAttr> getAttrs() {
		return attrs;
	}

	public void setAttrs(List<WebEntityAttr> attrs) {
		this.attrs = attrs;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	
	
	
}
