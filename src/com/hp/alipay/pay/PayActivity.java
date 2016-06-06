package com.hp.alipay.pay;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.hp.R;
import com.hp.application.ApplicationController;
import com.hp.bean.UGetPrice.Data;
import com.hp.dao.UGetPriceDao;
import com.hp.utils.CommonUtils;
import com.hp.utils.GetGoodPrice;
import com.hp.utils.hpCantant;
import com.hp.widget.TopBar;
import com.hp.widget.TopBar.topBarClickListener;

public class PayActivity extends FragmentActivity {
	private TextView tvDate;

	private static final int TYPE_HP = 1;

	private static final String PAY_OK = "确认";
	private Handler mHandler = new Handler() {
		private JSONObject js;
		private UGetPriceDao mGetPriceDao;
		private Data mPriceData;

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case hpCantant.LABLE_UORDERCREATE:
				Bundle bundle = msg.getData();
				try {
					js = new JSONObject(bundle.getString(hpCantant.GETDATA));
					String data = js.getString("data");
					if (btnPay.getText().equals(PAY_OK)) {
						Toast.makeText(PayActivity.this,
								js.getString("summary"), 0).show();
					} else
						new OpenZFB(PayActivity.this, data);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;

			case hpCantant.LABLE_UGetPrice:
				Bundle bundle1 = msg.getData();
				mGetPriceDao = new UGetPriceDao();
				try {
					js = new JSONObject(bundle1.getString(hpCantant.GETDATA));
					if (js.getString("code").equals(hpCantant.SUCCESS_CODE)) {
						mPriceData = mGetPriceDao.GetPrice(bundle1
								.getString(hpCantant.GETDATA));
						runOnUiThread(new Runnable() {
							public void run() {
								allPrice.setText("¥" + mPriceData.getPrice());
							}
						});
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

			default:
				break;
			}
		};
	};
	private String OrderDate = null;//
	private float OrderPrice = 0.0f;//
	private String OrderTitle = null;//
	private String id = null;// 商品id
	private String countid = null;// 用户id
	private String classid;// 商品分类id
	private String tel;
	private EditText edtTel;
	private String ImageUrl;
	private int count = 1;

	private ImageLoader imageLoader;

	private RelativeLayout rlDate;

	private Button btnPay;

	private float pri;

	private ImageButton ibtnSum;

	private ImageButton ibtnAdd;

	private TextView allPrice;

	private EditText edtNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		OrderTitle = bundle.getString("orderTitle");
		try {
			OrderPrice = bundle.getFloat("orderPrice");
		} catch (Exception e) {
			OrderPrice = -1f;
		}
		OrderDate = bundle.getString("orderDate");
		classid = bundle.getString("classid");
		if (Integer.parseInt(classid) == TYPE_HP)
			count = bundle.getInt("count");
		id = bundle.getString("id");
		ImageUrl = bundle.getString("imageurl");
		countid = bundle.getString("countid");
		initView();
	}

	/**
	 * 订单创建
	 */
	private boolean OrderCreate() {
		tel = edtTel.getText().toString();
		// String date = tvDate.getText().toString().trim();
		String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。

		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("id", id);// 商品id
		map.put("countid", countid);// 用户id
		map.put("num", count + "");// 用户id

		if (tel.matches(telRegex)) {
			map.put("tel", tel);// 购买者联系电话
		} else {
			Toast.makeText(this, "联系号码错误", 1).show();
			return false;
		}
		if (Integer.parseInt(classid) == TYPE_HP) {
			String priceid = getIntent().getStringExtra("priceid");
			map.put("itemid", priceid);// 选择时间段的id
			// if (date.isEmpty()) {
			// Toast.makeText(this, "尚未填写时间", 1).show();
			// return false;
			// } else {
			// map.put("choicedate", date);// 用户预定到达时间
			// }
		}
		CommonUtils.getData(mHandler, map, hpCantant.UORDERCREATE_URL,
				hpCantant.LABLE_UORDERCREATE);
		return true;
	}

	private void initView() {
		TopBar tBar = (TopBar) findViewById(R.id.topbar_pay);
		tBar.setTitleText("预定");
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
		setImageByUrl(R.id.iv_order_icon, ImageUrl);
		setTextById(R.id.tv_order_title, OrderTitle);
		setTextById(R.id.tv_order_time, OrderDate);
		setTextById(R.id.tv_order_nowprice, "¥" + OrderPrice);
		edtTel = (EditText) findViewById(R.id.edt_order_tel);
		btnPay = (Button) findViewById(R.id.btn_pay);
		edtTel.setText(ApplicationController.getInstance().getUser()
				.getString("tel", ""));
		edtNum = (EditText) findViewById(R.id.edt_detail_pop_num);
		edtNum.setText(count + "");
		edtNum.setEnabled(false);
		allPrice = (TextView) findViewById(R.id.tv_order_allprice);
		allPrice.setText("¥" + OrderPrice * count);
		ibtnSum = (ImageButton) findViewById(R.id.ibtn_detail_pop_sum);
		ibtnSum.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (count > 1) {
					count--;
					edtNum.setText(count + "");
					new GetGoodPrice(mHandler, id, count, OrderPrice + "");
				}
			}
		});
		ibtnAdd = (ImageButton) findViewById(R.id.ibtn_detail_pop_add);
		ibtnAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				count++;
				edtNum.setText(count + "");
				new GetGoodPrice(mHandler, id, count, OrderPrice + "");
			}
		});

		// rlDate = (RelativeLayout) findViewById(R.id.rl_order_date);
		// if (Integer.parseInt(classid) == TYPE_HP) {
		// rlDate.setVisibility(View.VISIBLE);
		// tvDate = (TextView) findViewById(R.id.tv_order_date);
		// rlDate.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View arg0) {
		// Date day = new Date();
		// SimpleDateFormat df = new SimpleDateFormat(
		// "yyyy-MM-dd HH:mm");
		//
		// TimeSelector mSelector = new TimeSelector(PayActivity.this,
		// new TimeSelector.ResultHandler() {
		// @Override
		// public void handle(String time) {
		// Toast.makeText(PayActivity.this, time,
		// Toast.LENGTH_LONG).show();
		// tvDate.setText(String.valueOf(time));
		// }
		// }, df.format(day), "2018-12-31 23:59");
		// // mSelector.setScrollUnit(TimeSelector.SCROLLTYPE.HOUR);
		// mSelector.show();
		// }
		// });
		// }
		if (OrderPrice == 0) {
			btnPay.setText(PAY_OK);
		}
	}

	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay(View v) {
		OrderCreate();
	}

	/**
	 * 为TextView设置值
	 * 
	 * @param view
	 * @param id
	 * @return
	 */
	public void setTextById(int id, String text) {
		TextView view = (TextView) findViewById(id);
		view.setText(text);
	}

	public void setImageByUrl(int viewId, String url) {
		ImageView view = (ImageView) findViewById(viewId);
		if (imageLoader == null)
			imageLoader = ApplicationController.getInstance().getImageLoader();
		ImageListener listener = ImageLoader.getImageListener(view,
				R.drawable.fail_image, R.drawable.fail_image);
		imageLoader.get(url, listener);
	}
}
