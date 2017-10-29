package me.codetalk.flow.auth.pojo;

import java.io.Serializable;

/**
 * 访问token
 * @author guobxu
 *
 */
public class UserLogin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String accessToken;
	private Integer userId;		// ID
	private String userLogin; 	// user login
	private String srcType; 	// Web | iOS | Android
	private Long createDate;	// timemillis
	
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public String getUserLogin() {
		return userLogin;
	}
	
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}
	
	public String getSrcType() {
		return srcType;
	}
	
	public void setSrcType(String srcType) {
		this.srcType = srcType;
	}
	
	public Long getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
}
