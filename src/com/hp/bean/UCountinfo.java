package com.hp.bean;

public class UCountinfo {
	private int id;
	/** 微博账号 */
	private String sinaid;
	/** 微信账号 */
	private String wxid;
	/** QQ账号 */
	private String qqid;
	/** 密码 */
	private String pwd;
	/** 手机号 */
	private String tel;
	/** 第三方获取的昵称 */
	private String nickname;
	/**从第三方获取的头像*/
	private String icon;
	
	private String Email;//用户email
	private int state;//账户状态表示，0为正常

	public UCountinfo() {
	}

	public UCountinfo(int id, String sinaid, String wxid, String qqid,
			String pwd, String tel, String nickname, String icon) {
		super();
		this.id = id;
		this.sinaid = sinaid;
		this.wxid = wxid;
		this.qqid = qqid;
		this.pwd = pwd;
		this.tel = tel;
		this.nickname = nickname;
		this.icon = icon;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the sinaid
	 */
	public String getSinaid() {
		return sinaid;
	}

	/**
	 * @param sinaid the sinaid to set
	 */
	public void setSinaid(String sinaid) {
		this.sinaid = sinaid;
	}

	/**
	 * @return the wxid
	 */
	public String getWxid() {
		return wxid;
	}

	/**
	 * @param wxid the wxid to set
	 */
	public void setWxid(String wxid) {
		this.wxid = wxid;
	}

	/**
	 * @return the qqid
	 */
	public String getQqid() {
		return qqid;
	}

	/**
	 * @param qqid the qqid to set
	 */
	public void setQqid(String qqid) {
		this.qqid = qqid;
	}

	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}


}
