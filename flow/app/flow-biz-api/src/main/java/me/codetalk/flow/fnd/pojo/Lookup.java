package me.codetalk.flow.fnd.pojo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 字典
 * @author guobxu
 *
 */
public class Lookup {

	@JsonIgnore
	private Integer id;
	
	@JsonIgnore
	private String category;
	
	@JsonProperty("code")
	private String code;
	
	@JsonProperty("value")
	private String value;
	
	@JsonIgnore
	private Integer order;
	
	@JsonIgnore
	private Timestamp createDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	
	
}
