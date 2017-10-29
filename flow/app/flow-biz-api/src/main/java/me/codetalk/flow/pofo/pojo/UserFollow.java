package me.codetalk.flow.pofo.pojo;

import java.sql.Timestamp;

public class UserFollow {

	private Long id;
	private Integer userId;
	private Integer userFollowed;
	private Integer deleteMark;
	
	private Timestamp updateDate;
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
	
	public Integer getUserFollowed() {
		return userFollowed;
	}
	
	public void setUserFollowed(Integer userFollowed) {
		this.userFollowed = userFollowed;
	}
	
	public Integer getDeleteMark() {
		return deleteMark;
	}
	
	public void setDeleteMark(Integer deleteMark) {
		this.deleteMark = deleteMark;
	}
	
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	
	public Timestamp getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	
	
	
}
