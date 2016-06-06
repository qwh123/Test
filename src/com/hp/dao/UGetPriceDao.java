package com.hp.dao;

import com.google.gson.Gson;
import com.hp.bean.UGetPrice;

public class UGetPriceDao {
	public UGetPrice.Data GetPrice(String json) {
		Gson gson = new Gson();
		UGetPrice mInfo = gson.fromJson(json, UGetPrice.class);
		UGetPrice.Data data = mInfo.getData();
		return data;
	}

}
