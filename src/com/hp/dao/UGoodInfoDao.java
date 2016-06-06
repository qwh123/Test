package com.hp.dao;

import java.util.List;

import com.google.gson.Gson;
import com.hp.bean.UGoodInfoList;
import com.hp.bean.UGoodInfoList.Data;
/**
 * 获取商品列表
 * @author qwh
 *
 */
public class UGoodInfoDao {
	public List<UGoodInfoList.Data> loadGood(String jsonString) {
		Gson gson = new Gson();
		UGoodInfoList mInfo = gson.fromJson(jsonString, UGoodInfoList.class);
		List<Data> data = mInfo.getData();
		return data;
	}
}
