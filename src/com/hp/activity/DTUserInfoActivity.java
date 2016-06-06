package com.hp.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.easemob.easeui.EaseConstant;
import com.easemob.easeui.domain.EaseUser;
import com.hp.R;
import com.hp.application.ApplicationController;
import com.hp.bean.UserInfo;
import com.hp.dao.UserDao;
import com.hp.dao.UserDaoImpl;
import com.hp.utils.CommonUtils;
import com.hp.utils.hpCantant;
import com.hp.widget.TopBar;
import com.hp.widget.TopBar.topBarClickListener;

public class DTUserInfoActivity extends Activity {
	private ImageView ivIcon;
	private ImageButton ibtnChat;

	private String id, nickname;
	private EaseUser user = null;

	UserInfo.Data mData;
	UserDao mDao;
	private Handler mHandler = new Handler() {

		private JSONObject js;

		public void handleMessage(android.os.Message msg) {
			if (msg.what == hpCantant.LABLE_UUserinfo) {
				mDao = new UserDaoImpl();
				Bundle bundle = msg.getData();
				try {
					js = new JSONObject(bundle.getString(hpCantant.GETDATA));
					if (js.getString("code").equals(hpCantant.SUCCESS_CODE)) {
						mData = mDao.loadUserToNet(bundle
								.getString(hpCantant.GETDATA));
						runOnUiThread(new Runnable() {
							public void run() {
								initView();
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
		setContentView(R.layout.aty_dt_userinfo);
		id = getIntent().getExtras().getString("userid");
		Log.i(getPackageName(), id);
		nickname = getIntent().getExtras().getString("nickname");
		TopBar tBar = (TopBar) findViewById(R.id.topbar_dtui);
		tBar.setTitleText(nickname);
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
		getInfo();
	}

	private void getInfo() {
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("countid", id);
		CommonUtils.getData(mHandler, map, hpCantant.UUserinfo_URL,
				hpCantant.LABLE_UUserinfo);
	}

	private void initView() {
		ivIcon = (ImageView) findViewById(R.id.iv_dt_userinfo);
		if (imageLoader == null)
			imageLoader = ApplicationController.getInstance().getImageLoader();
		ImageListener listener = ImageLoader.getImageListener(ivIcon,
				R.drawable.user_null, R.drawable.user_null);
		imageLoader.get(mData.getIcon(), listener);

		setText(R.id.tv_dt_userinfo_nickname, mData.getNickname() + " ");
		setText(R.id.tv_dt_userinfo_nickname1, mData.getNickname() + " ");
		setText(R.id.tv_dt_userinfo_id, "ID:" + mData.getCountid() + " ");
		if (mData.getSex() == 0) {
			setText(R.id.tv_dt_userinfo_sex, "保密");
		} else if (mData.getSex() == 1) {
			setText(R.id.tv_dt_userinfo_sex, "男");
		} else if (mData.getSex() == 2) {
			setText(R.id.tv_dt_userinfo_sex, "女");
		}
		setText(R.id.tv_dt_userinfo_adress, mData.getSchool() + " ");
		setText(R.id.tv_dt_userinfo_per, mData.getPersign() + " ");
		ibtnChat = (ImageButton) findViewById(R.id.btn_dt_userinfo_chat);
		ibtnChat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (ApplicationController.getInstance().getUser()
						.getInt("countid", 0) == 0) {
					startActivity(new Intent(DTUserInfoActivity.this,
							UserLoginActivity.class));
					finish();
				} else {
					if (user == null) {
						user = new EaseUser(id + id);
						user.setNick(nickname);
					}
					startActivity(new Intent(DTUserInfoActivity.this,
							ChatActivity.class).putExtra(
							EaseConstant.EXTRA_USER_ID, user.getUsername()));
				}
			}
		});
	}

	private void setText(int id, String Text) {
		TextView view = (TextView) findViewById(id);
		view.setText(Text);
	}
}
