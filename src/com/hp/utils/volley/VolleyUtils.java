package com.hp.utils.volley;

import android.os.Handler;
import android.support.v4.util.ArrayMap;
/**
 * volley获取json数据方法
 * @author qwh
 *
 */
public interface VolleyUtils {
	 void getRequestString(String url, Handler handler,int lable);
	 void getRequest2String(String url, Handler handler,ArrayMap<String, String> map,int lable);
}
