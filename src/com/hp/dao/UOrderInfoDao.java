package com.hp.dao;

import java.util.List;

import com.hp.bean.UOrderinfoList;

public interface UOrderInfoDao {
	List<UOrderinfoList.Data> loadOrderList(String json);
}
