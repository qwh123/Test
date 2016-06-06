package com.hp.dao;

import com.google.gson.Gson;
import com.hp.bean.UJumpBussiness;
import com.hp.bean.UJumpBussiness.Data;
/**
 * 返回  判断是否商家
 * @author qwh
 *
 */
public class UJumpBussinessDao {
	public UJumpBussiness.Data loadUJB(String jsonString) {
		Gson gson = new Gson();
		UJumpBussiness mInfo = gson.fromJson(jsonString, UJumpBussiness.class);
		Data data = mInfo.getData();
		return data;
	}
}
