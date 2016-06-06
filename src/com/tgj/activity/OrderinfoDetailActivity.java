package com.tgj.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hp.R;
import com.hp.utils.CommonUtils;
import com.hp.utils.TGJCanst;
import com.hp.utils.hpCantant;
import com.hp.widget.TopBar;
import com.hp.widget.TopBar.topBarClickListener;
import com.tgj.Dao.BOrderDetailDao;
import com.tgj.bean.BOrderinfoDetail;
import com.tgj.bean.BOrderinfoDetail.Data;

public class OrderinfoDetailActivity extends Activity {
	private String orderid;
	private BOrderinfoDetail.Data mData;
	private BOrderDetailDao mDao;

	private Handler mHandler = new Handler() {
		private JSONObject js;

		public void handleMessage(android.os.Message msg) {
			if (msg.what == TGJCanst.LABLE_BORDERINFODETAIL) {
				Bundle bundle = msg.getData();
				mDao = new BOrderDetailDao();
				try {
					js = new JSONObject(bundle.getString(hpCantant.GETDATA));
					if (js.getString("code").equals(hpCantant.SUCCESS_CODE)) {
						mData = mDao.getOrderDetail(bundle
								.getString(hpCantant.GETDATA));
						runOnUiThread(new Runnable() {
							public void run() {
								initView(mData);
							}
						});
					} else {
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tgj_aty_orderdetail);
		orderid = getIntent().getStringExtra("orderid");

		TopBar tBar = (TopBar) findViewById(R.id.topbar_tgj_order_detail);
		tBar.setTitleText("新订单");
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
		getData();
		((LinearLayout) findViewById(R.id.rl_tgj_ordetail_tel))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(Intent.ACTION_CALL, Uri
								.parse("tel:" + mData.getTel()));
						startActivity(intent);
					}
				});
	}

	private void getData() {
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("orderid", orderid);
		CommonUtils.getTGJData(mHandler, map, TGJCanst.BORDERINFODETAIL_URL,
				TGJCanst.LABLE_BORDERINFODETAIL);
	}

	private void initView(final Data mData2) {
		setText(R.id.tv_tgj_ordertail_orderid, "订单号:" + mData2.getOrdernum());
		setText(R.id.tv_tgj_ordertail_title, mData2.getTitle());
		setText(R.id.tv_tgj_ordertail_date, "场次时间:" + mData2.getBegintime()
				+ "~" + mData2.getEndtime());
		setText(R.id.tv_tgj_ordertail_price, "价格:" + mData2.getPrice());
		setText(R.id.tv_tgj_ordertail_nickname, "昵称:" + mData2.getNickname());
		setText(R.id.tv_tgj_ordertail_time, "预计到达时间:" + mData2.getOrdernum());
		setText(R.id.tv_tgj_ordertail_tel, mData2.getTel());

	}

	private void setText(int id, String text) {
		TextView view = (TextView) findViewById(id);
		view.setText(text);
	}

}
