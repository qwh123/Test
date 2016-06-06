package com.hp.utils.volley;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.hp.application.ApplicationController;
import com.hp.utils.hpCantant;

public class VolleyUtilsImpl implements VolleyUtils {

	@Override
	public void getRequestString(String url, final Handler handler,
			final int lable) {
		JsonObjectRequest request = new JsonObjectRequest(url, null,
				new Response.Listener<JSONObject>() {
					public void onResponse(JSONObject json) {
						try {
							if (json.getString("code").equals(
									hpCantant.SUCCESS_CODE)) {
								String js = json.getString("data");
								Log.i("json:", js);
								Bundle bundle = new Bundle();
								Message message = new Message();
								bundle.putString(hpCantant.GETDATA, js);
								message.setData(bundle);
								message.what = lable;
								handler.sendMessage(message);

							} else if (json.getString("code").equals(
									hpCantant.ERROR_CODE)) {
								Log.e(hpCantant.TAG, "错误代码");
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e(hpCantant.TAG, error.getMessage(), error);
						Bundle bundle = new Bundle();
						Message message = new Message();
						bundle.putString(hpCantant.GETDATA, hpCantant.ERROR_6);
						message.setData(bundle);
						message.what = lable;
						handler.sendMessage(message);
					}
				});
		ApplicationController.getInstance().addToRequestQueue(request);
	}

	@Override
	public void getRequest2String(String url, final Handler handler,
			final ArrayMap<String, String> map, final int lable) {
		StringRequest stringRequest = new StringRequest(Request.Method.POST,
				url, new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.i("respone:", response);
						Bundle bundle = new Bundle();
						Message message = new Message();
						bundle.putString(hpCantant.GETDATA, response);
						message.setData(bundle);
						message.what = lable;
						handler.sendMessage(message);

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e(hpCantant.TAG, error.getMessage(), error);
						Bundle bundle = new Bundle();
						Message message = new Message();
						bundle.putString(hpCantant.GETDATA, hpCantant.ERROR_6);
						message.setData(bundle);
						message.what = lable;
						handler.sendMessage(message);
					}
				}) {

			@Override
			protected ArrayMap<String, String> getParams()
					throws AuthFailureError {
				ArrayMap<String, String> mMap = new ArrayMap<String, String>();
				mMap = map;
				return mMap;
			}

		};
		ApplicationController.getInstance().addToRequestQueue(stringRequest);
		// JSONObject jsonObject = new JSONObject(map);
		// JsonObjectRequest request = new JsonObjectRequest(Method.POST, url,
		// jsonObject, new Response.Listener<JSONObject>() {
		// @Override
		// public void onResponse(JSONObject json) {
		// try {
		// if (json.getString("code").equals(
		// hpCantant.SUCCESS_CODE)) {
		// String js = json.getString("data");
		// Log.i("json:", js);
		// Bundle bundle = new Bundle();
		// Message message = new Message();
		// bundle.putString(hpCantant.GETDATA, js);
		// message.setData(bundle);
		// handler.sendMessage(message);
		//
		// } else if (json.getString("code").equals(
		// hpCantant.ERROR_CODE)) {
		// Log.e(hpCantant.TAG, "错误代码");
		// }
		//
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// }, new Response.ErrorListener() {
		// @Override
		// public void onErrorResponse(VolleyError error) {
		// Log.e(hpCantant.TAG, error.getMessage(), error);
		// }
		// }) {
		// @Override
		// public Map<String, String> getHeaders() throws AuthFailureError{
		// HashMap<String, String> headers = new HashMap<String, String>();
		// headers.put("Accept", "application/json");
		// headers.put("Content-Type", "application/json; charset=UTF-8");
		//
		// return headers;
		// }
		// };

	}

}
