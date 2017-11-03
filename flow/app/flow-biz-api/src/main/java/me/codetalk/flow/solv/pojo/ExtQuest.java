package me.codetalk.flow.solv.pojo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 外部问题
 * @author guobxu
 *
 */
@JsonInclude(Include.NON_NULL)
public class ExtQuest {

	@JsonProperty("ext_quest_id")
	private String id;
	
	@JsonProperty("ext_quest_site")
	private String site;
	@JsonProperty("ext_quest_url")
	private String url;
	@JsonProperty("ext_quest_title")
	private String title;
	@JsonProperty("ext_quest_content")
	private String content;
	@JsonProperty("ext_quest_answer")
	private String answer;
	@JsonProperty("ext_quest_accept")
	private Integer answerAccept;	// 0 否 1 是
	
	@JsonProperty("ext_quest_tags")
	private String tags;
	@JsonProperty("ext_quest_votes")
	private Integer votes;
	
	@JsonIgnore
	private Integer indexed;		// 0 否 1 是
	
	@JsonProperty("create_date")
	private Timestamp createDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
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

	public Integer getAnswerAccept() {
		return answerAccept;
	}

	public void setAnswerAccept(Integer answerAccept) {
		this.answerAccept = answerAccept;
	}

	public Integer getIndexed() {
		return indexed;
	}

	public void setIndexed(Integer indexed) {
		this.indexed = indexed;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	
	
	
}

















