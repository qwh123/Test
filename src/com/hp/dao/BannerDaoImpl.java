package com.hp.dao;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hp.bean.UBanner;

public class BannerDaoImpl implements BannerDao {

	@Override
	public List<UBanner> loadBanner(String jsonString) {
		Gson gson = new Gson();
		return gson.fromJson(jsonString, new TypeToken<List<UBanner>>() {
		}.getType());
	}

}
