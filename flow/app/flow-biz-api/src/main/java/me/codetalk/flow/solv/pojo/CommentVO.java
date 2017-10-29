package me.codetalk.flow.solv.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CommentVO extends Comment {

	@JsonProperty("user_name")
	private String userName;	// 用户名
	
	@JsonProperty("user_login")
	private String userLogin;	// 登录名
	
	@JsonProperty("user_profile")
	private String userProfile;	// 照片

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}
	
	
	
	
	
	
}


