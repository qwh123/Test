package com.hp.utils;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.widget.Toast;

/**
 * 订单管理
 * 
 * @author qwh
 * 
 */
public class UOrderManager {
	public static int REFRESH = 1;
	private Context mContext;
	private ArrayMap<String, String> map;
	private Handler handler;

	/**
	 * 如果是删除订单操作则传countid orderid state=1就可以// 如果修改活动则countid orderid tel
	 * 如果要退款则传countid orderid, state=1011// 如果是修改轰趴馆则传 countid orderid priceid
	 * choicedate 和tel -
	 * 
	 * @param mHandler2
	 * 
	 */
	public UOrderManager(Context mContext, ArrayMap<String, String> map,
			Handler mHandler2) {
		this.mContext = mContext;
		this.map = map;
		handler = mHandler2;
		getData();
	}

	/**
	 * 获取数据
	 */
	private void getData() {
		if (map == null)
			return;
		CommonUtils.getData(mHandler, map, hpCantant.UOrderManager_URL,
				hpCantant.LABLE_UOrderManager);
	}

	private Handler mHandler = new Handler() {
		private JSONObject js;

		public void handleMessage(android.os.Message msg) {
			if (msg.what == hpCantant.LABLE_UOrderManager) {
				Bundle bundle = msg.getData();
				try {
					js = new JSONObject(bundle.getString(hpCantant.GETDATA));
					if (js.getString("code").equals(hpCantant.SUCCESS_CODE)) {
						Toast.makeText(mContext, js.getString("summary"),
								Toast.LENGTH_SHORT).show();
						Message message = new Message();
						message.what = REFRESH;
						handler.sendMessage(message);
					} else {
						Toast.makeText(mContext, "操作失败", Toast.LENGTH_SHORT)
								.show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
	};
}
