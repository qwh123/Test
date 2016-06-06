package com.tgj.Dao;

import java.util.List;

import com.tgj.bean.BOrderinfo;

public interface BOrderDao {
	public List<BOrderinfo.Data> loadNewOrderInfo(String json);
	public List<BOrderinfo.Data> loadSuccessOrderInfo(String json);
}
