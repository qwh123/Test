package com.hp.bean;

import java.util.List;

/**
 * 收藏列表模型
 * 
 * @author qwh
 * 
 */
public class UFavoriteList {
	private String code;
	private String summary;
	private List<Data> data;

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

	public List<Data> getData() {
		return data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}

	public class Data {
		private String id;// 商品id
		private String classid;// 商品分类
		private String title;// 商品标题
		private String icon;// 商品图标
		private String price;// 价格(活动)
		private String hostdate;//主办方（活动）
		private String score;// 商品评分
		private String addcode;// 商品地址码
		private String date;// 商品时间（轰趴管）
		private String oldprice;//原价（轰趴管）
		private String allprice;//现价（轰趴管）
		private String distance;// 商品距离
		private String state;// 商品状态

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
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

		public String getPrice() {
			return price;
		}

		public void setPrice(String price) {
			this.price = price;
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

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
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

		public String getHostdate() {
			return hostdate;
		}

		public void setHostdate(String hostdate) {
			this.hostdate = hostdate;
		}

		public String getOldprice() {
			return oldprice;
		}

		public void setOldprice(String oldprice) {
			this.oldprice = oldprice;
		}

		public String getAllprice() {
			return allprice;
		}

		public void setAllprice(String allprice) {
			this.allprice = allprice;
		}
	}

}
