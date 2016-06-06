package com.hp.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hp.R;
import com.hp.utils.CommonUtils;
import com.hp.utils.hpCantant;
import com.hp.utils.volley.VolleyUtils;
import com.hp.utils.volley.VolleyUtilsImpl;
import com.hp.widget.TopBar;
import com.hp.widget.TopBar.topBarClickListener;

/**
 * 注册密码界面
 * 
 * @author qwh
 * 
 */
public class UserSetPswActivity extends BaseActivity {
	private EditText edtNewPSW;
	private EditText edtConfirmPSW;
	private String tel;
	ProgressDialog dialog;
	private String title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_user_register_setpsw);
		tel = getIntent().getExtras().getString("tel");
		title = getIntent().getExtras().getString("title");
		initView();
		mHandler = new Handler() {
			private JSONObject js;

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what == hpCantant.LABLE_REGISTER
						|| msg.what == hpCantant.LABLE_URESETPSW) {
					try {
						dialog.dismiss();
						Bundle bundle = msg.getData();
						js = new JSONObject(bundle.getString(hpCantant.GETDATA));
						if (js.getString("code").equals(hpCantant.SUCCESS_CODE)) {
							Toast.makeText(getApplicationContext(),
									js.getString("summary"), Toast.LENGTH_SHORT)
									.show();
							startActivity(new Intent(UserSetPswActivity.this,
									UserLoginActivity.class));
							finish();
						} else {
							Toast.makeText(UserSetPswActivity.this,
									js.getString("summary"), Toast.LENGTH_SHORT)
									.show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}

	private void initView() {

		TopBar tBar = (TopBar) findViewById(R.id.topbar_setpsw);
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

		edtNewPSW = (EditText) findViewById(R.id.edt_set_newpsw);
		edtConfirmPSW = (EditText) findViewById(R.id.edt_set_confirpsw);
		((Button) findViewById(R.id.btn_register))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						String newpsw = edtNewPSW.getText().toString();
						String conpsw = edtConfirmPSW.getText().toString();
						if (newpsw.isEmpty() || conpsw.isEmpty()) {
							Toast.makeText(UserSetPswActivity.this, "输入不能为空",
									Toast.LENGTH_SHORT).show();
						} else if (newpsw.equals(conpsw)) {
							if (CommonUtils
									.isNetworkAvailable(UserSetPswActivity.this)) {
								ArrayMap<String, String> map = new ArrayMap<String, String>();
								dialog = new ProgressDialog(
										UserSetPswActivity.this);
								if (!title.isEmpty()
										&& title.equals(UserLoginActivity.USER_REGISTER)) {
									dialog.setMessage("注册中...");
									dialog.show();
									map.put("tel", tel);
									map.put("pwd", newpsw);
									CommonUtils.getData(mHandler, map,
											hpCantant.UREGISTER_URL,
											hpCantant.LABLE_REGISTER);
								} else if (!title.isEmpty()
										&& title.equals(UserLoginActivity.USER_FORGETPSW)) {
									dialog.setMessage("重置中...");
									dialog.show();
									map.put("tel", tel);
									map.put("newpwd", newpsw);
									CommonUtils.getData(mHandler, map,
											hpCantant.URESETPWD_URL,
											hpCantant.LABLE_URESETPSW);
								}

							} else {
								Toast.makeText(UserSetPswActivity.this,
										"网络不可用", Toast.LENGTH_SHORT).show();
							}
						} else {
							Toast.makeText(UserSetPswActivity.this, "输入不一致",
									Toast.LENGTH_SHORT).show();
						}

					}
				});

	}

}
