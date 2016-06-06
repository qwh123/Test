package com.hp.activity;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.widget.ListView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.hp.R;
import com.hp.application.ApplicationController;
import com.hp.bean.UMessageList;
import com.hp.bean.UMessageList.Data;
import com.hp.commonAdapter.CommonAdapter;
import com.hp.commonAdapter.ViewHolder;
import com.hp.dao.UMessageListDao;
import com.hp.utils.CommonUtils;
import com.hp.utils.hpCantant;
import com.hp.widget.CircleImageView;
import com.hp.widget.TopBar;
import com.hp.widget.TopBar.topBarClickListener;

public class MessageZanDetailActivity extends Activity {
	private String title;
	private int classid;
	private List<UMessageList.Data> mDatas;
	private UMessageListDao mDao;
	private CommonAdapter<UMessageList.Data> mAdapter;
	private ListView mListView;
	private Handler mHandler = new Handler() {
		private JSONObject js;

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == hpCantant.LABLE_UMessageList) {
				Bundle bundle = msg.getData();
				mDao = new UMessageListDao();
				try {
					js = new JSONObject(bundle.getString(hpCantant.GETDATA));
					if (js.getString("code").equals(hpCantant.SUCCESS_CODE)) {
						mDatas = mDao.loadMessage(bundle
								.getString(hpCantant.GETDATA));
						runOnUiThread(new Runnable() {
							public void run() {
								setData();
							}
						});
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	};
	private ImageLoader imageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_message_zan_detail);
		title = getIntent().getExtras().getString("title");
		classid = getIntent().getExtras().getInt("classid");
		mListView = (ListView) findViewById(R.id.lv_message_zan_detail);
		TopBar tBar = (TopBar) findViewById(R.id.topbar_message_zan);
		tBar.setTitleText(title);
		tBar.setRightIsVisable(false);
		tBar.setOnTopBarClickListener(new topBarClickListener() {
			@Override
			public void rightClick() {
			}

			@Override
			public void leftClick() {
				finish();
			}
		});
		getData();

	}

	protected void setData() {
		mAdapter = new CommonAdapter<UMessageList.Data>(
				MessageZanDetailActivity.this, mDatas,
				R.layout.item_square_dt_detail_comment) {
			@Override
			public void convert(ViewHolder helper, Data item) {
				helper.setImageByUrl(R.id.iv_squary_dt_comment_icon,
						item.getIcon());
				// helper.setCircleImageByUrl(R.id.iv_squary_dt_comment_icon,
				// item.getIcon());
				helper.setText(R.id.tv_squary_dt_comment_nickname,
						item.getNickname());
				helper.setText(R.id.tv_squary_dt_comment_time, item.getDate());
				helper.setText(R.id.tv_squary_dt_comment_contents,
						item.getContents());

			}
		};
		mListView.setAdapter(mAdapter);
	}

	private void getData() {
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("countid", ApplicationController.getInstance().getUser()
				.getInt("countid", 0)
				+ "");
		map.put("classid", classid + "");
		CommonUtils.getData(mHandler, map, hpCantant.UMessageList_URL,
				hpCantant.LABLE_UMessageList);
	}

	public void setImageByUrl1(int viewId, String url) {
		CircleImageView view = (CircleImageView) findViewById(viewId);
		if (imageLoader == null)
			imageLoader = ApplicationController.getInstance().getImageLoader();
		ImageListener listener = ImageLoader.getImageListener(view,
				R.drawable.user_null, R.drawable.user_null);
		imageLoader.get(url, listener);
	}

}
