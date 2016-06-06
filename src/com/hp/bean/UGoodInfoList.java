package com.hp.bean;

import java.util.List;

public class UGoodInfoList {
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

	public static class Data {
		private String id;// 商品id
		private String countid;
		private String classid;// 商品分类
		private String title;// 商品标题
		private String icon;// 商品图标（首页）
		private String allprice;// 最新价(轰趴)
		private String score;// 商品评分
		private String time;
		private String addcode;// 所在城市区域码
		private String distance;// 用户与商品所在地距离
		private String state;// 状态（最新最热）
		private String amount;//购买数量
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getCountid() {
			return countid;
		}
		public void setCountid(String countid) {
			this.countid = countid;
		}
		public String getClassid() {
			return classid;
		}
		public void setClassid(String classid) {
			this.classid = classid;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getIcon() {
			return icon;
		}
		public void setIcon(String icon) {
			this.icon = icon;
		}
		public String getAllprice() {
			return allprice;
		}
		public void setAllprice(String allprice) {
			this.allprice = allprice;
		}
		public String getScore() {
			return score;
		}
		public void setScore(String score) {
			this.score = score;
		}
		public String getAddcode() {
			return addcode;
		}
		public void setAddcode(String addcode) {
			this.addcode = addcode;
		}
		public String getDistance() {
			return distance;
		}
		public void setDistance(String distance) {
			this.distance = distance;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getAmount() {
			return amount;
		}
		public void setAmount(String amount) {
			this.amount = amount;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}

	}

}
