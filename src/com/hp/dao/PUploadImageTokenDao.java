package com.hp.dao;

import com.google.gson.Gson;
import com.hp.bean.PUploadImageToken;

public class PUploadImageTokenDao {
	public PUploadImageToken.Data getQiNiuToken(String json) {
		Gson gson = new Gson();
		PUploadImageToken mInfo = gson.fromJson(json, PUploadImageToken.class);
		PUploadImageToken.Data data = mInfo.getData();
		return data;
	}
}
