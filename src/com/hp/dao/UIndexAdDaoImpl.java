package com.hp.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hp.bean.UIndexAd;
import com.hp.bean.UIndexAd.Data;

public class UIndexAdDaoImpl implements UIndexAdDao {

	@Override
	public Data loadUIndexAd(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, new TypeToken<UIndexAd.Data>() {
		}.getType());
	}

}
