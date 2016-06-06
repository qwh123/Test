package com.tgj.Dao;

import com.tgj.bean.BUserInfo;


public interface BUserInfoDao {
	BUserInfo.Data loadUser(String json);
}
