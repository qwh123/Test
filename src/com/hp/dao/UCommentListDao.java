package com.hp.dao;

import java.util.List;

import com.google.gson.Gson;
import com.hp.bean.UCommentList;
/**
 * 获取UCommentList数据
 * @author qwh
 *
 */
public class UCommentListDao {
	public List<UCommentList.Data> loadUFav(String json) {
		Gson gson = new Gson();
		UCommentList mInfo = gson.fromJson(json, UCommentList.class);
		List<UCommentList.Data> data = mInfo.getData();
		return data;
	}
}
