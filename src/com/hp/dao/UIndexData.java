package com.hp.dao;

import com.google.gson.Gson;
import com.hp.bean.UIndex;

public class UIndexData {
	/**
	 * 加载首页数据
	 * 
	 * @author qwh
	 * @param json
	 * @return
	 */
	public UIndex.Data loadUIndex(String json) {
		Gson gson = new Gson();
		UIndex mInfo = gson.fromJson(json, UIndex.class);
		UIndex.Data data = mInfo.getData();
		return data;
	}
}
