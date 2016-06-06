package com.hp.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.widget.EditText;
import android.widget.Toast;

import com.hp.R;
import com.hp.utils.CommonUtils;
import com.hp.utils.hpCantant;
import com.hp.widget.TopBar;
import com.hp.widget.TopBar.topBarClickListener;

public class UserAlertPswActivity extends BaseActivity {
	private EditText edtOldPSW;
	private EditText edtNewPSW;
	private EditText edtConfirmPSW;
	private String tel;
	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_user_register_resetpsw);
		tel = getIntent().getExtras().getString("tel");
		initView();
		mHandler = new Handler() {
			private JSONObject js;

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what == hpCantant.LABLE_UALERTPWD) {
					Bundle bundle = msg.getData();
					try {
						js = new JSONObject(bundle.getString(hpCantant.GETDATA));
						dialog.dismiss();
						Toast.makeText(UserAlertPswActivity.this,
								js.getString("summary"), 0).show();
						if (js.getString("code").equals(hpCantant.SUCCESS_CODE)) {
							runOnUiThread(new Runnable() {
								public void run() {
									Toast.makeText(UserAlertPswActivity.this,
											"修改成功", 0).show();
									UserAlertPswActivity.this.finish();
								}
							});
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}

	private void initView() {

		TopBar tBar = (TopBar) findViewById(R.id.topbar_resetpsw);
		tBar.setTitleText("修改密码");
		tBar.setLefttImageResource(R.drawable.icon_back);
		tBar.setRighttImageResource(R.drawable.icon_agree);
		tBar.setOnTopBarClickListener(new topBarClickListener() {
			@Override
			public void rightClick() {
				String oldpsw = edtOldPSW.getText().toString();
				String newpsw = edtNewPSW.getText().toString();
				String conpsw = edtConfirmPSW.getText().toString();
				if (newpsw.isEmpty() || conpsw.isEmpty() || oldpsw.isEmpty()) {
					Toast.makeText(UserAlertPswActivity.this, "输入不能为空",
							Toast.LENGTH_SHORT).show();
				} else if (newpsw.equals(conpsw)) {
					if (CommonUtils
							.isNetworkAvailable(UserAlertPswActivity.this)) {
						dialog = new ProgressDialog(UserAlertPswActivity.this);
						dialog.setMessage("修改中...");
						dialog.show();
						ArrayMap<String, String> map = new ArrayMap<String, String>();

						map.put("countid",
								mApplication.getUser().getInt("countid", 0)
										+ "");
						map.put("oldpwd", oldpsw);
						map.put("newpwd", newpsw);
						CommonUtils.getData(mHandler, map,
								hpCantant.UALERTPWD_URL,
								hpCantant.LABLE_UALERTPWD);

					} else {
						Toast.makeText(UserAlertPswActivity.this, "网络不可用",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(UserAlertPswActivity.this, "输入不一致",
							Toast.LENGTH_SHORT).show();
				}

			}

			@Override
			public void leftClick() {
				finish();
			}
		});

		edtOldPSW = (EditText) findViewById(R.id.edt_reset_oldpsw);
		edtNewPSW = (EditText) findViewById(R.id.edt_reset_newpsw);
		edtConfirmPSW = (EditText) findViewById(R.id.edt_reset_confirmpsw);

	}

}
