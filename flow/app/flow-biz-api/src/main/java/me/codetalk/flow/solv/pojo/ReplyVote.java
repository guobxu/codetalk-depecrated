package me.codetalk.flow.solv.pojo;

import java.sql.Timestamp;

public class ReplyVote {

	private Long id;
	private Integer userId;
	private Long replyId;
	private Integer up;
	private Integer down;
	private Integer deleteMark;
	private Timestamp createDate;
	
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
	
	public Long getReplyId() {
		return replyId;
	}
	
	public void setReplyId(Long replyId) {
		this.replyId = replyId;
	}
	
	public Integer getUp() {
		return up;
	}
	
	public void setUp(Integer up) {
		this.up = up;
	}
	
	public Integer getDown() {
		return down;
	}
	
	public void setDown(Integer down) {
		this.down = down;
	}
	
	public Integer getDeleteMark() {
		return deleteMark;
	}
	
	public void setDeleteMark(Integer deleteMark) {
		this.deleteMark = deleteMark;
	}
	
	public Timestamp getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	
	 
	 
	 
}
