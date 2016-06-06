package com.hp.activity;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.hp.R;
import com.hp.application.ApplicationController;
import com.hp.bean.UGoodInfoList;
import com.hp.bean.UGoodInfoList.Data;
import com.hp.commonAdapter.CommonAdapter;
import com.hp.commonAdapter.ViewHolder;
import com.hp.dao.UGoodInfoDao;
import com.hp.utils.CommonUtils;
import com.hp.utils.hpCantant;
import com.hp.widget.TopBar;
import com.hp.widget.TopBar.topBarClickListener;

public class LableDetailActivity extends Activity {
	private String lableName;
	private String lableid;
	private ListView lvLable;
	private CommonAdapter<UGoodInfoList.Data> mAdapter;
	private List<UGoodInfoList.Data> mList;
	private UGoodInfoDao mDao;
	private Handler mHandler = new Handler() {
		private JSONObject js;

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == hpCantant.LABLE_UGOODINFO) {
				Bundle bundle = msg.getData();
				mDao = new UGoodInfoDao();
				try {
					js = new JSONObject(bundle.getString(hpCantant.GETDATA));
					if (js.getString("code").equals(hpCantant.SUCCESS_CODE)) {
						mList = mDao.loadGood(bundle
								.getString(hpCantant.GETDATA));
						if (mList.size() > 0)
							new initGoodsView(LableDetailActivity.this,
									lvLable, mList);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lable_detail);
		lvLable = (ListView) findViewById(R.id.lv_lable);
		lableName = getIntent().getExtras().getString("lableName");
		lableid = getIntent().getExtras().getString("lableid");
		TopBar tBar = (TopBar) findViewById(R.id.topbar_label);
		tBar.setTitleText(lableName);
		tBar.setRighttImageResource(R.drawable.home_search_big);
		tBar.setOnTopBarClickListener(new topBarClickListener() {
			@Override
			public void rightClick() {
				Intent intent = new Intent(LableDetailActivity.this,
						SearchActivity.class);
				startActivity(intent);
				finish();
			}

			@Override
			public void leftClick() {
				finish();
			}
		});
		getData();
	}

	private void getData() {
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("labelid", lableid);
		map.put("lat", ApplicationController.getInstance().getLat());
		map.put("lng", ApplicationController.getInstance().getLng());
		map.put("pageindex", "1");
		map.put("pagesize", "5");
		Log.i(getPackageName(), map.toString());
		CommonUtils.getData(mHandler, map, hpCantant.UGOODINFO_URL,
				hpCantant.LABLE_UGOODINFO);
	}

}
