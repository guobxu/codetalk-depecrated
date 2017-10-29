package me.codetalk.flow.solv.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class QuestVO extends Quest {

	@JsonProperty("user_name")
	private String userName;	// 用户名
	
	@JsonProperty("user_login")
	private String userLogin;	// 登录名
	
	@JsonProperty("user_profile")
	private String userProfile;	// 照片

	@JsonProperty("quest_accepted")
	private Integer accepted;	// 是否解决 0 否 1 是
	
	@JsonProperty("quest_frozen")
	private Integer frozen;		// 是否冻结 0 否 1 是
	
	@JsonProperty("quest_replynum")
	private Integer replyNum;	// 答复数
	
	@JsonProperty("quest_cmntnum")
	private Integer cmntNum;	// 评论数
	
	@JsonProperty("quest_tags")
	private List<QuestTagVO> tags;
	
	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty("quest_replies")
	private List<ReplyVO> replies;
	
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
	
	public Integer getAccepted() {
		return accepted;
	}

	public void setAccepted(Integer accepted) {
		this.accepted = accepted;
	}

	public Integer getFrozen() {
		return frozen;
	}

	public void setFrozen(Integer frozen) {
		this.frozen = frozen;
	}

	public Integer getReplyNum() {
		return replyNum;
	}

	public void setReplyNum(Integer replyNum) {
		this.replyNum = replyNum;
	}

	public List<QuestTagVO> getTags() {
		return tags;
	}

	public void setTags(List<QuestTagVO> tags) {
		this.tags = tags;
	}

	public List<ReplyVO> getReplies() {
		return replies;
	}

	public void setReplies(List<ReplyVO> replies) {
		this.replies = replies;
	}

	public Integer getCmntNum() {
		return cmntNum;
	}

	public void setCmntNum(Integer cmntNum) {
		this.cmntNum = cmntNum;
	}

	
}
