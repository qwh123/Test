package com.hp.dao;

import com.google.gson.Gson;
import com.hp.bean.UGoodinfoDetail;
import com.hp.bean.UGoodinfoDetail.Data;

;
public class UGoodinfoDetailDao {
	public Data getGoodDetailInfo(String json) {
		Gson gson = new Gson();
		UGoodinfoDetail mDetilInfo = gson.fromJson(json, UGoodinfoDetail.class);
		Data result = mDetilInfo.getData();
		return result;
	}
}
