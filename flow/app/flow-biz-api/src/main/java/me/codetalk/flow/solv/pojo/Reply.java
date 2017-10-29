package me.codetalk.flow.solv.pojo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 问题回复
 * @author guobxu
 *
 */
@JsonInclude(Include.NON_NULL)
public class Reply {

	@JsonProperty("reply_id")
	private Long id;
	
	@JsonProperty("quest_id")
	private Long questId;
	
	@JsonProperty("user_id")
	private Integer userId;
	
	@JsonProperty("reply_content")
	private String content;
	
	@JsonProperty("reply_seq")
	private Integer seq;
	
	@JsonProperty("reply_accepted")
	private Integer accepted; // 0 未接受 1 接受 
	
	@JsonIgnore
	private String notifyUsers;
	
	@JsonIgnore
	private Integer spamMark;
	
	@JsonProperty("update_date")
	private Timestamp updateDate;
	@JsonProperty("update_by")
	private Integer updateBy;
	@JsonProperty("create_date")
	private Timestamp createDate;
	
	@JsonProperty("comment_thread")
	private String commentThread; 		// 评论thread
	
	@JsonProperty("accept_date")
	private Timestamp acceptDate; 
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getQuestId() {
		return questId;
	}
	
	public void setQuestId(Long questId) {
		this.questId = questId;
	}
	
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public Integer getSeq() {
		return seq;
	}
	
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	public Integer getAccepted() {
		return accepted;
	}
	
	public void setAccepted(Integer accepted) {
		this.accepted = accepted;
	}
	
	public String getNotifyUsers() {
		return notifyUsers;
	}
	
	public void setNotifyUsers(String notifyUsers) {
		this.notifyUsers = notifyUsers;
	}
	
	public Integer getSpamMark() {
		return spamMark;
	}
	
	public void setSpamMark(Integer spamMark) {
		this.spamMark = spamMark;
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
	
	public Timestamp getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	
	public String getCommentThread() {
		return commentThread;
	}

	public void setCommentThread(String commentThread) {
		this.commentThread = commentThread;
	}

	public Timestamp getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(Timestamp acceptDate) {
		this.acceptDate = acceptDate;
	}
	
	
	
}
