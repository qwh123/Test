package com.hp.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import com.hp.R;
import com.hp.widget.TopBar;
import com.hp.widget.TopBar.topBarClickListener;

public class UserRegisterActivity extends Activity implements OnClickListener {
	String title;
	private EditText edtTel;// 手机号
	private EditText edtCode;// 验证码
	private Button btnGetCode;// 获取验证码
	private Button btnNext;// 下一步
	// 弹出加载框
	ProgressDialog dialog;
	private Handler mHandler;

	int i = 60;
	private String phoneNums;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.aty_user_register);
		// 启动短信验证sdk
		SMSSDK.initSDK(this, "10459c656d54e",
				"dd0d3289d8050c819010bdfeb33ab5f1");
		title = getIntent().getStringExtra("Title");
		// setContentView(R.layout.test_aty_register);
		init();
	}

	/**
	 * 初始化控件
	 */
	private void init() {
		TopBar tBar = (TopBar) findViewById(R.id.topbar_register);
		tBar.setTitleText(title);
		tBar.setTitleTextColor(getResources().getColor(R.color.textcolor1));
		tBar.setLefttImageResource(R.drawable.icon_back);
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
		edtCode = (EditText) findViewById(R.id.edt_code);
		edtTel = (EditText) findViewById(R.id.edt_tel);
		btnGetCode = (Button) findViewById(R.id.btn_getEncode);
		btnNext = (Button) findViewById(R.id.btn_next);
		btnNext.setOnClickListener(this);
		btnGetCode.setOnClickListener(this);
		btnNext.setOnClickListener(this);

		EventHandler eventHandler = new EventHandler() {
			@Override
			public void afterEvent(int event, int result, Object data) {
				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				handler.sendMessage(msg);
			}
		};
		// 注册回调监听接口
		SMSSDK.registerEventHandler(eventHandler);
	}

	@Override
	public void onClick(View v) {
		phoneNums = edtTel.getText().toString();
		switch (v.getId()) {
		case R.id.btn_getEncode:
			// 1. 通过规则判断手机号
			if (!judgePhoneNums(phoneNums)) {
				return;
			} // 2. 通过sdk发送短信验证
			SMSSDK.getVerificationCode("86", phoneNums);

			// 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
			btnGetCode.setClickable(false);
			btnGetCode.setText(i + "s");
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (; i > 0; i--) {
						handler.sendEmptyMessage(-9);
						if (i <= 0) {
							break;
						}
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					handler.sendEmptyMessage(-8);
				}
			}).start();
			break;

		case R.id.btn_next:
			if (phoneNums.isEmpty() || (edtCode.getText().toString()).isEmpty()) {
				Toast.makeText(UserRegisterActivity.this, "帐号或验证码均不能为空",
						Toast.LENGTH_SHORT).show();
			} else {
				SMSSDK.submitVerificationCode("86", phoneNums, edtCode
						.getText().toString());
				createProgressBar();
			}
			break;
		}
	}

	/**
	 * 
	 */
	Handler handler = new Handler() {
		private Intent intent;

		public void handleMessage(Message msg) {
			if (msg.what == -9) {
				btnGetCode.setText(i + "s");
			} else if (msg.what == -8) {
				btnGetCode.setText("获取验证码");
				btnGetCode.setClickable(true);
				i = 30;
			} else {
				int event = msg.arg1;
				int result = msg.arg2;
				Object data = msg.obj;
				Log.e("event", "event=" + event);
				if (result == SMSSDK.RESULT_COMPLETE) {
					// 短信注册成功后，返回MainActivity,然后提示
					if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
						Toast.makeText(getApplicationContext(), "提交验证码成功",
								Toast.LENGTH_SHORT).show();
						if (title.equals(UserDataActivity.CHANGE_PSW)) {
							intent = new Intent(UserRegisterActivity.this,
									UserAlertPswActivity.class);
						} else if (title
								.equals(UserLoginActivity.USER_FORGETPSW)) {
							intent = new Intent(UserRegisterActivity.this,
									UserSetPswActivity.class);
							intent.putExtra("title",
									UserLoginActivity.USER_FORGETPSW);
						} else if (title
								.equals(UserLoginActivity.USER_REGISTER)) {
							intent = new Intent(UserRegisterActivity.this,
									UserSetPswActivity.class);
							intent.putExtra("title",
									UserLoginActivity.USER_REGISTER);
						}
						intent.putExtra("tel", phoneNums);
						startActivity(intent);
						UserRegisterActivity.this.finish();
					} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
						Toast.makeText(UserRegisterActivity.this, "验证码已经发送",
								Toast.LENGTH_SHORT).show();
					} else {
						((Throwable) data).printStackTrace();
					}
				}
			}
		}
	};

	/**
	 * 判断手机号码是否合理
	 * 
	 * @param phoneNums
	 */
	private boolean judgePhoneNums(String phoneNums) {
		if (isMatchLength(phoneNums, 11) && isMobileNO(phoneNums)) {
			return true;
		}
		Toast.makeText(this, "手机号码输入有误！", Toast.LENGTH_SHORT).show();
		return false;
	}

	/**
	 * 判断一个字符串的位数
	 * 
	 * @param str
	 * @param length
	 * @return
	 */
	public static boolean isMatchLength(String str, int length) {
		if (str.isEmpty()) {
			return false;
		} else {
			return str.length() == length ? true : false;
		}
	}

	/**
	 * 验证手机格式
	 */
	public static boolean isMobileNO(String mobileNums) {
		/*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
		String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (TextUtils.isEmpty(mobileNums))
			return false;
		else
			return mobileNums.matches(telRegex);
	}

	/**
	 * progressbar
	 */
	private void createProgressBar() {
		FrameLayout layout = (FrameLayout) findViewById(android.R.id.content);
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.gravity = Gravity.CENTER;
		ProgressBar mProBar = new ProgressBar(this);
		mProBar.setLayoutParams(layoutParams);
		mProBar.setVisibility(View.VISIBLE);
		layout.addView(mProBar);
	}

	@Override
	protected void onDestroy() {
		SMSSDK.unregisterAllEventHandler();
		super.onDestroy();
	}

}
