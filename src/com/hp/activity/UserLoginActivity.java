package com.hp.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.easemob.easeui.ui.EaseBaseActivity;
import com.hp.R;
import com.hp.application.ApplicationController;
import com.hp.bean.UserInfo.Data;
import com.hp.dao.UserDao;
import com.hp.dao.UserDaoImpl;
import com.hp.utils.CommonUtils;
import com.hp.utils.hpCantant;
import com.hp.widget.TopBar;
import com.hp.widget.TopBar.topBarClickListener;

public class UserLoginActivity extends EaseBaseActivity implements
		OnClickListener {
	public static String USER_REGISTER = "用户注册";
	public static String USER_FORGETPSW = "忘记密码";
	private EditText mUsernameET;
	private EditText mPasswordET;
	private TextView mPasswordForgetTV;
	private Button mSigninBtn;
	private TextView mSignupTV;

	private ProgressDialog mDialog;
	private JSONObject js;
	private Data mUData;
	private UserDao uUser;

	private String password;
	private String userName;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == hpCantant.LABLE_LOGIN) {
				Bundle bundle = msg.getData();
				uUser = new UserDaoImpl();
				try {
					js = new JSONObject(bundle.getString(hpCantant.GETDATA));
					if (js.getString("code").equals(hpCantant.SUCCESS_CODE)) {
						mUData = (Data) uUser.loadUserToNet(bundle
								.getString(hpCantant.GETDATA));
						try {
							manage(js.getString("summary"));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					} else {
						Toast.makeText(UserLoginActivity.this,
								js.getString("summary"), 1).show();
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {

			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_user_login);
		initView();
		mSignupTV.setOnClickListener(this);
		if (EMChat.getInstance().isLoggedIn()) {
			Log.d("TAG", "已经登陆过");
			startActivity(new Intent(UserLoginActivity.this,
					MainActivity_01.class));
			this.finish();
		}
	}

	private ImageButton btnWeiBLogin;
	private ImageButton btnWeiXLogin;
	private ImageButton btnQQLogin;

	private void initView() {
		TopBar tBar = (TopBar) findViewById(R.id.topbar_login);
		tBar.setBackground(R.color.white);
		tBar.setTitleText("登陆");
		tBar.setTitleTextColor(getResources().getColor(R.color.textcolor1));
		tBar.setLefttImageResource(R.drawable.icon_back);
		tBar.setRightIsVisable(false);
		tBar.setOnTopBarClickListener(new topBarClickListener() {
			@Override
			public void rightClick() {
				// DOTO
			}

			@Override
			public void leftClick() {
				Intent intent = new Intent();
				intent.setClass(UserLoginActivity.this, MainActivity_01.class);
				startActivity(intent);
				finish();
			}
		});
		btnWeiBLogin = (ImageButton) findViewById(R.id.ibtn_login_wb);
		btnWeiXLogin = (ImageButton) findViewById(R.id.ibtn_login_wx);
		btnQQLogin = (ImageButton) findViewById(R.id.ibtn_login_qq);
		mUsernameET = (EditText) findViewById(R.id.chat_login_username);
		mPasswordET = (EditText) findViewById(R.id.chat_login_password);
		mPasswordForgetTV = (TextView) findViewById(R.id.chat_login_forget_password);
		mSigninBtn = (Button) findViewById(R.id.chat_login_signin_btn);
		mSignupTV = (TextView) findViewById(R.id.chat_login_signup);
		// 微博，微信，QQ登陆
		btnWeiBLogin.setOnClickListener(this);
		btnWeiXLogin.setOnClickListener(this);
		btnQQLogin.setOnClickListener(this);
		mPasswordForgetTV.setOnClickListener(this);

		/**
		 * 帐号更改，清空密码
		 */
		mUsernameET.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				mPasswordET.setText(null);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
			}
		});

		/**
		 * 登陆帐号
		 */
		mSigninBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				userName = mUsernameET.getText().toString().trim();
				password = mPasswordET.getText().toString().trim();

				if (TextUtils.isEmpty(userName)) {
					Toast.makeText(getApplicationContext(), "请输入用户名",
							Toast.LENGTH_SHORT).show();
				} else if (TextUtils.isEmpty(password)) {
					Toast.makeText(getApplicationContext(), "请输入密码",
							Toast.LENGTH_SHORT).show();
				} else {
					mDialog = new ProgressDialog(UserLoginActivity.this);
					mDialog.setTitle("登陆");
					((ProgressDialog) mDialog).setMessage("正在登陆服务器，请稍后...");
					mDialog.show();
					ArrayMap<String, String> map = new ArrayMap<String, String>();
					map.put("tel", userName);
					map.put("pwd", password);
					CommonUtils.getData(mHandler, map, hpCantant.ULOGIN_URL,
							hpCantant.LABLE_LOGIN);
				}
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 忘记密码
		case R.id.chat_login_forget_password:
			Intent intent = new Intent(UserLoginActivity.this,
					UserRegisterActivity.class);
			intent.putExtra("Title", USER_FORGETPSW);
			startActivity(intent);
			break;
		case R.id.chat_login_signup:
			Intent intent1 = new Intent(UserLoginActivity.this,
					UserRegisterActivity.class);
			intent1.putExtra("Title", USER_REGISTER);
			startActivity(intent1);
			break;
		// 微博登陆
		case R.id.ibtn_login_wb:
			break;
		// 微信登陆
		case R.id.ibtn_login_wx:
			break;
		// QQ登陆
		case R.id.ibtn_login_qq:
			break;
		default:
			break;
		}
	}

	/*
	 * @Override public boolean onKeyDown(int keyCode, KeyEvent event) { if
	 * (keyCode == KeyEvent.KEYCODE_BACK) {
	 * 
	 * new AlertDialog.Builder(UserLoginActivity.this) .setTitle("应用提示")
	 * .setMessage( "确定要退出" + getResources().getString( R.string.app_name) +
	 * "客户端吗？") .setPositiveButton("确定", new DialogInterface.OnClickListener() {
	 * 
	 * @Override public void onClick(DialogInterface dialog, int which) {
	 * AppManager.getInstance().AppExit( UserLoginActivity.this);
	 * UserLoginActivity.this.finish(); } }) .setNegativeButton("取消", new
	 * DialogInterface.OnClickListener() { public void onClick(DialogInterface
	 * dialog, int whichButton) { } }).show(); }
	 * 
	 * return super.onKeyDown(keyCode, event); }
	 */
	protected void manage(String string) {
		if (string.equals("成功")) {// 与数据库进行比较
			StringReverse mReverse = new StringReverse();
			Log.i("--name---psw", userName + "--" + password);
			String user = mUData.getCountid() + "";
			EMChatManager.getInstance().login(user + user,
					mReverse.swapWords(user) + mReverse.swapWords(user),
					new EMCallBack() {// 回调
						@Override
						public void onSuccess() {
							mDialog.dismiss();
							Log.d("main", "登陆聊天服务器成功！");
							SharedPreferences.Editor mEditor = ApplicationController
									.getInstance().getUser().edit();
							// 将用户登陆的号码存储起来
							mEditor.putString("tel", userName);
							mEditor.putInt("countid", mUData.getCountid());
							mEditor.putString("icon", mUData.getIcon());
							mEditor.putString("nickname", mUData.getNickname());
							mEditor.putInt("sex", mUData.getSex());
							mEditor.putString("industry", mUData.getIndustry());
							mEditor.putString("job", mUData.getJob());
							mEditor.putString("school", mUData.getSchool());
							mEditor.putString("borndate", mUData.getBorndate());
							mEditor.putString("starid", mUData.getStarid());
							mEditor.putString("btypeid", mUData.getBtypeid());
							mEditor.putString("persign", mUData.getPersign());
							mEditor.putInt("state", mUData.getState());
							mEditor.commit();
							startActivity(new Intent(UserLoginActivity.this,
									MainActivity_01.class));
							UserLoginActivity.this.finish();
						}

						@Override
						public void onProgress(int progress, String status) {

						}

						@Override
						public void onError(int code, String message) {
							mDialog.dismiss();
							if (code == -1005) {
								message = "用户名或密码错误";
							}
							final String msg = message;
							runOnUiThread(new Runnable() {
								public void run() {
									Toast.makeText(getApplicationContext(),
											msg, Toast.LENGTH_SHORT).show();
								}
							});
						}
					});

		} else
			Toast.makeText(this, string, Toast.LENGTH_SHORT).show();

	}

	public class StringReverse {
		public void swap(char[] arr, int begin, int end) {
			while (begin < end) {
				char temp = arr[begin];
				arr[begin] = arr[end];
				arr[end] = temp;
				begin++;
				end--;
			}
		}

		// 反转字符串
		public String swapWords(String str) {
			char[] arr = str.toCharArray();
			swap(arr, 0, arr.length - 1);
			int begin = 0;
			for (int i = 1; i < arr.length; i++) {
				if (arr[i] == ' ') {
					swap(arr, begin, i - 1);
					begin = i + 1;
				}
			}
			return new String(arr);
		}
	}
}