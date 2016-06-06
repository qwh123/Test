package com.hp.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.hp.R;
import com.hp.application.ApplicationController;
import com.hp.bean.UBanner;
import com.hp.bean.UIndexAd;
import com.hp.bean.UserInfo;
import com.hp.bean.UserInfo.Data;
import com.hp.dao.BannerDaoImpl;
import com.hp.dao.UIndexAdDao;
import com.hp.dao.UIndexAdDaoImpl;
import com.hp.utils.CommonUtils;
import com.hp.utils.HPDBHelper;
import com.hp.utils.UpdateAppManager;
import com.hp.utils.hpCantant;
import com.hp.utils.volley.VolleyUtils;
import com.hp.utils.volley.VolleyUtilsImpl;

/**
 * 
 * @{# SplashActivity.java Create on 2015-10-2 下午9:10:01
 * 
 *     class desc: 启动画面 (1)判断是否是首次加载应用--采取读取SharedPreferences的方法
 *     (2)是，则进入GuideActivity；否，则进入MainActivity (3)3s后执行(2)操作
 * @Version 1.0
 * 
 */

public class SplashActivity extends Activity {
	private ImageLoader imageLoader;
	private NetworkImageView ivIndexAd;
	UIndexAd.Data uAd;
	boolean isFirstIn = false;

	private static final int GO_GUIDE = 1001;
	private static final int GO_LOGIN = 1002;
	// 延迟3秒
	private static final long SPLASH_DELAY_MILLIS = 3000;

	private static final String SHAREDPREFERENCES_NAME = "first_pref";

	/**
	 * Handler:跳转到不同界面
	 */
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_LOGIN:
				goLogin();
				break;
			case GO_GUIDE:
				goGuide();
				break;
			}
			super.handleMessage(msg);
		}
	};
	private SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.aty_splash);
		// 读取SharedPreferences中需要的数据
		// 使用SharedPreferences来记录程序的使用次数
		preferences = getSharedPreferences(SHAREDPREFERENCES_NAME, MODE_PRIVATE);
		// 取得相应的值，如果没有该值，说明还未写入，用true作为默认值
		isFirstIn = preferences.getBoolean("isFirstIn", true);
		if (CommonUtils.isNetworkAvailable(SplashActivity.this) && !isFirstIn) {
			initView();
		} else {
			init();
		}

		// HPDBHelper helper = new HPDBHelper(this);
		// helper.getReadableDatabase();

	}

	void initView() {
		if (imageLoader == null) {
			imageLoader = ApplicationController.getInstance().getImageLoader();
		}
		ivIndexAd = (NetworkImageView) findViewById(R.id.iv_splash);
		Handler mH = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what == hpCantant.LABLE_INDEXAD) {
					Bundle bundle = msg.getData();
					if ((bundle.getString(hpCantant.GETDATA))
							.equals(hpCantant.ERROR_6)) {
						Toast.makeText(SplashActivity.this, bundle.toString(),
								1).show();
						SplashActivity.this.finish();
						return;
					}
					UIndexAdDao uAdDao = new UIndexAdDaoImpl();
					uAd = uAdDao.loadUIndexAd(bundle
							.getString(hpCantant.GETDATA));
					ivIndexAd.setImageUrl(uAd.getImagelink(), imageLoader);
					doClickEvent();
				}
				init();
			};

		};
		CommonUtils.getData1(mH, hpCantant.UINDEXAD_URL, hpCantant.LABLE_INDEXAD);
	}

	protected void doClickEvent() {
		ivIndexAd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Toast.makeText(SplashActivity.this,
						"链接至:" + uAd.getDetaillink(), 1).show();
			}
		});
	}

	private void init() {
		SharedPreferences userPreferences = getSharedPreferences(
				hpCantant.USERINFO_DATA, MODE_PRIVATE);
		Log.i("splash-countid:", userPreferences.getInt("countid", 0) + "");
		// 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面
		if (!isFirstIn) {
			mHandler.sendEmptyMessageDelayed(GO_LOGIN, SPLASH_DELAY_MILLIS);
		} else {
			mHandler.sendEmptyMessageDelayed(GO_GUIDE, SPLASH_DELAY_MILLIS);
		}

	}

	/**
	 * 跳转至登陆界面
	 */
	private void goLogin() {
		Intent intent = new Intent(SplashActivity.this, UserLoginActivity.class);
		SplashActivity.this.startActivity(intent);
		SplashActivity.this.finish();
	}

	/**
	 * 跳转至引导界面
	 */
	private void goGuide() {
		Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
		SplashActivity.this.startActivity(intent);
		SplashActivity.this.finish();
	}
}
