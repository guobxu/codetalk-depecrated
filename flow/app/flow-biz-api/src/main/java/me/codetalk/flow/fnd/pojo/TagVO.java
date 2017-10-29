package me.codetalk.flow.fnd.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TagVO extends Tag {

	@JsonIgnore
	private String groupTitle;

	public String getGroupTitle() {
		return groupTitle;
	}

	public void setGroupTitle(String groupTitle) {
		this.groupTitle = groupTitle;
	}
	
	
}
