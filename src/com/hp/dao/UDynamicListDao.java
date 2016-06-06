package com.hp.dao;

import java.util.List;

import com.google.gson.Gson;
import com.hp.bean.UDynamicList;

public class UDynamicListDao {
	/**
	 * 获取动态列表
	 * @param jsonString
	 * @return
	 */
	public List<UDynamicList.Data> loadUDynamic(String jsonString) {
		Gson gson = new Gson();
		UDynamicList mInfo = gson.fromJson(jsonString, UDynamicList.class);
		List<UDynamicList.Data> data = mInfo.getData();
		return data;
	}

}
