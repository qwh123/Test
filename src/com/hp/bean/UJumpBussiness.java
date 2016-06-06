package com.hp.bean;
/**
 * 判断是否是商家
 * @author qwh
 *
 */
public class UJumpBussiness {
	private String code;
	private String summary;
	private Data data;
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
	public class Data{
		private String state;
		private String link;
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getLink() {
			return link;
		}
		public void setLink(String link) {
			this.link = link;
		}
		
	}

}
