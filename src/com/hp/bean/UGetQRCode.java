package com.hp.bean;
/**
 * 获取二维码和验码的model
 * @author qwh
 *
 */
public class UGetQRCode {
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
		private String begintime;
		private String endtime;
		private String code;

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

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

	}
}
