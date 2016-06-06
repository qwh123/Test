package com.hp.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.hp.R;
import com.hp.adapter.GoodDetailHuiAdapter;
import com.hp.alipay.pay.PayActivity;
import com.hp.application.ApplicationController;
import com.hp.bean.UGoodinfoDetail;
import com.hp.bean.UGoodinfoDetail.Data.Item;
import com.hp.bean.UGoodinfoDetail.Data.Item.Cheap;
import com.hp.commonAdapter.CommonAdapter;
import com.hp.commonAdapter.ViewHolder;
import com.hp.dao.UGoodinfoDetailDao;
import com.hp.utils.CommonUtils;
import com.hp.utils.GetGoodPrice;
import com.hp.utils.hpCantant;
import com.hp.widget.CollapsibleTextView;
import com.hp.widget.MyListView;

public class GoodDetailActivity extends Activity implements OnClickListener {
	private static final String TYPE_HP = "1";
	private static final String TYPE_ACTIVITY = "2";
	private String id = null;// 商品id
	private String ImageUrl = null;// 商品顶部显示图片
	private String classid = null;// 商品类型
	private int countid;// 用户id

	private RelativeLayout loadrRelativeLayout;// 加载界面
	private ScrollView scView;
	private ImageButton ibtnBack;// 返回上一页
	private ImageButton ibtnShare;// 点击分享
	private ImageButton ibtnCall;// 电话拨打
	private Button btnBook;// 预定
	private RatingBar rbComment;// 趴友评分
	private RatingBar rbScore;// 趴友平均分
	private RelativeLayout rlHui;// 优惠团购
	private RelativeLayout rlComment;// 跳转至评论页
	private CheckBox cbIsFav;// 是否收藏
	// private CollapsibleTextView clDescribe;
	private RelativeLayout rlMore;
	private ImageButton ibtnDel;
	private PopupWindow popupWindow;
	private String ChooseTime;// 选择的场地时间
	private float ChoosePrice;// 选择场地的价格
	private boolean IsShowPop = false;
	private LinearLayout llintroduce;// 活动介绍
	private ImageView ivCommentNull;
	private RelativeLayout rlChoose;// 轰趴选择

	private CollapsibleTextView tvBeforeBuy;// 购票须知缩放

	private String priceid;// 当前选择的时段id
	private int num = 1;// 选择商品数量

