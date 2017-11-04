package me.codetalk.flow.solv.elastic;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(indexName = "flow-solv", type="quest")
public class DocQuest implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@JsonProperty("quest_id")
	private String id;
	
	@JsonProperty("quest_title")
	private String title;
	
	@JsonProperty("quest_content")
	private String content;
	
	@JsonProperty("quest_tags")
	private String tags;
	
	@JsonProperty("quest_votes")
	private Integer votes;
	
	@JsonProperty("ext_quest_url")
	private String extUrl;
	
	@JsonProperty("quest_accepted")
	private Integer accepted;
	
	public DocQuest() {}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Integer getVotes() {
		return votes;
	}

	public void setVotes(Integer votes) {
		this.votes = votes;
	}

	public String getExtUrl() {
		return extUrl;
	}

	public void setExtUrl(String extUrl) {
		this.extUrl = extUrl;
	}

	public Integer getAccepted() {
		return accepted;
	}

	public void setAccepted(Integer accepted) {
		this.accepted = accepted;
	}
	
	
}
