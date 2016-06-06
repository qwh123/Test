package com.tgj.bean;

public class BOrderinfoDetail {
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
		private String id;
		private String ordernum;
		private String title;
		private String	classid;
		private String price;
		private String tel;
		private String begintime;
		private String endtime;
		private String nickname;
		private String countid;
		private String icon;
		private String mark;
		private String state;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getOrdernum() {
			return ordernum;
		}
		public void setOrdernum(String ordernum) {
			this.ordernum = ordernum;
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
		public String getPrice() {
			return price;
		}
		public void setPrice(String price) {
			this.price = price;
		}
		public String getTel() {
			return tel;
		}
		public void setTel(String tel) {
			this.tel = tel;
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
		public String getMark() {
			return mark;
		}
		public void setMark(String mark) {
			this.mark = mark;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getNickname() {
			return nickname;
		}
		public void setNickname(String nickname) {
			this.nickname = nickname;
		}
		public String getCountid() {
			return countid;
		}
		public void setCountid(String countid) {
			this.countid = countid;
		}
		public String getIcon() {
			return icon;
		}
		public void setIcon(String icon) {
			this.icon = icon;
		}
	}	

}
