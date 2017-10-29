package me.codetalk.flow.pofo.pojo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author guobxu
 *
 */
public class Comment {

	@JsonProperty("comment_id")
	private Long id;
	private Long postId;
	private Integer userId;
	
	@JsonProperty("comment_content")
	private String content;
//	private Integer userReply;
	
	private Long commentReply;
	private Integer seq;
	
	@JsonProperty("notify_users")
	private String notifyUsers; // 提醒用户 eg: ['user1', 'user2'...]
	
	@JsonProperty("create_date")
	private Timestamp createDate;
	
	private String thread;	// 评论thread

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
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

//	public Integer getUserReply() {
//		return userReply;
//	}
//
//	public void setUserReply(Integer userReply) {
//		this.userReply = userReply;
//	}

	public Long getCommentReply() {
		return commentReply;
	}

	public void setCommentReply(Long commentReply) {
		this.commentReply = commentReply;
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
	
}
