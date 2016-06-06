package com.tgj.bean;

import java.util.List;

import android.graphics.Bitmap;

public class BOrderinfo {
	private int code;
	private String summary;
	private List<Data> data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public List<Data> getData() {
		return data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}

	public class Data {
		private int id;
		private String icon;
		private String title;
		private String classid;
		private String begintime;
		private String endtime;
		private String price;
		private String itemname;
		private String state;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getIcon() {
			return icon;
		}
		public void setIcon(String icon) {
			this.icon = icon;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getClassid() {
			return classid;
		}
		public void setClassid(String classid) {
			this.classid = classid;
		}
		public String getBegintime() {
			return begintime;
		}
		public void setBegintime(String begintime) {
			this.begintime = begintime;
		}
		public String getEndtime() {
			return endtime;
		}
		public void setEndtime(String endtime) {
			this.endtime = endtime;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getItemname() {
			return itemname;
		}
		public void setItemname(String itemname) {
			this.itemname = itemname;
		}
		public String getPrice() {
			return price;
		}
		public void setPrice(String price) {
			this.price = price;
		}
	}
}
