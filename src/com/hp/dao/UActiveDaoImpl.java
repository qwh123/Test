package com.hp.dao;

import java.util.List;

import com.google.gson.Gson;
import com.hp.bean.UActiveList;
import com.hp.bean.UActiveList.Data;

public class UActiveDaoImpl implements UActiveDao {

	@Override
	public List<Data> loadActive(String jsonString) {
		Gson gson = new Gson();
		UActiveList mInfo = gson.fromJson(jsonString, UActiveList.class);
		List<Data> data = mInfo.getData();
		return data;
	}

}
