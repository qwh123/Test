package com.hp.dao;

import java.util.List;

import org.json.JSONException;

import com.hp.bean.UBanner;
import com.hp.bean.UserInfo.Data;

public interface BannerDao {
	/*
	 * 加载轮播图
	 */
	List<UBanner> loadBanner(String jsonString);

	
	
}
