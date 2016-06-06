package com.hp.bean;

import java.util.List;

public class UOrderinfoList {
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
		private String id;// 订单id
		private String goodid;// 商品id
		private String icon;// 商品图标
		private String title;// 商品名
		private String classid;// 商品分类
		private String begintime;// 开始时间
		private String endtime;// 结束时间
		private String price;// 价格
		private String state;//状态
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getGoodid() {
			return goodid;
		}
		public void setGoodid(String goodid) {
			this.goodid = goodid;
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
		public String getPrice() {
			return price;
		}
		public void setPrice(String price) {
			this.price = price;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
	}
}
