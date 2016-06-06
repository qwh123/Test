package com.hp.activity;

import com.hp.R;
import com.hp.widget.TopBar;
import com.hp.widget.TopBar.topBarClickListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 用户账号绑定界面
 * 
 * @author qwh
 * 
 */
public class UserAcountBindingActivity extends Activity implements
		OnClickListener {
	private ImageView doBack;
	private TextView Title;

	private RelativeLayout rlWB, rlWX, rlQQ, rlMobile;
	// 提示第三方是否绑定状态
	private TextView tvWBMessage, tvWXMessage, tvQQMessage, tvMobileMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_user_accountbinding_detail);

		initView();
	}

	private void initView() {
		TopBar tBar=(TopBar) findViewById(R.id.topbar_accountbinding);
		tBar.setTitleText("帐号绑定");
		tBar.setRightIsVisable(false);
		tBar.setOnTopBarClickListener(new topBarClickListener() {
			@Override
			public void rightClick() {
				Toast.makeText(UserAcountBindingActivity.this, "click", 1).show();
			}
			@Override
			public void leftClick() {
				finish();
			}
		});

		rlWB = (RelativeLayout) findViewById(R.id.rl_WB);
		rlWX = (RelativeLayout) findViewById(R.id.rl_WX);
		rlQQ = (RelativeLayout) findViewById(R.id.rl_QQ);
		rlMobile = (RelativeLayout) findViewById(R.id.rl_Mobile);

		tvWBMessage = (TextView) findViewById(R.id.tv_wbMessage);
		tvWXMessage = (TextView) findViewById(R.id.tv_wxMessage);
		tvQQMessage = (TextView) findViewById(R.id.tv_qqMessage);
		tvMobileMessage = (TextView) findViewById(R.id.tv_mobileMessage);

		rlWB.setOnClickListener(this);
		rlWX.setOnClickListener(this);
		rlQQ.setOnClickListener(this);
		rlMobile.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		//返回上一个界面
		case R.id.tv_wbMessage:

			break;
		case R.id.tv_wxMessage:

			break;
		case R.id.tv_qqMessage:

			break;
		case R.id.tv_mobileMessage:

			break;
			

		default:
			break;
		}
	}
}
