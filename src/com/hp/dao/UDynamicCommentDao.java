package com.hp.dao;

import java.util.List;

import com.google.gson.Gson;
import com.hp.bean.UDynamicCommentList;

/**
 * 获取动态详情
 * 
 * @author qwh
 * 
 */
public class UDynamicCommentDao {
	public List<UDynamicCommentList.Data> getComments(String json) {
		Gson gson = new Gson();
		UDynamicCommentList mInfo = gson.fromJson(json,
				UDynamicCommentList.class);
		List<UDynamicCommentList.Data> data = mInfo.getData();
		return data;
	}
}
