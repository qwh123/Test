package com.hp.bean;

public class UIndexAd {
	private String code;
	private String summary;
	private Data data;
	public static class Data{
		private int id;
		private String imagelink;
		private String detaillink;
		private String create_time;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getImagelink() {
			return imagelink;
		}
		public void setImagelink(String imagelink) {
			this.imagelink = imagelink;
		}
		public String getDetaillink() {
			return detaillink;
		}
		public void setDetaillink(String detaillink) {
			this.detaillink = detaillink;
		}
		public String getCreate_time() {
			return create_time;
		}
		public void setCreate_time(String create_time) {
			this.create_time = create_time;
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
