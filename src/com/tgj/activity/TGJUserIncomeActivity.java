package com.tgj.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hp.R;
import com.hp.activity.BaseActivity;
import com.hp.utils.CommonUtils;
import com.hp.utils.TGJCanst;
import com.hp.utils.hpCantant;
import com.hp.widget.TopBar;
import com.hp.widget.TopBar.topBarClickListener;
import com.tgj.Dao.BGetMoneyDao;
import com.tgj.bean.BGetMoney.Data;

public class TGJUserIncomeActivity extends BaseActivity {
	private String money;// 申请提现的金额
	private TextView tvIncomeNow;
	private TextView tvIncomeTotal;
	private Data mMoney;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tgj_aty_user_income);

		TopBar tBar = (TopBar) findViewById(R.id.topbar_tgj_income);
		tBar.setTitleText("提现");
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

		initView();
		getData();

	}

	/**
	 * 获取收入
	 */
	private void getData() {
		mHandler = new Handler() {
			private BGetMoneyDao bBGM;
			private JSONObject js;

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what == TGJCanst.LABLE_BGETMONEY) {
					Bundle bundle = msg.getData();
					bBGM = new BGetMoneyDao();
					Log.i("uUser:", bundle.getString(hpCantant.GETDATA));
					try {
						js = new JSONObject(bundle.getString(hpCantant.GETDATA));
						if (js.getString("code").equals(hpCantant.SUCCESS_CODE)) {
							mMoney = (Data) bBGM.getBGetMoney(bundle
									.getString(hpCantant.GETDATA));
							runOnUiThread(new Runnable() {
								public void run() {
									tvIncomeNow.setText("¥"
											+ mMoney.getMoneynow());
									tvIncomeTotal.setText("¥"
											+ mMoney.getMoneytotal());
								}
							});

						} else {
							Toast.makeText(TGJUserIncomeActivity.this,
									js.getString("summary"), 1).show();
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else if (msg.what == TGJCanst.LABLE_BRequestMoney) {
					Bundle bundle = msg.getData();
					try {
						js = new JSONObject(bundle.getString(hpCantant.GETDATA));
						Toast.makeText(TGJUserIncomeActivity.this,
								js.getString("summary"), Toast.LENGTH_SHORT)
								.show();
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}
			}
		};

		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("countid", mApplication.getUser().getInt("countod", 0) + "");
		CommonUtils.getTGJData(mHandler, map, TGJCanst.BGETMONEY_URL,
				TGJCanst.LABLE_BGETMONEY);
		// 申请提现功能
		((RelativeLayout) findViewById(R.id.rl_withdraw))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						try {

							if (Float.parseFloat(mMoney.getMoneynow()) > 0.0f) {
								ArrayMap<String, String> map = new ArrayMap<String, String>();
								map.put("countid", mApplication.getUser()
										.getInt("countod", 0) + "");
								map.put("money", money);
								CommonUtils.getTGJData(mHandler, map,
										TGJCanst.BRequestMoney_URL,
										TGJCanst.LABLE_BRequestMoney);
							} else {
								Toast.makeText(TGJUserIncomeActivity.this,
										"提现金额必须大于0", 0).show();
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				});
	}

	private void initView() {
		tvIncomeNow = (TextView) findViewById(R.id.tv_income_now);
		tvIncomeTotal = (TextView) findViewById(R.id.tv_income_total);
	}
}
