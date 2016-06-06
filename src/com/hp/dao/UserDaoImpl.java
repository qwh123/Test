package com.hp.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.hp.bean.UserInfo;
import com.hp.bean.UserInfo.Data;
import com.hp.utils.HPDBHelper;

public class UserDaoImpl implements UserDao {
	private Context mContext;

	public UserDaoImpl() {
	}

	public UserDaoImpl(Context mContext) {
		this.mContext = mContext;
	}

	/**
	 * 通过网络链接加载用户资料
	 */
	@Override
	public Data loadUserToNet(String json) {
		Gson gson = new Gson();
		UserInfo mUser = gson.fromJson(json, UserInfo.class);
		Data result = mUser.getData();
		return result;
	}

	/**
	 * 通过读取数据库加载用户资料
	 */
	@Override
	public Data loadUserToDB() {
		HPDBHelper helper = new HPDBHelper(mContext);
		SQLiteDatabase db = helper.getReadableDatabase();

		Cursor cursor = db.rawQuery("select * from t_user ", null);
		UserInfo.Data user = new Data();
		while (cursor.moveToNext()) {
			user.setCountid(cursor.getInt(cursor.getColumnIndex("count_id")));
			user.setNickname(cursor.getString(cursor
					.getColumnIndex("user_nickname")));
			user.setSex(cursor.getInt(cursor.getColumnIndex("user_sex")));
			user.setIcon(cursor.getString(cursor.getColumnIndex("user_icon")));
			user.setIndustry(cursor.getString(cursor
					.getColumnIndex("user_industry")));
			user.setJob(cursor.getString(cursor.getColumnIndex("user_job")));
			user.setSchool(cursor.getString(cursor
					.getColumnIndex("user_school")));
			user.setBorndate(cursor.getString(cursor
					.getColumnIndex("user_borndate")));
			user.setStarid(cursor.getString(cursor
					.getColumnIndex("user_starid")));
			user.setBtypeid(cursor.getString(cursor
					.getColumnIndex("user_btypeid")));
			user.setPersign(cursor.getString(cursor
					.getColumnIndex("user_persign")));
		}

		return user;
	}

	/**
	 * 添加用户资料
	 */
	@Override
	public void addUser(Data user) {

		HPDBHelper helper = new HPDBHelper(mContext);
		SQLiteDatabase db = helper.getWritableDatabase();

		db.execSQL(
				"insert into t_user (count_id,user_nickname,user_sex,user_icon,user_industry,"
						+ "user_job,user_school,user_borndate,user_starid,user_btypeid,user_persign) "
						+ "values(?,?,?,?,?,?,?,?,?,?,?)",
				new Object[] { user.getCountid(), user.getNickname(),
						user.getSex(), user.getIcon(), user.getIndustry(),
						user.getJob(), user.getSchool(), user.getBorndate(),
						user.getStarid(), user.getBtypeid(), user.getPersign()

				});

	}
}
