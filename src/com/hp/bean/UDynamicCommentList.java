package com.hp.bean;

import java.util.List;

/**
 * 获取动态详情
 * 
 * @author qwh
 * 
 */
public class UDynamicCommentList {
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
		private int id;// 评论id
		private String countid;
		private int pid;// 父级id
		private String nickname;
		private String date;
		private String contents;
		private String icon;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getCountid() {
			return countid;
		}

		public void setCountid(String countid) {
			this.countid = countid;
		}


		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getContents() {
			return contents;
		}

		public void setContents(String contents) {
			this.contents = contents;
		}

		public String getIcon() {
			return icon;
		}

		public void setIcon(String icon) {
			this.icon = icon;
		}

		public int getPid() {
			return pid;
		}

		public void setPid(int pid) {
			this.pid = pid;
		}
	}
}
