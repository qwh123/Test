package com.tgj.Dao;

import com.google.gson.Gson;
import com.tgj.bean.BGetMoney;
import com.tgj.bean.BGetMoney.Data;

/**
 * 获取商家收入
 * @author qwh
 *
 */
public class BGetMoneyDao {
	public BGetMoney.Data getBGetMoney(String json){
		Gson gson=new Gson();
		BGetMoney mInfo=gson.fromJson(json, BGetMoney.class);
		Data data=mInfo.getData();
		return data;
	}
}
