package me.codetalk.flow.press.elastic;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@Document(indexName = "flow-press", type="article")
public class DocArticle implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("article_id")
	private Integer articleId;
	
	@Id
	@JsonProperty("article_uuid")
	private String uuid;
	
	@JsonProperty("article_site")
	private String site;
	
	@JsonProperty("article_url")
	private String url;
	
	@JsonProperty("article_title")
	private String title;
	
	@JsonProperty("article_summary")
	private String summary;
	
	@JsonProperty("article_content")
	private String content;
	
	@JsonProperty("article_tags")
	private String tags;
	
	@JsonProperty("create_date")
	private Timestamp createDate;
	
	public DocArticle() {}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}
	
	
	
}
