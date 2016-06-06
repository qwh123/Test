package com.hp.bean;

import java.util.List;

/**
 * 获取首页公告model
 * 
 * @author qwh
 * @since 2016-1-16
 */
public class UIndex {
	private String code;// 状态码
	private String summary;// 获取信息状态
	private Data data;// 获取数据

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

	public class Data {
		private List<Banner> banner;
		private List<Label> label;
		private Publish publish;
		private List<Goods> goods;

		public List<Banner> getBanner() {
			return banner;
		}

		public void setBanner(List<Banner> banner) {
			this.banner = banner;
		}

		public List<Label> getLabel() {
			return label;
		}

		public void setLabel(List<Label> label) {
			this.label = label;
		}

		public List<Goods> getGoods() {
			return goods;
		}

		public void setGoods(List<Goods> goods) {
			this.goods = goods;
		}


		public Publish getPublish() {
			return publish;
		}

		public void setPublish(Publish publish) {
			this.publish = publish;
		}


		/**
		 * 轮播model
		 * 
		 * @author qwh
		 * 
		 */
		public class Banner {
			private String id;// banner id
			private String imagelink;// 图片地址

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getImagelink() {
				return imagelink;
			}

			public void setImagelink(String imagelink) {
				this.imagelink = imagelink;
			}
		}

		/**
		 * 标签model
		 * 
		 * @author qwh
		 * 
		 */
		public class Label {
			private String id;
			private String icon;
			private String name;

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getIcon() {
				return icon;
			}

			public void setIcon(String icon) {
				this.icon = icon;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}
		}

		/**
		 * 公告model
		 * 
		 * @author qwh
		 * 
		 */
		public class Publish {
			private String id;
			private String title;
			public String getId() {
				return id;
			}
			public void setId(String id) {
				this.id = id;
			}
			public String getTitle() {
				return title;
			}
			public void setTitle(String title) {
				this.title = title;
			}
		}

		/**
		 * 商品model
		 * 
		 * @author qwh
		 * 
		 */
		public class Goods {
			private String id;// 商品id
			private String countid;// 商品所属id
			private String classid;// 商品类别
			private String title;// 商品名称
			private String icon;// 商品图标
			private String time;
			private String amount;
			private String allprice;// 商品价格
			private String score;// 商品评分
			private String distance;// 商品距离
			private String state;// 商品状态

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

			public String getTime() {
				return time;
			}

			public void setTime(String time) {
				this.time = time;
			}

			public String getAmount() {
				return amount;
			}

			public void setAmount(String amount) {
				this.amount = amount;
			}

		}
	}

}
