package me.codetalk.flow.fnd.pojo;

import java.sql.Timestamp;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * 通知
 * 
 * @author guobxu
 *
 */
public class Notice {
	
	@JsonProperty("notice_id")
	private Long id;
	
	@JsonIgnore
	private Long userId;		// 通知接收者ID
	
	@JsonProperty("notice_type")
	private Integer type;
	@JsonProperty("notice_subtype")
	private Integer subType;
	
	@JsonIgnore
	private Long fromUser;		// 通知发起人ID
	@JsonProperty("create_date")
	private Timestamp createDate;
	
	@JsonProperty("notice_content")
	private String content;
	@JsonProperty("notice_dtlcontent")
	private String dtlContent;

	@JsonIgnore
	private Integer isApp;
	@JsonIgnore
	private Integer appStatus; // 1 未读    2 已读
	@JsonIgnore
	private Integer isPush;
	@JsonIgnore
	private Integer pushStatus;	// 1 未推送    2 已推送
	
	@JsonIgnore
	private Map<String, String> data;
	
	@JsonProperty("attr1")
	private String attr1;
	@JsonProperty("attr2")
	private String attr2;
	@JsonProperty("attr3")
	private String attr3;
	@JsonProperty("attr4")
	private String attr4;
	@JsonProperty("attr5")
	private String attr5;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getFromUser() {
		return fromUser;
	}

	public void setFromUser(Long fromUser) {
		this.fromUser = fromUser;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDtlContent() {
		return dtlContent;
	}

	public void setDtlContent(String dtlContent) {
		this.dtlContent = dtlContent;
	}

	public Integer getIsApp() {
		return isApp;
	}

	public void setIsApp(Integer isApp) {
		this.isApp = isApp;
	}

	public Integer getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(Integer appStatus) {
		this.appStatus = appStatus;
	}

	public Integer getIsPush() {
		return isPush;
	}

	public void setIsPush(Integer isPush) {
		this.isPush = isPush;
	}

	public Integer getPushStatus() {
		return pushStatus;
	}

	public void setPushStatus(Integer pushStatus) {
		this.pushStatus = pushStatus;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}
	
	public Integer getSubType() {
		return subType;
	}

	public void setSubType(Integer subType) {
		this.subType = subType;
	}

	public String getAttr1() {
		return attr1;
	}

	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}

	public String getAttr2() {
		return attr2;
	}

	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}

	public String getAttr3() {
		return attr3;
	}

	public void setAttr3(String attr3) {
		this.attr3 = attr3;
	}

	public String getAttr4() {
		return attr4;
	}

	public void setAttr4(String attr4) {
		this.attr4 = attr4;
	}

	public String getAttr5() {
		return attr5;
	}
	
	public void setAttr5(String attr5) {
		this.attr5 = attr5;
	}
	
}


