package me.codetalk.flow.solv.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuestTagVO extends QuestTag {

	@JsonProperty("tag_text")
	private String tagText;

	public String getTagText() {
		return tagText;
	}

	public void setTagText(String tagText) {
		this.tagText = tagText;
	}
	
	
}
