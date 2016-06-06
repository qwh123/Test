package com.hp.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hp.bean.UCities;

public class UCitiesDaoImpl implements UCitiesDao {

	@Override
	public UCities loadCity(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, new TypeToken<UCities>() {
		}.getType());
	}

}
