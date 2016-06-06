package com.tgj.Dao;

import java.util.List;

import com.google.gson.Gson;
import com.tgj.bean.BOrderinfo;
import com.tgj.bean.BOrderinfo.Data;
public class BOrderDaoImpl implements BOrderDao {

	@Override
	public List<Data> loadNewOrderInfo(String json) {
		Gson gson=new Gson();
		BOrderinfo mInfo=gson.fromJson(json, BOrderinfo.class);
		List<Data> data=mInfo.getData();
		return data;
	}


	@Override
	public List<Data> loadSuccessOrderInfo(String json) {
		return null;
	}

}
