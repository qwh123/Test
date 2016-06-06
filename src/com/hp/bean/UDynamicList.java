package com.hp.bean;

import java.util.List;

/**
 * 获取动态列表
 * @author qwh
 *
 */
public class UDynamicList {
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
	public class Data{
		private String id;
		private String nickname;
		private String classid;
		private String icon;
		private String countid;
		private String contents;
		private String date;
		private String likes;
		private String lat;
		private String lng;
		private String images;
		private String goodicon;
		private String goodtitle;
		private String goodid;
		private String goodclass;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getNickname() {
			return nickname;
		}
		public void setNickname(String nickname) {
			this.nickname = nickname;
		}
		public String getClassid() {
			return classid;
		}
		public void setClassid(String classid) {
			this.classid = classid;
		}
		public String getIcon() {
			return icon;
		}
		public void setIcon(String icon) {
			this.icon = icon;
		}
		public String getCountid() {
			return countid;
		}
		public void setCountid(String countid) {
			this.countid = countid;
		}
		public String getContents() {
			return contents;
		}
		public void setContents(String contents) {
			this.contents = contents;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getLikes() {
			return likes;
		}
		public void setLikes(String likes) {
			this.likes = likes;
		}
		public String getLat() {
			return lat;
		}
		public void setLat(String lat) {
			this.lat = lat;
		}
		public String getLng() {
			return lng;
		}
		public void setLng(String lng) {
			this.lng = lng;
		}
		public String getImages() {
			return images;
		}
		public void setImages(String images) {
			this.images = images;
		}
		public String getGoodicon() {
			return goodicon;
		}
		public void setGoodicon(String goodicon) {
			this.goodicon = goodicon;
		}
		public String getGoodtitle() {
			return goodtitle;
		}
		public void setGoodtitle(String goodtitle) {
			this.goodtitle = goodtitle;
		}
		public String getGoodid() {
			return goodid;
		}
		public void setGoodid(String goodid) {
			this.goodid = goodid;
		}
		public String getGoodclass() {
			return goodclass;
		}
		public void setGoodclass(String goodclass) {
			this.goodclass = goodclass;
		}
		
		
		
		
	}

}
