package com.hp.bean;
/**
 * 获取七牛云图片上传的Token
 * @author qwh
 *
 */
public class PUploadImageToken {
	private String code;
	private String summary;
	private Data data;

	public class Data{
		private String name;
		private String token;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		
		
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
}
