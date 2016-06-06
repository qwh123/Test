package com.hp.dao;

import java.util.List;

import com.google.gson.Gson;
import com.hp.bean.UOrderinfoList;
import com.hp.bean.UOrderinfoList.Data;

public class UOrderInfoDaoImpl implements UOrderInfoDao {

	@Override
	public List<Data> loadOrderList(String json) {
		Gson gson=new Gson();
		UOrderinfoList mInfo=gson.fromJson(json, UOrderinfoList.class);
		List<Data> data=mInfo.getData();
		return data;
	}

}
