package com.tgj.bean;
/**
 * 获取商家收入的model
 * @author qwh
 *
 */
public class BGetMoney {
	private String code;
	private String summary;
	private Data data;
	public class Data{
		
		private String moneynow;
		private String moneytotal;
		public String getMoneytotal() {
			return moneytotal;
		}
		public void setMoneytotal(String moneytotal) {
			this.moneytotal = moneytotal;
		}
		public String getMoneynow() {
			return moneynow;
		}
		public void setMoneynow(String moneynow) {
			this.moneynow = moneynow;
		}
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
