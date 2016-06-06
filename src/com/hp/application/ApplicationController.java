package com.hp.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.easemob.easeui.controller.EaseUI;
import com.hp.utils.hpCantant;
import com.hp.utils.volley.BitmapCache;

public class ApplicationController extends Application {
	// 经纬度
	private String lat;
	private String lng;
	private static final String TAG = ApplicationController.class
			.getSimpleName();

	private RequestQueue requestQueue;
	private ImageLoader imageLoader;

	private static ApplicationController mInstance;

	@Override
	public void onCreate() {
		super.onCreate();
		EaseUI.getInstance().init(this);
		mInstance = this;
	}

	public SharedPreferences getUser() {
		SharedPreferences sp = getSharedPreferences(hpCantant.USERINFO_DATA,
				Context.MODE_PRIVATE);
		if (sp == null) {
			return null;
		}
		return sp;
	}

	public static synchronized ApplicationController getInstance() {
		return mInstance;
	}

	public RequestQueue getRequestQueue() {
		if (requestQueue == null)
			requestQueue = Volley.newRequestQueue(getApplicationContext());
		return requestQueue;
	}

	public ImageLoader getImageLoader() {
		getRequestQueue();
		if (imageLoader == null) {
			imageLoader = new ImageLoader(requestQueue, new BitmapCache());
		}
		return imageLoader;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);

		getRequestQueue().add(req);
	}

	public void cancelPendingRequest(Object tag) {
		if (requestQueue != null) {
			requestQueue.cancelAll(tag);
		}
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}
}