	private UGoodinfoDetail.Data mDetail;
	UGoodinfoDetailDao mDetailDao;
	private Handler mHandler = new Handler() {
		private JSONObject js;

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle bundle = null;
			switch (msg.what) {
			case hpCantant.LABLE_UGOODINFODETAIL:// 获取详情数据
				bundle = msg.getData();
				mDetailDao = new UGoodinfoDetailDao();
				try {
					js = new JSONObject(bundle.getString(hpCantant.GETDATA));
					if (js.getString("code").equals(hpCantant.SUCCESS_CODE)) {
						mDetail = mDetailDao.getGoodDetailInfo(bundle
								.getString(hpCantant.GETDATA));
						runOnUiThread(new Runnable() {
							public void run() {
								init();
							}
						});
					} else {
					}
					loadrRelativeLayout.setVisibility(View.GONE);
					btnBook.setEnabled(true);
					scView.setVisibility(View.VISIBLE);
				} catch (JSONException e) {
					e.printStackTrace();
				}

				break;
			case hpCantant.LABLE_UFAVORITE:// 对商品收藏或取消收藏
				bundle = msg.getData();
				try {
					js = new JSONObject(bundle.getString(hpCantant.GETDATA));
					if (js.getString("code").equals(hpCantant.SUCCESS_CODE)) {
						Toast.makeText(GoodDetailActivity.this, "操作成功",
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;
			case hpCantant.LABLE_UDYNAMICCREATE:
				bundle = msg.getData();
				try {
					js = new JSONObject(bundle.getString(hpCantant.GETDATA));
					if (js.getString("code").equals(hpCantant.SUCCESS_CODE)) {
						Toast.makeText(GoodDetailActivity.this, "分享成功", 0)
								.show();
					} else {
						Toast.makeText(GoodDetailActivity.this, "分享失败", 0)
								.show();
					}
					popupWindow.dismiss();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_home_detail);
		Bundle bundle = getIntent().getExtras();
		id = bundle.getString("id");
		ImageUrl = bundle.getString("imageurl");
		classid = bundle.getString("classid");
		countid = ApplicationController.getInstance().getUser()
				.getInt("countid", 0);
		initView();
		// TODO Auto-generated method stub
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("id", id + "");// 商品id
		map.put("countid", countid + "");// 用户id
		initData(map, hpCantant.UGOODINFODETAIL_URL,
				hpCantant.LABLE_UGOODINFODETAIL);

	}

	/**
	 * 为各组件赋值
	 */
	private void init() {
		if (mDetail == null)
			return;
		if (ImageUrl != null) {
			setImageByUrl1(R.id.iv_deail_top_img, ImageUrl);
		}
		if (mDetail.getPricelist() != null
				&& mDetail.getPricelist().getDateprice().size() > 0) {
			List<Float> price = new ArrayList<Float>();
			for (int i = 0; i < mDetail.getPricelist().getDateprice().size(); i++) {
				price.add(Float.parseFloat(mDetail.getPricelist()
						.getDateprice().get(i).getPrice()));
			}
			Collections.sort(price);
			setTextById(R.id.tv_good_price, "￥" + price.get(0) + "~" + "￥"
					+ price.get(price.size() - 1));
		} else
			setTextById(R.id.tv_good_price, mDetail.getPrice());
		setTextById(R.id.tv_hostname, "主办方：" + mDetail.getHostname());
		setTextById(R.id.tv_businessdes_content, mDetail.getHostname());
		setTextById(R.id.tv_activity_style, "活动类型：" + mDetail.getLabel());
		setTextById(R.id.tv_activity_begintime,
				"开始时间：" + mDetail.getBegintime());
		setTextById(R.id.tv_activity_endtime, "结束时间：" + mDetail.getEndtime());
		setTextById(R.id.tv_activity_stop_time,
				"截止时间：" + mDetail.getClosetime());
		setTextById(R.id.tv_activity_num, "报名人数：" + mDetail.getNumnow() + "/"
				+ mDetail.getAllnum());

		setTextById(R.id.tv_good_title, mDetail.getTitle());
		setTextById(R.id.tv_good_oldprice, mDetail.getOldprice());
		if (mDetail.getFavorite() != null && mDetail.getFavorite().equals("1"))
			cbIsFav.setChecked(true);
		else
			cbIsFav.setChecked(false);
		setTextById(R.id.tv_detail_good_adress, mDetail.getAddress());
		setTextById(R.id.tv_businessdes_content, mDetail.getSummary());
		tvBeforeBuy.setDesc(mDetail.getBeforebuy(), BufferType.NORMAL);

		setTextById(R.id.tv_comment_num, mDetail.getAllnum() + "人评价");

		if (mDetail.getComment() != null) {
			// if (!mDetail.getAllnum().equals("0") && mDetail.getComment() !=
			// null) {
			setImageByUrl1(R.id.iv_comment_icon, mDetail.getComment().getIcon());
			// rbComment.setRating(Float.parseFloat(mHPData.getComment().get));
			setTextById(R.id.tv_comment_name, mDetail.getComment().getName());
			setTextById(R.id.tv_comment_time, mDetail.getComment().getTime());
			setTextById(R.id.tv_comment_content, mDetail.getComment()
					.getContent());
		} else {
			ivCommentNull.setVisibility(View.VISIBLE);
			LinearLayout l = (LinearLayout) findViewById(R.id.layout_comment);
			l.setVisibility(View.GONE);
		}
		if (classid.equals(TYPE_HP)) {
			btnBook.setText("立即预定");
			rlChoose.setVisibility(View.VISIBLE);
			float sc = Float.parseFloat(mDetail.getScore());
			if (mDetail.getScore() == null || sc < 0.0f || sc > 5.0f) {
				rbScore.setRating(0.0f);
			} else {
				rbScore.setRating(sc);
			}
		} else {
			btnBook.setText("立即报名");
			llintroduce.setVisibility(View.VISIBLE);
		}

	}

	/**
	 * 获取数据
	 * 
	 * @param lable
	 * @param url
	 * @param map
	 */
	private void initData(ArrayMap<String, String> map, String url, int lable) {
		CommonUtils.getData(mHandler, map, url, lable);
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		loadrRelativeLayout = (RelativeLayout) findViewById(R.id.home_detail_loading);
		scView = (ScrollView) findViewById(R.id.sv_content);
		rbComment = (RatingBar) findViewById(R.id.rb_comment_score);
		rbScore = (RatingBar) findViewById(R.id.rb_av_score);
		ibtnBack = (ImageButton) findViewById(R.id.ibtn_back);
		ibtnShare = (ImageButton) findViewById(R.id.ibtn_share);
		ibtnCall = (ImageButton) findViewById(R.id.ibtn_call);
		cbIsFav = (CheckBox) findViewById(R.id.cb_good_fav);
		btnBook = (Button) findViewById(R.id.btn_book);
		rlHui = (RelativeLayout) findViewById(R.id.rl_detail_good_hui);
		rlMore = (RelativeLayout) findViewById(R.id.rl_more);
		rlComment = (RelativeLayout) findViewById(R.id.rl_comment);

		tvBeforeBuy = (CollapsibleTextView) findViewById(R.id.tv_businessdes_beforebuy);

		ivCommentNull = (ImageView) findViewById(R.id.iv_comment_null);

		llintroduce = (LinearLayout) findViewById(R.id.ll_introduce);// 活动所有
		rlChoose = (RelativeLayout) findViewById(R.id.rl_choose);

		// 按钮点击事件的处理
		ibtnBack.setOnClickListener(this);// 返回
		ibtnShare.setOnClickListener(this);// 分享
		btnBook.setOnClickListener(this);// 预定
		rlMore.setOnClickListener(this);// 查看更多图文
		rlHui.setOnClickListener(this);// 团购优惠
		ibtnCall.setOnClickListener(this);// 电话拨打
		rlComment.setOnClickListener(this);// 跳转至评论
		rlChoose.setOnClickListener(this);

		cbIsFav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (!cbIsFav.isPressed())
					return;
				if (countid == 0) {
					cbIsFav.toggle();
					Intent mIntent = new Intent();
					mIntent.setClass(getApplication(), UserLoginActivity.class);
					startActivity(mIntent);
				} else {
					ArrayMap<String, String> map = new ArrayMap<String, String>();
					map.put("countid", countid + "");// 用户id
					map.put("goodid", id);// 商品id
					initData(map, hpCantant.UFAVORITE_URL,
							hpCantant.LABLE_UFAVORITE);
				}
			}
		});

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.ibtn_back:
			this.finish();
			break;
		case R.id.ibtn_share:
			showSharePop((RelativeLayout) findViewById(R.id.rl_homeDetailLayout));
			break;
		case R.id.ibtn_call:
			AlertDialog(mDetail.getTel());// 弹出提示框
			break;
		case R.id.rl_choose:
			showPopupWindow(btnBook);
			break;
		case R.id.rl_more:
			Intent intent = new Intent(GoodDetailActivity.this,
					WebActivity.class);
			intent.putExtra("url", hpCantant.UGoodDetail_URL+id);
			startActivity(intent);
			break;
		case R.id.rl_comment:
			startActivity(new Intent(GoodDetailActivity.this,
					UCommentListActivity.class));
			break;
		case R.id.btn_book:
			ToPay();
			break;
		case R.id.rl_detail_good_hui:
			if (mDetail.getItem() != null && mDetail.getItem().size() > 0)
				showHuiPop((RelativeLayout) findViewById(R.id.rl_homeDetailLayout));
			else {
				Toast.makeText(GoodDetailActivity.this, "该场地无优惠活动",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.ibtn_pop_home_detail_share:// 分享 dialog
			ArrayMap<String, String> map = new ArrayMap<String, String>();
			map.put("lat", ApplicationController.getInstance().getLat());
			map.put("lng", ApplicationController.getInstance().getLng());
			map.put("contents", edtContents.getText().toString());
			map.put("classid", "2");
			map.put("goodid", id);
			map.put("countid", ApplicationController.getInstance().getUser()
					.getInt("countid", 0)
					+ "");
			Log.i(getPackageName(), map.toString());
			initData(map, hpCantant.UDYNAMICCREATE_URL,
					hpCantant.LABLE_UDYNAMICCREATE);
			break;
		case R.id.ibtn_detail_pop_del:
			IsShowPop = false;
			popupWindow.dismiss();
		case R.id.ibtn_detail_pop_sum:
			if (Integer.parseInt(edtNum.getText().toString()) > 1) {
				num = Integer.parseInt(edtNum.getText().toString()) - 1;
				edtNum.setText(num + "");
				new GetGoodPrice(mHandler, mDetail.getId() + "", num,
						ChoosePrice + "");
			}
			break;
		case R.id.ibtn_detail_pop_add:
			num = (Integer.parseInt(edtNum.getText().toString()) + 1);
			edtNum.setText(num + "");
			new GetGoodPrice(mHandler, mDetail.getId() + "", num, ChoosePrice
					+ "");
			break;
		case R.id.btn_pop_home_detail_hui:
			popupWindow.dismiss();
			break;
		default:
			break;
		}

	}

	/**
	 * 跳转至订单界面
	 */
	private void ToPay() {
		if (countid == 0) {
			startActivity(new Intent(GoodDetailActivity.this,
					UserLoginActivity.class));
			return;
		}
		Intent mIntent = new Intent(GoodDetailActivity.this, PayActivity.class);
		Bundle mBundle = new Bundle();
		mBundle.putString("orderDate", ChooseTime);// 选择的时间
		mBundle.putString("id", id);// 商品id
		mBundle.putString("imageurl", ImageUrl);// 商品图片
		mBundle.putString("countid", countid + "");// 用户id
		mBundle.putString("classid", classid + "");// 商品类型id
		mBundle.putString("orderTitle", mDetail.getTitle());// 商品名称

		if (classid.equals(TYPE_HP)) {
			mBundle.putString("priceid", priceid);
			mBundle.putFloat("orderPrice", ChoosePrice);// 该商品价格
			mBundle.putInt("count", num);// 该商品价格

			if (ChooseTime == null) {
				if (IsShowPop) {
					popupWindow.dismiss();
					IsShowPop = false;
				} else
					showPopupWindow(btnBook);
			} else {
				if (IsShowPop) {
					popupWindow.dismiss();
					IsShowPop = false;
				}
				mIntent.putExtras(mBundle);
				startActivity(mIntent);
			}
		} else {
			mBundle.putString("orderPrice", mDetail.getPrice());// 该商品价格
			mIntent.putExtras(mBundle);
			startActivity(mIntent);
		}

	}

	/**
	 * 弹出分享窗口
	 */
	private EditText edtContents;
	private View huiView = null;

	/**
	 * 弹出团购优惠
	 * 
	 * @param view
	 */
	private void showHuiPop(View view) {
		huiView = LayoutInflater.from(GoodDetailActivity.this).inflate(
				R.layout.pop_home_detail_hui, null, false);
		popupWindow = new PopupWindow(huiView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, true);
		popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		// 显示在控件上方
		popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
		((Button) huiView.findViewById(R.id.btn_pop_home_detail_hui))
				.setOnClickListener(this);
		ListView lv1 = (ListView) huiView
				.findViewById(R.id.lv_pop_home_detail_hui_1);
		GoodDetailHuiAdapter mAdapter = new GoodDetailHuiAdapter(
				GoodDetailActivity.this, mDetail.getItem());
		lv1.setAdapter(mAdapter);
	}

	private void showSharePop(View view) {
		View shareView = LayoutInflater.from(GoodDetailActivity.this).inflate(
				R.layout.pop_home_detail_share, null, false);
		popupWindow = new PopupWindow(shareView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, true);
		popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		// 显示在控件上方
		popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
		try {
			setImageByUrl(shareView, R.id.iv_pop_home_detail_share_image,
					ImageUrl);
		} catch (Exception e) {
		}

		((ImageButton) shareView.findViewById(R.id.ibtn_pop_home_detail_share))
				.setOnClickListener(this);
		edtContents = (EditText) shareView
				.findViewById(R.id.edt_pop_home_detail_share);
		((TextView) shareView.findViewById(R.id.tv_pop_home_detail_share_title))
				.setText(mDetail.getTitle());

	}

	private EditText edtNum;
	private ImageButton ibtnSum, ibtnAdd;
	private TextView tvPrice;

	/**
	 * 商品时段选择
	 * 
	 * @param view
	 */
	@SuppressLint("NewApi")
	public void showPopupWindow(View view) {
		final int tSize = mDetail.getPricelist().getDateprice().size();
		if (mDetail.getPricelist().getDateprice() == null || tSize == 0)
			return;
		IsShowPop = true;
		// 一个自定义的布局，作为显示的内容
		View contentView = LayoutInflater.from(GoodDetailActivity.this)
				.inflate(R.layout.pop_home_detail_book, null);
		WindowManager wm = this.getWindowManager();
		int height = wm.getDefaultDisplay().getHeight();
		popupWindow = new PopupWindow(contentView, LayoutParams.MATCH_PARENT,
				height - view.getHeight(), true);
		popupWindow.setFocusable(false);// 这里必须设置为true才能点击区域外或者消失
		popupWindow.setOutsideTouchable(false);// setFocusable,setOutsideTouchable均设为false是外部按钮才能响应
		popupWindow.setTouchable(true);// 这个控制PopupWindow内部控件的点击事件
		popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		// 显示在控件上方
		popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, 0);
		popupWindow.update();
		ibtnDel = (ImageButton) contentView
				.findViewById(R.id.ibtn_detail_pop_del);
		ibtnDel.setOnClickListener(this);
		try {
			setImageByUrl(contentView, R.id.iv_detail_pop_image, ImageUrl);
		} catch (Exception e) {
		}
		TextView tvPopTitle = (TextView) contentView
				.findViewById(R.id.tv_detail_pop_title);
		// ((TextView) contentView.findViewById(R.id.tv_detail_pop_cheapname))
		// .setText(mHPData.getCheapname());
		edtNum = (EditText) contentView.findViewById(R.id.edt_detail_pop_num);
		edtNum.setEnabled(false);
		ibtnSum = (ImageButton) contentView
				.findViewById(R.id.ibtn_detail_pop_sum);
		ibtnSum.setOnClickListener(this);
		ibtnAdd = (ImageButton) contentView
				.findViewById(R.id.ibtn_detail_pop_add);
		ibtnAdd.setOnClickListener(this);
		final TextView tvTime = (TextView) contentView
				.findViewById(R.id.tv_detail_pop_time);
		tvPrice = (TextView) contentView.findViewById(R.id.tv_detail_pop_price);
		tvPopTitle.setText(mDetail.getTitle());
		if (ChooseTime == null) {
			tvTime.setText("还未选择时间段");
			tvPrice.setText("价格：");
		} else {
			tvPrice.setText("价格:￥" + ChoosePrice);
			tvTime.setText(ChooseTime);
			edtNum.setText(num + "");
		}
		RadioGroup group = (RadioGroup) contentView.findViewById(R.id.rg_price);
		// 动态添加Button
		for (int i = 0; i < tSize; i++) {
			final String date = mDetail.getPricelist().getDateprice().get(i)
					.getBegintime()
					+ "~"
					+ mDetail.getPricelist().getDateprice().get(i).getEndtime();

			RadioButton mRBtn = new RadioButton(this);
			Bitmap bg = null;
			mRBtn.setButtonDrawable(new BitmapDrawable(bg));// 设RadioButton的按钮为空
			mRBtn.setBackground(getResources().getDrawable(
					R.drawable.radiobutton_drawable));
			mRBtn.setText(date);
			mRBtn.setTextColor(getResources().getColor(
					R.drawable.radiobutton_color));
			RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			DisplayMetrics dm = getResources().getDisplayMetrics();
			final int margin = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 8, dm);
			params.setMargins(margin, margin, margin, margin);
			mRBtn.setLayoutParams(params);
			mRBtn.setGravity(Gravity.CENTER);

			group.addView(mRBtn, params);
			group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					checkedId = (checkedId - 1) % tSize;
					Log.i("??checked id", checkedId + "");
					try {
						ChoosePrice = Float.parseFloat(mDetail.getPricelist()
								.getDateprice().get(checkedId).getPrice());
						ChoosePrice = (float) (Math.round(ChoosePrice * 100)) / 100;
					} catch (Exception e) {
						ChoosePrice = 0;
					}
					ChooseTime = mDetail.getPricelist().getDateprice()
							.get(checkedId).getBegintime()
							+ "~"
							+ mDetail.getPricelist().getDateprice()
									.get(checkedId).getEndtime();
					tvTime.setText(ChooseTime);
					tvPrice.setText("价格:￥" + ChoosePrice);
					num = 1;
					edtNum.setText(num + "");
					priceid = mDetail.getPricelist().getDateprice()
							.get(checkedId).getId();
				}
			});
		}
	}

	/**
	 * 弹出电话拨打
	 * 
	 * @param tel
	 */
	private void AlertDialog(final String tel) {
		new AlertDialog.Builder(GoodDetailActivity.this)
		// 设置对话框标题
				.setMessage("联系我时,请说是在轰趴兔上看到的,谢谢!")
				// 设置显示的内容

				.setPositiveButton("拨打", new DialogInterface.OnClickListener() {// 添加确定按钮
							@Override
							public void onClick(DialogInterface dialog,
									int which) {// 确定按钮的响应事件
								// 用intent启动拨打电话
								Intent intent = new Intent(Intent.ACTION_CALL,
										Uri.parse("tel:" + tel));
								startActivity(intent);
							}
						})//
				.setNegativeButton("取消", null)//
				.show();// 在按键响应事件中显示此对话框
	}

	private ImageLoader imageLoader;

	/**
	 * 为TextView设置值
	 * 
	 * @param view
	 * @param id
	 * @return
	 */
	public void setTextById(int id, String text) {
		TextView view = (TextView) findViewById(id);
		if (text == null || text.isEmpty())
			text = "null";
		view.setText(text);
	}

	public void setImageByUrl(View mView, int viewId, String url) {
		ImageView view = (ImageView) mView.findViewById(viewId);
		if (imageLoader == null)
			imageLoader = ApplicationController.getInstance().getImageLoader();
		ImageListener listener = ImageLoader.getImageListener(view,
				R.drawable.fail_image, R.drawable.fail_image);
		imageLoader.get(url, listener);
	}

	public void setImageByUrl1(int viewId, String url) {
		ImageView view = (ImageView) findViewById(viewId);
		if (imageLoader == null)
			imageLoader = ApplicationController.getInstance().getImageLoader();
		ImageListener listener = ImageLoader.getImageListener(view,
				R.drawable.fail_image, R.drawable.fail_image);
		imageLoader.get(url, listener);
	}

}
