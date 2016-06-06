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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hp.R;
import com.hp.application.ApplicationController;
import com.hp.bean.UFavoriteList;
import com.hp.bean.UFavoriteList.Data;
import com.hp.commonAdapter.CommonAdapter;
import com.hp.commonAdapter.ViewHolder;
import com.hp.dao.UFavoriteListDao;
import com.hp.utils.CommonUtils;
import com.hp.utils.hpCantant;
import com.hp.widget.TopBar;
import com.hp.widget.TopBar.topBarClickListener;

public class UserCollectActivity extends Activity {
	private ListView mListView;
	private CommonAdapter<UFavoriteList.Data> mAdapter;
	UFavoriteListDao mDao;
	List<UFavoriteList.Data> mUFDatas;
	Handler mHandler = new Handler() {
		private JSONObject js;
		private boolean IsDel = false;

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == hpCantant.LABLE_UFAVORITELIST) {
				pgb.setVisibility(View.GONE);
				Bundle bundle = msg.getData();
				mDao = new UFavoriteListDao();
				try {
					js = new JSONObject(bundle.getString(hpCantant.GETDATA));
					if (js.getString("code").equals(hpCantant.SUCCESS_CODE)) {
						mUFDatas = mDao.loadUFav(bundle
								.getString(hpCantant.GETDATA));
						if (mUFDatas.size() > 0 || mUFDatas != null) {
							((TextView) findViewById(R.id.tv_user_collect_num))
									.setText("全部收藏(" + mUFDatas.size() + ")");
							mAdapter = new CommonAdapter<UFavoriteList.Data>(
									UserCollectActivity.this, mUFDatas,
									R.layout.item_collect_list) {

								@Override
								public void convert(ViewHolder helper,
										final Data item) {
									helper.setText(R.id.tv_collect_title,
											item.getTitle());
									helper.setText(R.id.tv_collect_time,
											item.getDate());
									helper.setImageByUrl(R.id.iv_collect_icon,
											item.getIcon());

									if (item.getClassid().equals("1")) {
										helper.setText(R.id.tv_collect_price,
												item.getAllprice() + "");
									} else {
										helper.setText(R.id.tv_collect_price,
												item.getPrice() + "");
									}
									helper.setOnClickListener(
											R.id.btn_collect_del,
											new OnClickListener() {
												@Override
												public void onClick(View arg0) {
													DelFavGoods(item.getId());
													getData();
												}
											});
								}
							};
							mAdapter.notifyDataSetChanged();
							mListView.setAdapter(mAdapter);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			if (msg.what == hpCantant.LABLE_UFAVORITE) {
				Bundle bundle = msg.getData();
				try {
					js = new JSONObject(bundle.getString(hpCantant.GETDATA));
					if (js.getString("code").equals(hpCantant.SUCCESS_CODE)) {
						Toast.makeText(UserCollectActivity.this,
								js.getString("summary"), 0).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	};
	private ProgressBar pgb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_user_collect);
		pgb = (ProgressBar) findViewById(R.id.pgb_user_collect);
		initView();
		getData();
	}

	private void getData() {
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("countid", ApplicationController.getInstance().getUser()
				.getInt("countid", 0)
				+ "");// 用户账号id
		map.put("pageindex", "1");// 页码
		map.put("pagesize", "20");// 每条页数
		CommonUtils.getData(mHandler, map, hpCantant.UFAVORITELIST_URL,
				hpCantant.LABLE_UFAVORITELIST);
	}

	private void initView() {
		TopBar tBar = (TopBar) findViewById(R.id.topbar_collect);
		tBar.setTitleText("我的收藏");
		tBar.setRightIsVisable(false);
		tBar.setOnTopBarClickListener(new topBarClickListener() {
			@Override
			public void rightClick() {
				Toast.makeText(UserCollectActivity.this, "click", 1).show();
			}

			@Override
			public void leftClick() {
				finish();
			}
		});
		mListView = (ListView) findViewById(R.id.lv_collect);

		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {

				Intent intent = new Intent(UserCollectActivity.this,
						GoodDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("imageurl", mUFDatas.get(position).getIcon());
				bundle.putString("id", mUFDatas.get(position).getId());
				String type = mUFDatas.get(position).getClassid();
				bundle.putString("classid", type + "");
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});
	}

	/**
	 * 删除收藏商品
	 */
	private void DelFavGoods(String id) {
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("countid", ApplicationController.getInstance().getUser()
				.getInt("countid", 0)
				+ "");// 用户id
		map.put("goodid", id);// 商品id
		CommonUtils.getData(mHandler, map, hpCantant.UFAVORITE_URL,
				hpCantant.LABLE_UFAVORITE);
	}
}
