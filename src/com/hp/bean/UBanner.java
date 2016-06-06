package com.hp.bean;


/**
 * 获取广告信息
 * 
 * @author qwh
 * 
 */
public class UBanner {
	private int id;
	private String imagelink;// 广告图片链接
	private String detaillink;// 广告详细链接

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImagelink() {
		return imagelink;
	}

	public void setImagelink(String imagelink) {
		this.imagelink = imagelink;
	}

	public String getDetaillink() {
		return detaillink;
	}

	public void setDetaillink(String detaillink) {
		this.detaillink = detaillink;
	}

}
