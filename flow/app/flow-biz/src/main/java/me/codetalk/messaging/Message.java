package me.codetalk.messaging;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 异步消息
 * @author guobxu
 *
 */
public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Object data; 			// Map<String, Object> || List<Map<String, Object>>
	private String app;
	private String module;
	
	private String attribute1;
	private String attribute2;
	private String attribute3;
	private String attribute4;
	private String attribute5;

	private String key; // 消息标志
	
	private Timestamp createDate;
	
	public static Builder builder() {
		return new Builder();
	}
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	
	public String getApp() {
		return app;
	}
	
	public void setApp(String app) {
		this.app = app;
	}
	
	public String getModule() {
		return module;
	}
	
	public void setModule(String module) {
		this.module = module;
	}
	
	public Timestamp getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public String toString() {
		return "Message: key = " + key + ", app = " + app + ", module = " 
					+ module + ", data = [" + data + "]";
	}

	public static class Builder {
		
		private Message mesg = new Message();
		
		public Message build() {
			return mesg;
		}
		
		public Builder key(String key) {
			mesg.setKey(key);
			
			return this;
		}
		
		public Builder app(String app) {
			mesg.setApp(app);
			
			return this;
		}
		
		public Builder module(String module) {
			mesg.setModule(module);
			
			return this;
		}
		
		public Builder data(Object data) {
			mesg.setData(data);
			
			return this;
		}
		
		public Builder attr1(String attr1) {
			mesg.setAttribute1(attr1);
			
			return this;
		}
		public Builder attr2(String attr2) {
			mesg.setAttribute2(attr2);
			
			return this;
		}
		public Builder attr3(String attr3) {
			mesg.setAttribute3(attr3);
			
			return this;
		}
		public Builder attr4(String attr4) {
			mesg.setAttribute4(attr4);
			
			return this;
		}
		public Builder attr5(String attr5) {
			mesg.setAttribute5(attr5);
			
			return this;
		}
		
	}
	
	
}
