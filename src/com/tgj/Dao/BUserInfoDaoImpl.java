package com.tgj.Dao;

import com.google.gson.Gson;
import com.tgj.bean.BUserInfo;
import com.tgj.bean.BUserInfo.Data;

public class BUserInfoDaoImpl implements BUserInfoDao {

	@Override
	public Data loadUser(String json) {
		Gson gson = new Gson();
		BUserInfo mUser = gson.fromJson(json, BUserInfo.class);
		Data result = mUser.getData();
		return result;
	}

}
