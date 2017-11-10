package me.codetalk.flow.solv.elastic;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@Document(indexName = "flow-solv", type="quest")
public class DocQuest implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("quest_id")
	private Long questId;
	
	@Id
	@JsonProperty("quest_uuid")
	private String uuid;
	
	@JsonProperty("quest_title")
	private String title;
	
	@JsonProperty("quest_content")
	private String content;
	
	@JsonProperty("quest_tags")
	private String tags;
	
	@JsonProperty("quest_votes")
	private Integer votes;
	
	@JsonProperty("ext_quest_site")
	private String extSite;
	
	@JsonProperty("ext_quest_url")
	private String extUrl;
	
	@JsonProperty("quest_accepted")
	private Integer accepted;
	
	@JsonProperty("create_date")
	private Timestamp createDate;
	
	public DocQuest() {}
	
	public Long getQuestId() {
		return questId;
	}

	public void setQuestId(Long questId) {
		this.questId = questId;
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getExtSite() {
		return extSite;
	}

	public void setExtSite(String extSite) {
		this.extSite = extSite;
	}

	
}
