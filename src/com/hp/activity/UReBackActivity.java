package com.hp.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.widget.EditText;
import android.widget.Toast;

import com.hp.R;
import com.hp.utils.CommonUtils;
import com.hp.utils.hpCantant;
import com.hp.utils.volley.VolleyUtils;
import com.hp.utils.volley.VolleyUtilsImpl;
import com.hp.widget.TopBar;
import com.hp.widget.TopBar.topBarClickListener;

public class UReBackActivity extends BaseActivity {
	private String contents;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_user_setting_ureback);

		mHandler = new Handler() {
			private JSONObject js;
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what == hpCantant.LABLE_UReback) {
					Bundle bundle = msg.getData();
					try {
						js = new JSONObject(bundle.getString(hpCantant.GETDATA));
						if (js.getString("code").equals(hpCantant.SUCCESS_CODE)) {
							runOnUiThread(new Runnable() {
								public void run() {
									try {
										Toast.makeText(UReBackActivity.this,
												js.getString("summary"),
												Toast.LENGTH_SHORT).show();
									} catch (JSONException e) {
										e.printStackTrace();
									}
									UReBackActivity.this.finish();
								}
							});
						} else {
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};

		TopBar tBar = (TopBar) findViewById(R.id.topbar_ureback);
		tBar.setTitleText("意见反馈");
		tBar.setRighttImageResource(R.drawable.icon_right_tj);
		tBar.setOnTopBarClickListener(new topBarClickListener() {
			@Override
			public void rightClick() {
				contents = ((EditText) findViewById(R.id.edt_setting_reback))
						.getText().toString();
				if (!contents.isEmpty()) {
					ArrayMap<String, String> map = new ArrayMap<String, String>();
					map.put("countid",
							mApplication.getUser().getInt("countid", 0) + "");
					map.put("contents", contents);
					VolleyUtils mUtils1 = new VolleyUtilsImpl();
					CommonUtils.getData(mHandler, map, hpCantant.UReback_URL, hpCantant.LABLE_UReback);
				}
			}
			@Override
			public void leftClick() {
				finish();
			}
		});
	}
}
