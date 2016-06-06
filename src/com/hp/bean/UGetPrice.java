package com.hp.bean;

public class UGetPrice {
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
		private String price;

		public String getPrice() {
			return price;
		}

		public void setPrice(String price) {
			this.price = price;
		}
		
	}
}
