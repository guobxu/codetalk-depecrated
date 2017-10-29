package me.codetalk.flow.pofo.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author guobxu
 *
 */
public class Post implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("post_id")
	private Long id;
	@JsonIgnore
	private Integer userId;
	@JsonProperty("post_type")
	private Integer type;		// 帖子类型 1、帖子（原创） 2、转帖 3、引用帖子
	@JsonProperty("post_content")
	private String content;
	@JsonProperty("post_id_refer")
	private Long referPost;		// 转帖帖子ID
	@JsonProperty("comment_id_refer")
	private Long referComment;	// 转帖评论ID
	
	@JsonIgnore
	private String notifyUsers; // 提醒用户 eg: ['user1', 'user2'...]
	
	@JsonProperty("post_refers")
	private Integer postRefers; // 转帖数
	@JsonProperty("post_likes")
	private Integer postLikes; 	// 点赞数
	@JsonProperty("post_comments")
	private Integer postComments;	// 评论数
	@JsonProperty("post_views")
	private Integer postViews;	// 查看数
	
	@JsonProperty("create_date")
	private Timestamp createDate;

	@JsonProperty("post_imgs")
	private List<PostImg> imgList;
	
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getReferPost() {
		return referPost;
	}

	public void setReferPost(Long referPost) {
		this.referPost = referPost;
	}

	public Long getReferComment() {
		return referComment;
	}

	public void setReferComment(Long referComment) {
		this.referComment = referComment;
	}

	public String getNotifyUsers() {
		return notifyUsers;
	}

	public void setNotifyUsers(String notifyUsers) {
		this.notifyUsers = notifyUsers;
	}

	public Integer getPostRefers() {
		return postRefers;
	}

	public void setPostRefers(Integer postRefers) {
		this.postRefers = postRefers;
	}

	public Integer getPostLikes() {
		return postLikes;
	}

	public void setPostLikes(Integer postLikes) {
		this.postLikes = postLikes;
	}

	public Integer getPostComments() {
		return postComments;
	}

	public void setPostComments(Integer postComments) {
		this.postComments = postComments;
	}

	public Integer getPostViews() {
		return postViews;
	}

	public void setPostViews(Integer postViews) {
		this.postViews = postViews;
	}
	
	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public List<PostImg> getImgList() {
		return imgList;
	}

	public void setImgList(List<PostImg> imgList) {
		this.imgList = imgList;
	}

	
	
	
}
