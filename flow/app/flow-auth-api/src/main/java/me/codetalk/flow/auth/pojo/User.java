package me.codetalk.flow.auth.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

/**
 * @author guobxu
 *
 */
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer id;
	private String login;
	private String loginLower;		
	private String mobile;
	private Integer mobileVerified;	
	private String mail;
	private String mailLower;		
	private Integer mailVerified;	
	private Integer status;			// 1. active  2. suspend 
	
	private String passwd;
	private Timestamp createDate;
	
	private Map<String, String> attrs; 
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public Integer getMobileVerified() {
		return mobileVerified;
	}
	
	public void setMobileVerified(Integer mobileVerified) {
		this.mobileVerified = mobileVerified;
	}
	
	public String getMail() {
		return mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public Integer getMailVerified() {
		return mailVerified;
	}
	
	public void setMailVerified(Integer mailVerified) {
		this.mailVerified = mailVerified;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getPasswd() {
		return passwd;
	}
	
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	public Timestamp getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getLoginLower() {
		return loginLower;
	}

	public void setLoginLower(String loginLower) {
		this.loginLower = loginLower;
	}

	public String getMailLower() {
		return mailLower;
	}

	public void setMailLower(String mailLower) {
		this.mailLower = mailLower;
	}

	public Map<String, String> getAttrs() {
		return attrs;
	}

	public void setAttrs(Map<String, String> attrs) {
		this.attrs = attrs;
	}
	
	
	
}
