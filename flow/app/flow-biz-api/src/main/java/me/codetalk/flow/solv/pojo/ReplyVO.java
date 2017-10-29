package me.codetalk.flow.solv.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class ReplyVO extends Reply {

	@JsonProperty("user_name")
	private String userName;	// 用户名
	
	@JsonProperty("user_login")
	private String userLogin;	// 登录名
	
	@JsonProperty("user_profile")
	private String userProfile;	// 照片
	
	@JsonProperty("reply_cmntnum")
	private Integer cmntNum;	// 评论数

	@JsonIgnore
	private Integer questCreateBy; // 问题创建人
	
	@JsonIgnore
	private Integer questStatus; // 问题状态
	
	@JsonProperty("reply_votenum")
	private Integer voteNum;
	
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

	public Integer getCmntNum() {
		return cmntNum;
	}

	public void setCmntNum(Integer cmntNum) {
		this.cmntNum = cmntNum;
	}
	
	public Integer getQuestCreateBy() {
		return questCreateBy;
	}

	public void setQuestCreateBy(Integer questCreateBy) {
		this.questCreateBy = questCreateBy;
	}

	public Integer getQuestStatus() {
		return questStatus;
	}

	public void setQuestStatus(Integer questStatus) {
		this.questStatus = questStatus;
	}
	
	public Integer getVoteNum() {
		return voteNum;
	}

	public void setVoteNum(Integer voteNum) {
		this.voteNum = voteNum;
	}
	
	
	
}
