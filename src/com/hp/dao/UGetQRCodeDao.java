package com.hp.dao;

import com.google.gson.Gson;
import com.hp.bean.UGetQRCode;
/**
 * 获取二维码和验码
 * @author qwh
 *
 */
public class UGetQRCodeDao {
	public UGetQRCode.Data getUGetQRCode(String json) {
		Gson gson = new Gson();
		UGetQRCode mInfo = gson.fromJson(json, UGetQRCode.class);
		UGetQRCode.Data data = mInfo.getData();
		return data;
	}
}
