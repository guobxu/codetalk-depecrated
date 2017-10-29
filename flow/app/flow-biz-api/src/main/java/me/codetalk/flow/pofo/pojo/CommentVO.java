package me.codetalk.flow.pofo.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommentVO extends Comment {

	@JsonProperty("user_name")
	private String userName;
	@JsonProperty("user_login")
	private String userLogin;
	@JsonProperty("user_profile")
	private String userProfile;
	
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
