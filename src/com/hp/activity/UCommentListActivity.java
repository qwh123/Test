package com.hp.activity;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.hp.R;
import com.hp.application.ApplicationController;
import com.hp.bean.UCommentList;
import com.hp.bean.UCommentList.Data;
import com.hp.commonAdapter.CommonAdapter;
import com.hp.commonAdapter.ViewHolder;
import com.hp.dao.UCommentListDao;
import com.hp.utils.CommonUtils;
import com.hp.utils.hpCantant;
import com.hp.widget.TopBar;
import com.hp.widget.TopBar.topBarClickListener;

public class UCommentListActivity extends Activity {
	private ListView mListView;
	private CommonAdapter<UCommentList.Data> mAdapter;
	UCommentListDao mDao;
	List<UCommentList.Data> mUFDatas;
	private Handler mHandler = new Handler() {
		private JSONObject js;

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == hpCantant.LABLE_UCOMMENTLIST) {
				Bundle bundle = msg.getData();
				mDao = new UCommentListDao();
				try {
					js = new JSONObject(bundle.getString(hpCantant.GETDATA));
					if (js.getString("code").equals(hpCantant.SUCCESS_CODE)) {
						mUFDatas = mDao.loadUFav(bundle
								.getString(hpCantant.GETDATA));
						if (mUFDatas != null && mUFDatas.size() > 0) {
							mAdapter = new CommonAdapter<UCommentList.Data>(
									UCommentListActivity.this, mUFDatas,
									R.layout.item_home_detail_comment) {

								@Override
								public void convert(final ViewHolder helper,
										final Data item) {
									runOnUiThread(new Runnable() {
										public void run() {
											initData(helper, item);
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
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_home_detail_comment);
		initView();
		getData();
	}

	private void getData() {
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("countid", ApplicationController.getInstance().getUser()
				.getInt("countid", 0)
				+ "");// 用户账号id
		// map.put("state", "0");// 订单状态
		map.put("pageindex", "1");// 页码
		map.put("pagesize", "20");// 每条页数
		CommonUtils.getData(mHandler, map, hpCantant.UCOMMENTLIST_URL,
				hpCantant.LABLE_UCOMMENTLIST);
	}

	private void initView() {
		TopBar tBar = (TopBar) findViewById(R.id.topbar_home_comment);
		tBar.setTitleText("评论列表");
		tBar.setOnTopBarClickListener(new topBarClickListener() {
			@Override
			public void rightClick() {
				Toast.makeText(UCommentListActivity.this, "click", 1).show();
			}

			@Override
			public void leftClick() {
				finish();
			}
		});
		mListView = (ListView) findViewById(R.id.lv_collect);
	}

	private void initData(ViewHolder helper, Data item) {
		helper.setText(R.id.tv_home_detail_comment_nickname, item.getNickname());
		helper.setText(R.id.tv_home_detail_comment_date, item.getDate());
		helper.setText(R.id.tv_home_detail_comment_content, item.getContent());
		if (Float.parseFloat(item.getScore()) >= 0
				|| Float.parseFloat(item.getScore()) <= 5) {
			helper.setRating(R.id.rbhome_detail_comment,
					Float.parseFloat(item.getScore()));
		} else {
			helper.setRating(R.id.rbhome_detail_comment, 0.0f);
		}
		helper.setImageByUrl(R.id.iv_home_detail_comment_icon, item.getIcon());

		helper.setOnClickListener(R.id.btn_home_detail_comment_dianzan,
				new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Toast.makeText(UCommentListActivity.this,
								"you click dianzan ", 1).show();
					}
				});
		helper.setOnClickListener(R.id.btn_home_detail_comment_jubao,
				new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Toast.makeText(UCommentListActivity.this,
								"you click ju bao ", 1).show();
					}
				});

	}
}
