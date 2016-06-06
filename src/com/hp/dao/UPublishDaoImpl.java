package com.hp.dao;

import java.util.List;

import com.google.gson.Gson;
import com.hp.bean.UGoodInfoList;
import com.hp.bean.UPublish;
import com.hp.bean.UPublish.Data;

public class UPublishDaoImpl implements UPublishDao {

	@Override
	public List<Data> loadUPublish(String json) {
		Gson gson=new Gson();
		UPublish mInfo=gson.fromJson(json, UPublish.class);
		List<UPublish.Data> data=mInfo.getData();
		return data;
	}

}
