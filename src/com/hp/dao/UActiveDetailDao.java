package com.hp.dao;

import com.google.gson.Gson;
import com.hp.bean.UActiveDetail;
import com.hp.bean.UActiveDetail.Data;

;
public class UActiveDetailDao {
	public UActiveDetail.Data loadActiveDetail(String jsonString) {
		Gson gson = new Gson();
		UActiveDetail mInfo = gson.fromJson(jsonString, UActiveDetail.class);
		Data data = mInfo.getData();
		return data;
	}
}
