package com.hp.utils;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HPDBHelper extends SQLiteOpenHelper {



	public HPDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public HPDBHelper(Context context) {
		super(context, hpCantant.HP_DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// 创建用户基本资料表
		db.execSQL("create table t_user(user_id integer primary key autoincrement,"
				+ "count_id integer,"
				+ "constraint FK_USER foreign key (count_id) references t_count(count_id) on delete cascade on update cascade),"
				+ " user_nickname varchar(20),user_sex integer,user_icon varchar(30),"
				+ "user_industry varchar(30), user_job varchar(20),"
				+ "user_school varchar(30), user_borndate varchar(20),"
				+ "user_starid varchar(10), user_btypeid varchar(10),"
				+ "user_persign varchar(120)");
		// 创建账户资料表
		db.execSQL("create table t_count(count_id integer primary key autoincrement, "
				+ "count_sinaid varchar(30), count_wxid varchar(20),"
				+ "count_qqid varchar(30), count_pwd varchar(20),"
				+ "count_tel varchar(30), count_email varchar(10),"
				+ "count_state varchar(30)");

		Log.i("hp", "成功创建数据表!");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

		db.execSQL("drop table t_user");
		db.execSQL("drop table t_count");
		// 创建用户基本资料表
		db.execSQL("create table t_user(user_id integer primary key autoincrement,"
				+ "count_id integer,"
				+ "constraint FK_USER foreign key (count_id) references t_count(count_id) on delete cascade on update cascade),"
				+ " user_nickname varchar(20),user_sex integer,user_icon varchar(30),"
				+ "user_industry varchar(30), user_job varchar(20),"
				+ "user_school varchar(30), user_borndate varchar(20),"
				+ "user_starid varchar(10), user_btypeid varchar(10),"
				+ "user_persign varchar(120)");
		// 创建账户资料表
		db.execSQL("create table t_count(count_id integer primary key autoincrement, "
				+ "count_sinaid varchar(30), count_wxid varchar(20),"
				+ "count_qqid varchar(30), count_pwd varchar(20),"
				+ "count_tel varchar(30), count_email varchar(10),"
				+ "count_state varchar(30)");
		Log.i("hp", "更新成功!");

	}

}
