package com.hp.utils;

import android.os.Handler;
import android.support.v4.util.ArrayMap;

public class GetGoodPrice {
	public GetGoodPrice(Handler mHandler, String goodid, int num, String price) {
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("num", num + "");// 数量
		map.put("goodid", goodid);// 商品id
		map.put("price", price);// 商品价格
		CommonUtils.getData(mHandler, map, hpCantant.UGetPrice_URL,
				hpCantant.LABLE_UGetPrice);
	}

	public GetGoodPrice(Handler mHandler, String goodid, int num, String price,
			String itemid) {
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("num", num + "");// 数量
		map.put("goodid", goodid);// 商品id
		map.put("price", price);// 商品价格
		map.put("itemid", itemid);//
		CommonUtils.getData(mHandler, map, hpCantant.UGetPrice_URL,
				hpCantant.LABLE_UGetPrice);
	}
}
