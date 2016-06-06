package com.hp.bean;

import java.util.List;

/**
 * UActiveDetail model
 * 
 * @author qwh
 * 
 */
public class UActiveDetail {
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
		private String id;
		private String icon;
		private String title;
		private String summary;
		private String num;
		private String begintime;
		private String endtime;
		private String lat;
		private String lng;
		private String address;
		private String countid;
		private String member;
		private HostInfo hostinfo;
		private String groupid;

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


		public String getNum() {
			return num;
		}

		public void setNum(String num) {
			this.num = num;
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

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getCountid() {
			return countid;
		}

		public void setCountid(String countid) {
			this.countid = countid;
		}

		public String getMember() {
			return member;
		}

		public void setMember(String member) {
			this.member = member;
		}

		public HostInfo getHostinfo() {
			return hostinfo;
		}

		public void setHostinfo(HostInfo hostinfo) {
			this.hostinfo = hostinfo;
		}

		public List<Member_list> getMember_list() {
			return member_list;
		}

		public void setMember_list(List<Member_list> member_list) {
			this.member_list = member_list;
		}

		public String getSummary() {
			return summary;
		}

		public void setSummary(String summary) {
			this.summary = summary;
		}

		public String getGroupid() {
			return groupid;
		}

		public void setGroupid(String groupid) {
			this.groupid = groupid;
		}

		public String getIcon() {
			return icon;
		}

		public void setIcon(String icon) {
			this.icon = icon;
		}

		public class HostInfo {
			private String id;
			private String icon;
			private String nickname;

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

			public String getNickname() {
				return nickname;
			}

			public void setNickname(String nickname) {
				this.nickname = nickname;
			}
		}

		private List<Member_list> member_list;

		public class Member_list {
			private String id;
			private String icon;

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
		}
	}
}
