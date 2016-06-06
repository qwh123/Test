package com.hp.bean;


public class UserInfo {
	private String code;
	private String summary;
	private Data data;

	public static class Data {
		private int countid;// 用户账户id
		private String nickname;// 用户昵称
		private int sex;// 用户性别，0保密，1男2女
		private String icon;// 用户头像
		private String industry;// 用户行业
		private String job;// 用户职业
		private String school;// 用户学校
		private String borndate;// 用户出生日期
		private String starid;// 用户星座id（0保密，十二个星座，直接存在本地）
		private String btypeid;// 用户血型id（0保密，A,AB,0,B,存在本地）
		private String persign;// 用户个性签名
		private int state;// 用户状态标识，0为正常
		
		public int getCountid() {
			return countid;
		}
		public void setCountid(int countid) {
			this.countid = countid;
		}
		public String getNickname() {
			return nickname;
		}
		public void setNickname(String nickname) {
			this.nickname = nickname;
		}
		public int getSex() {
			return sex;
		}
		public void setSex(int sex) {
			this.sex = sex;
		}
		public String getIcon() {
			return icon;
		}
		public void setIcon(String icon) {
			this.icon = icon;
		}
		public String getIndustry() {
			return industry;
		}
		public void setIndustry(String industry) {
			this.industry = industry;
		}
		public String getJob() {
			return job;
		}
		public void setJob(String job) {
			this.job = job;
		}
		public String getSchool() {
			return school;
		}
		public void setSchool(String school) {
			this.school = school;
		}
		public String getBorndate() {
			return borndate;
		}
		public void setBorndate(String borndate) {
			this.borndate = borndate;
		}
		public String getStarid() {
			return starid;
		}
		public void setStarid(String starid) {
			this.starid = starid;
		}
		public String getBtypeid() {
			return btypeid;
		}
		public void setBtypeid(String btypeid) {
			this.btypeid = btypeid;
		}
		public String getPersign() {
			return persign;
		}
		public void setPersign(String persign) {
			this.persign = persign;
		}
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
	
//		private static SharedPreferences mSharedPreferences;
//		public static final String USERINFO_DATA="userinfo_data";
//		public static Data mData;
//		private static SharedPreferences.Editor editor;
//		public Data() {
//		}
//		public Data(Context mContext) {
//			mSharedPreferences=mContext.getSharedPreferences(USERINFO_DATA, mContext.MODE_PRIVATE);
//		}
//		/**
//		 * 单例模式，获取instance实例
//		 * 
//		 * @param cxt
//		 * @return
//		 */
//		public static Data getInstance(Context cxt) {
//			if (mData == null) {
//				mData = new Data(cxt);
//			}
//			editor = mSharedPreferences.edit();
//			return mData;
//		}

//		public int getCountid() {
//			return mSharedPreferences.getInt("countid", 0);
//		}
//
//		public void setCountid(int countid) {
//			editor.putInt("countid", countid);
//			editor.commit();
//		}
//
//		public String getNickname() {
//			return mSharedPreferences.getString("nickname", "");
//		}
//
//		public void setNickname(String nickname) {
//			editor.putString("nickname", nickname);
//			editor.commit();
//		}
//
//		public int getSex() {
//			return mSharedPreferences.getInt("sex", 0);
//		}
//
//		public void setSex(int sex) {
//			editor.putInt("sex", sex);
//			editor.commit();
//		}
//
//		public String getIcon() {
//			return mSharedPreferences.getString("icon", "");
//		}
//
//		public void setIcon(String icon) {
//			editor.putString("icon", icon);
//			editor.commit();
//		}
//
//		public String getIndustry() {
//			return mSharedPreferences.getString("industry", "");
//		}
//
//		public void setIndustry(String industry) {
//			editor.putString("industry", industry);
//			editor.commit();
//		}
//
//		public String getJob() {
//			return  mSharedPreferences.getString("job", "");
//		}
//
//		public void setJob(String job) {
//			editor.putString("job", job);
//			editor.commit();
//		}
//
//		public String getSchool() {
//			return  mSharedPreferences.getString("school", "");
//		}
//
//		public void setSchool(String school) {
//			editor.putString("school", school);
//			editor.commit();
//		}
//
//		public String getBorndate() {
//			return  mSharedPreferences.getString("borndate", "");
//		}
//
//		public void setBorndate(String borndate) {
//			editor.putString("borndate", borndate);
//			editor.commit();
//		}
//
//		public String getStarid() {
//			return  mSharedPreferences.getString("starid", "");
//		}
//
//		public void setStarid(String starid) {
//			editor.putString("starid", starid);
//			editor.commit();
//		}
//
//		public String getBtypeid() {
//			return  mSharedPreferences.getString("btypeid", "");
//		}
//
//		public void setBtypeid(String btypeid) {
//			editor.putString("btypeid", btypeid);
//			editor.commit();
//		}
//
//		public String getPersign() {
//			return  mSharedPreferences.getString("persign", "");
//		}
//
//		public void setPersign(String persign) {
//			editor.putString("persign", persign);
//			editor.commit();
//		}
//
//		public int getState() {
//			return mSharedPreferences.getInt("state", 0);
//		}
//
//		public void setState(int state) {
//			editor.putInt("state", state);
//		}
	}

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

}
