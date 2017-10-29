package me.codetalk.flow.solv.pojo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class QuestTag {

	@JsonIgnore
	private Long id;
	
	@JsonProperty("quest_id")
	private Long qid;

	@JsonProperty("tag_id")
	private Integer tagId;
	
	@JsonIgnore
	private Timestamp createDate;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getQid() {
		return qid;
	}
	
	public void setQid(Long qid) {
		this.qid = qid;
	}
	
	public Integer getTagId() {
		return tagId;
	}
	
	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}
	
	public Timestamp getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	
	
	
}
