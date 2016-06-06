package com.hp.dao;

import com.hp.bean.UserInfo;
import com.hp.bean.UserInfo.Data;

public interface UserDao {
	/*
	 * 加载用户
	 */
	Data loadUserToNet(String jsonString);
	Data loadUserToDB();
	void addUser(UserInfo.Data user);
}
