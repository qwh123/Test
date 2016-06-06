package com.hp.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.hp.R;
import com.hp.application.ApplicationController;
import com.hp.utils.CommonUtils;
import com.hp.utils.DataCleanManager;
import com.hp.utils.hpCantant;
import com.hp.widget.TopBar;
import com.hp.widget.TopBar.topBarClickListener;

public class UserSettingActivity extends Activity implements OnClickListener {
	/* 个人资料，清除缓存，意见反馈，关于我们 */
	private RelativeLayout rlUserData, rlAccountBinding, rlClearCache,
			rlFeedBack, rlAboutUs;
	private ImageView ivMobile, ivWeiBo, ivWX, ivQQ;
	// 显示缓存大小
	private TextView tvCache;
	private Button btnExitLogin;
	private SharedPreferences userInfoPreferences;

	private Handler mHandler = new Handler() {
		private JSONObject js;

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == hpCantant.LABLE_LOGOUT) {
				Bundle bundle = msg.getData();
				try {
					js = new JSONObject(bundle.getString(hpCantant.GETDATA));
					if (js.getString("code").equals(hpCantant.SUCCESS_CODE)) {
						ExitLogin();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		}
	};
	private int countid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_user_setting);
		userInfoPreferences = ApplicationController.getInstance().getUser();
		countid = userInfoPreferences.getInt("countid", 0);
		initView();
		init();
	}

	/**
	 * 为各组件赋值
	 */
	private void init() {
		try {
			tvCache.setText(DataCleanManager
					.getTotalCacheSize(UserSettingActivity.this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initView() {
		TopBar tBar = (TopBar) findViewById(R.id.topbar_setting);
		tBar.setTitleText("设置");
		tBar.setRightIsVisable(false);
		tBar.setOnTopBarClickListener(new topBarClickListener() {
			@Override
			public void rightClick() {
				Toast.makeText(UserSettingActivity.this, "click", 1).show();
			}

			@Override
			public void leftClick() {
				finish();
			}
		});

		rlUserData = (RelativeLayout) findViewById(R.id.rl_userMessage);
		rlAccountBinding = (RelativeLayout) findViewById(R.id.rl_accountBinding);
		rlClearCache = (RelativeLayout) findViewById(R.id.rl_clearCache);
		rlFeedBack = (RelativeLayout) findViewById(R.id.rl_feedBack);
		rlAboutUs = (RelativeLayout) findViewById(R.id.rl_aboutUs);
		tvCache = (TextView) findViewById(R.id.tv_cache);
		btnExitLogin = (Button) findViewById(R.id.btn_exit_login);

		ivMobile = (ImageView) findViewById(R.id.iv_mobileIcon);
		ivWeiBo = (ImageView) findViewById(R.id.iv_weiboIcon);
		ivWX = (ImageView) findViewById(R.id.iv_wxIcon);
		ivQQ = (ImageView) findViewById(R.id.iv_qqIcon);

		rlUserData.setOnClickListener(this);
		rlClearCache.setOnClickListener(this);
		rlFeedBack.setOnClickListener(this);
		rlAboutUs.setOnClickListener(this);
		btnExitLogin.setOnClickListener(this);
		rlAccountBinding.setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {
		Intent intent = new Intent();
		switch (view.getId()) {
		case R.id.rl_userMessage:
			if (countid != 0) {
				intent.setClass(UserSettingActivity.this,
						UserDataActivity.class);
				startActivity(intent);
			} else {
				intent.setClass(UserSettingActivity.this,
						UserLoginActivity.class);
				startActivity(intent);
			}
			break;
		case R.id.rl_accountBinding:
			if (countid != 0) {
				intent.setClass(getApplicationContext(),
						UserAcountBindingActivity.class);
				startActivity(intent);
			} else {
				intent.setClass(UserSettingActivity.this,
						UserLoginActivity.class);
				startActivity(intent);
			}
			break;
		case R.id.rl_clearCache:
			DataCleanManager.clearAllCache(UserSettingActivity.this);
			try {
				tvCache.setText(DataCleanManager
						.getTotalCacheSize(UserSettingActivity.this));
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.rl_feedBack:
			startActivity(new Intent(UserSettingActivity.this,
					UReBackActivity.class));
			break;
		case R.id.rl_aboutUs:
			break;
		case R.id.btn_exit_login:

			if (countid != 0) {
				ArrayMap<String, String> map = new ArrayMap<String, String>();
				map.put("countid", countid + "");
				CommonUtils.getData(mHandler, map, hpCantant.ULOGOUT_URL,
						hpCantant.LABLE_LOGOUT);
			} else {
				Toast.makeText(UserSettingActivity.this, "用戶未登陆",
						Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
	}

	public void ExitLogin() {
		logout(new EMCallBack() {
			@Override
			public void onSuccess() {
				userInfoPreferences.edit().clear().commit();
				Log.i("usersetting:exit?id:",
						userInfoPreferences.getInt("countid", 0) + "");
				startActivity(new Intent(UserSettingActivity.this,
						UserLoginActivity.class));
				UserSettingActivity.this.finish();
			}

			@Override
			public void onProgress(int arg0, String arg1) {
			}

			@Override
			public void onError(int arg0, String arg1) {
			}
		});
	}

	public void logout(final EMCallBack callback) {
		EMChatManager.getInstance().logout(new EMCallBack() {
			@Override
			public void onSuccess() {
				if (callback != null) {
					callback.onSuccess();
				}
			}

			@Override
			public void onError(int code, String message) {
			}

			@Override
			public void onProgress(int progress, String status) {
				if (callback != null) {
					callback.onProgress(progress, status);
				}
			}

		});
	}

}
