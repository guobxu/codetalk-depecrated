package me.codetalk.flow.pofo.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class PostImg implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long postId;
	private String url;
	private Integer seq;
	private Timestamp createDate;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getPostId() {
		return postId;
	}
	
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public Integer getSeq() {
		return seq;
	}
	
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	public Timestamp getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	
}



