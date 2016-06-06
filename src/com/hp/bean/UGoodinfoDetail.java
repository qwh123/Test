package com.hp.bean;

import java.util.List;

/**
 * 获取商品商品详细信息
 * 
 * @author qwh
 * 
 */
public class UGoodinfoDetail {
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

	public class Data {

		private String id; // #活动id
		private String title;// #活动标题
		private String images; // #活动图片
		private String summary;// #活动简介
		private String oldprice;// #活动价格
		private String tel;// #活动联系电话
		private String address;// #活动地址
		private String lng;// #活动经度
		private String lat;// #活动纬度
		private String favorite;// #活动收藏状态
		private String allnum;// #总人数
		private String beforebuy;// #购买须知

		private String score;
		private String cheap;
		private String cheapname;

		private String price;// #活动价格
		private String hostname; // #活动举办方
		private String hostdate;// #活动举办时间
		private String label; // #主关键词，商品的第一个标签
		private String begintime;// #开始时间
		private String endtime;// #结束时间
		private String closetime;// #报名截止时间
		private String numnow;// #报名人数

		private String commentnum;// #总评论人数
		private Comment comment;
		private List<Item> item;
		private PriceList pricelist;

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

		public String getImages() {
			return images;
		}

		public void setImages(String images) {
			this.images = images;
		}

		public String getSummary() {
			return summary;
		}

		public void setSummary(String summary) {
			this.summary = summary;
		}

		public String getOldprice() {
			return oldprice;
		}

		public void setOldprice(String oldprice) {
			this.oldprice = oldprice;
		}

		public String getTel() {
			return tel;
		}

		public void setTel(String tel) {
			this.tel = tel;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getLng() {
			return lng;
		}

		public void setLng(String lng) {
			this.lng = lng;
		}

		public String getLat() {
			return lat;
		}

		public void setLat(String lat) {
			this.lat = lat;
		}

		public String getFavorite() {
			return favorite;
		}

		public void setFavorite(String favorite) {
			this.favorite = favorite;
		}

		public String getAllnum() {
			return allnum;
		}

		public void setAllnum(String allnum) {
			this.allnum = allnum;
		}

		public String getBeforebuy() {
			return beforebuy;
		}

		public void setBeforebuy(String beforebuy) {
			this.beforebuy = beforebuy;
		}

		public String getScore() {
			return score;
		}

		public void setScore(String score) {
			this.score = score;
		}

		public String getCheap() {
			return cheap;
		}

		public void setCheap(String cheap) {
			this.cheap = cheap;
		}

		public String getCheapname() {
			return cheapname;
		}

		public void setCheapname(String cheapname) {
			this.cheapname = cheapname;
		}

		public String getPrice() {
			return price;
		}

		public void setPrice(String price) {
			this.price = price;
		}

		public String getHostname() {
			return hostname;
		}

		public void setHostname(String hostname) {
			this.hostname = hostname;
		}

		public String getHostdate() {
			return hostdate;
		}

		public void setHostdate(String hostdate) {
			this.hostdate = hostdate;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
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

		public String getClosetime() {
			return closetime;
		}

		public void setClosetime(String closetime) {
			this.closetime = closetime;
		}

		public String getNumnow() {
			return numnow;
		}

		public void setNumnow(String numnow) {
			this.numnow = numnow;
		}

		public String getCommentnum() {
			return commentnum;
		}

		public void setCommentnum(String commentnum) {
			this.commentnum = commentnum;
		}

		public Comment getComment() {
			return comment;
		}

		public void setComment(Comment comment) {
			this.comment = comment;
		}

		public List<Item> getItem() {
			return item;
		}

		public void setItem(List<Item> item) {
			this.item = item;
		}


		public PriceList getPricelist() {
			return pricelist;
		}

		public void setPricelist(PriceList pricelist) {
			this.pricelist = pricelist;
		}


		public class Comment {
			private String countid;
			private String icon;
			private String name;
			private String time;
			private String content;
			private String score;

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

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getTime() {
				return time;
			}

			public void setTime(String time) {
				this.time = time;
			}

			public String getContent() {
				return content;
			}

			public void setContent(String content) {
				this.content = content;
			}

			public String getScore() {
				return score;
			}

			public void setScore(String score) {
				this.score = score;
			}
		}

		public class Item {
			private String name;
			private List<Cheap> cheap;

			public class Cheap {
				private String text;

				public String getText() {
					return text;
				}

				public void setText(String text) {
					this.text = text;
				}
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public List<Cheap> getCheap() {
				return cheap;
			}

			public void setCheap(List<Cheap> cheap) {
				this.cheap = cheap;
			}
		}

		public class PriceList {
			private List<DatePrice> dateprice;
			public class DatePrice{
				private String id;
				private String name;
				private String begintime;
				private String endtime;
				private String price;

				public String getId() {
					return id;
				}

				public void setId(String id) {
					this.id = id;
				}

				public String getName() {
					return name;
				}

				public void setName(String name) {
					this.name = name;
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
			}
			public List<DatePrice> getDateprice() {
				return dateprice;
			}
			public void setDateprice(List<DatePrice> dateprice) {
				this.dateprice = dateprice;
			}

		}
	}

}
