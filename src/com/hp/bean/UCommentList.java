package com.hp.bean;

import java.util.Date;
import java.util.List;

/**
 * 获取评论列表
 * 
 * @author qwh
 * 
 */
public class UCommentList {
	private String code;// 评论id
	private String summary;
	private List<Data> data;


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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public class Data {
		private String id; // 评论id
		private String score; // 评分
		private String content; // 评论正文
		private String date; // 评论时间
		private String countid; // 评论用户id
		private String icon; // 评论用户头像

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getScore() {
			return score;
		}

		public void setScore(String score) {
			this.score = score;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
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

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		private String nickname; // 评论用户昵称

	}
}
