package me.codetalk.flow.pofo.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostVO extends Post {

	@JsonProperty("user_name")
	private String userName;
	
	@JsonProperty("user_login")
	private String userLogin;
	
	@JsonProperty("user_profile")
	private String userProfile;
	
	@JsonProperty("post_login_refer")
	private String postLoginRefer;		// 引用帖子登录名
	
	@JsonProperty("post_name_refer")
	private String postNameRefer;		// 引用帖子用户名
	
//	@JsonProperty("comment_login_refer")
//	private String commentLoginRefer;	// 引用评论登录名
//	
//	@JsonProperty("comment_name_refer")
//	private String commentNameRefer;	// 引用评论用户名
	
//	@JsonProperty("comment_login_reply")
//	private String commentLoginReply; 	// 评论回复的用户登录名
//	
//	@JsonProperty("comment_name_reply")
//	private String commentNameReply; 	// 评论回复的用户名
	
//	private Post postRefer;
//	private Comment commentRefer;
	
	@JsonProperty("post_id_refer")
	private Long postIdRefer;			// 引用的post ID
	
	@JsonProperty("post_content_refer")
	private String postContentRefer;	// 引用的post 内容
	
//	@JsonProperty("comment_id_refer")
//	private Long commentIdRefer;		// 引用的评论ID
//	
//	@JsonProperty("comment_content_refer")
//	private Long commentContentRefer;	// 引用的评论内容
	
	
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

	public String getPostLoginRefer() {
		return postLoginRefer;
	}

	public void setPostLoginRefer(String postLoginRefer) {
		this.postLoginRefer = postLoginRefer;
	}

	public String getPostNameRefer() {
		return postNameRefer;
	}

	public void setPostNameRefer(String postNameRefer) {
		this.postNameRefer = postNameRefer;
	}

//	public String getCommentLoginRefer() {
//		return commentLoginRefer;
//	}
//
//	public void setCommentLoginRefer(String commentLoginRefer) {
//		this.commentLoginRefer = commentLoginRefer;
//	}
//
//	public String getCommentNameRefer() {
//		return commentNameRefer;
//	}
//
//	public void setCommentNameRefer(String commentNameRefer) {
//		this.commentNameRefer = commentNameRefer;
//	}
//
//	public String getCommentLoginReply() {
//		return commentLoginReply;
//	}
//
//	public void setCommentLoginReply(String commentLoginReply) {
//		this.commentLoginReply = commentLoginReply;
//	}
//
//	public String getCommentNameReply() {
//		return commentNameReply;
//	}
//
//	public void setCommentNameReply(String commentNameReply) {
//		this.commentNameReply = commentNameReply;
//	}
	
	public Long getPostIdRefer() {
		return postIdRefer;
	}

	public void setPostIdRefer(Long postIdRefer) {
		this.postIdRefer = postIdRefer;
	}

	public String getPostContentRefer() {
		return postContentRefer;
	}

	public void setPostContentRefer(String postContentRefer) {
		this.postContentRefer = postContentRefer;
	}
	
//	public Long getCommentIdRefer() {
//		return commentIdRefer;
//	}
//
//	public void setCommentIdRefer(Long commentIdRefer) {
//		this.commentIdRefer = commentIdRefer;
//	}
//
//	public Long getCommentContentRefer() {
//		return commentContentRefer;
//	}
//
//	public void setCommentContentRefer(Long commentContentRefer) {
//		this.commentContentRefer = commentContentRefer;
//	}
	
}
