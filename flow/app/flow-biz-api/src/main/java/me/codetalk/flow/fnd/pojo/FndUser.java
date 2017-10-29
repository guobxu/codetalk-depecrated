package me.codetalk.flow.fnd.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class FndUser implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer userId;
	private String userLogin;
	private String userName;
	private String userProfile;
	private Integer profileStatus; //  头像状态 1. 待审核    2. 审核通过    3. 拒绝
	
	private Integer positionType; // 职位类别
	
	private String attribute1;
	private String attribute2;
	private String attribute3;
	
	private Timestamp createDate;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}

	public Integer getProfileStatus() {
		return profileStatus;
	}

	public void setProfileStatus(Integer profileStatus) {
		this.profileStatus = profileStatus;
	}

	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}
	
	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public Integer getPositionType() {
		return positionType;
	}

	public void setPositionType(Integer positionType) {
		this.positionType = positionType;
	}
	
	
	
}
