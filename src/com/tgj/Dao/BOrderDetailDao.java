package com.tgj.Dao;

import com.google.gson.Gson;
import com.tgj.bean.BOrderinfoDetail;
import com.tgj.bean.BOrderinfoDetail.Data;

;
public class BOrderDetailDao {
	public BOrderinfoDetail.Data getOrderDetail(String json) {
		Gson gson = new Gson();
		BOrderinfoDetail mUser = gson.fromJson(json, BOrderinfoDetail.class);
		Data result = mUser.getData();
		return result;
	}
}
