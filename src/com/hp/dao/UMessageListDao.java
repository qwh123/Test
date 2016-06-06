package com.hp.dao;

import java.util.List;

import com.google.gson.Gson;
import com.hp.bean.UMessageList;
import com.hp.bean.UMessageList.Data;

public class UMessageListDao {
	public List<UMessageList.Data> loadMessage(String jsonString) {
		Gson gson = new Gson();
		UMessageList mInfo = gson.fromJson(jsonString, UMessageList.class);
		List<Data> data = mInfo.getData();
		return data;
	}
}
