package com.hp.dao;

import java.util.List;

import com.google.gson.Gson;
import com.hp.bean.UFavoriteList;
/**
 * 获取 收藏列表
 * @author qwh
 *
 */
public class UFavoriteListDao {
	public List<UFavoriteList.Data> loadUFav(String json) {
		Gson gson = new Gson();
		UFavoriteList mInfo = gson.fromJson(json, UFavoriteList.class);
		List<UFavoriteList.Data> data = mInfo.getData();
		return data;
	}

}
