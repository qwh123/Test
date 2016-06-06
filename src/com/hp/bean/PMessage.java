package com.hp.bean;

import java.util.Date;

/**
 * 获取信息和发送信息
 * @author qwh
 *
 */
public class PMessage {
	private int id;
	private Date date;
	private String content;//发送消息正文
	private String countid;//发送用户id
	private String username;//发送用户昵称
	private String icon;//发送用户头像
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCountid() {
		return countid;
	}
	public void setCountid(String countid) {
		this.countid = countid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
}
