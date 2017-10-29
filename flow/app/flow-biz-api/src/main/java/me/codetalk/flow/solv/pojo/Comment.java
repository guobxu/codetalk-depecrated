package me.codetalk.flow.solv.pojo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 评论: 问题评论 / 答复评论 / 评论的评论
 * @author guobxu
 *
 */
@JsonInclude(Include.NON_NULL)
public class Comment {

	@JsonProperty("comment_id")
	private Long id;
	private Integer userId;
	private Long questId;
	private Long replyId;
	private Long commentReply;
	
	@JsonProperty("comment_content")
	private String content;
	private Integer seq;
	private String notifyUsers;
	private Integer spamMark;
	
	private Timestamp updateDate;
	private Integer updateBy;
	
	@JsonProperty("create_date")
	private Timestamp createDate;
	
	private String thread;
	
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
	
	public Long getReplyId() {
		return replyId;
	}
	
	public void setReplyId(Long replyId) {
		this.replyId = replyId;
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
	
	public String getThread() {
		return thread;
	}

	public void setThread(String thread) {
		this.thread = thread;
	}

	public Long getCommentReply() {
		return commentReply;
	}

	public void setCommentReply(Long commentReply) {
		this.commentReply = commentReply;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
	
	
}
