package me.codetalk.flow.solv.pojo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class Quest {

	@JsonProperty("quest_id")
	private Long id;
	
	@JsonIgnore
	private String uuid;
	
	@JsonIgnore
	private Integer userId;
	
	@JsonProperty("quest_title")
	private String title;
	
	@JsonProperty("quest_content")
	private String content;
	
	@JsonProperty("quest_plus")
	private Integer plus; // 同样的问题
	
	@JsonIgnore
	private Integer status; // 1 - 草稿 	2 - 已发布 	3 - 冻结 (不允许回复)	4 - 已解决 	
	
	@JsonIgnore
	private String notifyUsers;
	
	@JsonIgnore
	private Integer dupMark; // 0 非重复 1 重复
	
	@JsonIgnore
	private Long dupId;		// 重复问题ID
	
	@JsonProperty("quest_spam")
	private Integer spamMark; // 0 non-spam 1 spam
	
	@JsonIgnore
	private Integer indexed;
	
	@JsonProperty("create_date")
	private Timestamp createDate;
	
	@JsonProperty("update_by")
	private Integer updateBy;
	
	@JsonProperty("update_date")
	private Timestamp updateDate;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	
	public Integer getPlus() {
		return plus;
	}
	
	public void setPlus(Integer plus) {
		this.plus = plus;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getNotifyUsers() {
		return notifyUsers;
	}
	
	public void setNotifyUsers(String notifyUsers) {
		this.notifyUsers = notifyUsers;
	}
	
	public Integer getDupMark() {
		return dupMark;
	}
	
	public void setDupMark(Integer dupMark) {
		this.dupMark = dupMark;
	}
	
	public Long getDupId() {
		return dupId;
	}
	
	public void setDupId(Long dupId) {
		this.dupId = dupId;
	}
	
	public Integer getSpamMark() {
		return spamMark;
	}
	
	public void setSpamMark(Integer spamMark) {
		this.spamMark = spamMark;
	}
	
	public Timestamp getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getIndexed() {
		return indexed;
	}

	public void setIndexed(Integer indexed) {
		this.indexed = indexed;
	}

	
	
	
	
}
