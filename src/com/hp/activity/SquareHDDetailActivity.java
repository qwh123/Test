package com.hp.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.hp.R;
import com.hp.application.ApplicationController;
import com.hp.bean.UActiveDetail;
import com.hp.bean.UActiveDetail.Data;
import com.hp.bean.UActiveDetail.Data.Member_list;
import com.hp.commonAdapter.CommonAdapter;
import com.hp.commonAdapter.ViewHolder;
import com.hp.dao.UActiveDetailDao;
import com.hp.utils.CommonUtils;
import com.hp.utils.FastBlur;
import com.hp.utils.hpCantant;
import com.hp.widget.MyGridView;
import com.hp.widget.TopBar;
import com.hp.widget.TopBar.topBarClickListener;

public class SquareHDDetailActivity extends Activity {
	private Data mData;

	private Handler mHandler = new Handler() {
		private JSONObject js;
		private UActiveDetailDao mActiveDetailDao;

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == hpCantant.LABLE_UACTIVEDETAIL) {
				Bundle bundle = msg.getData();
				mActiveDetailDao = new UActiveDetailDao();
				try {
					js = new JSONObject(bundle.getString(hpCantant.GETDATA));
					if (js.getString("code").equals(hpCantant.SUCCESS_CODE)) {
						mData = mActiveDetailDao.loadActiveDetail(bundle
								.getString(hpCantant.GETDATA));
						initUI(mData);
					}
					loadingLayout.setVisibility(View.GONE);
					mScroView.setVisibility(View.VISIBLE);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else if (msg.what == hpCantant.LABLE_UACTIVESIGNUP) {
				Bundle bundle = msg.getData();
				try {
					js = new JSONObject(bundle.getString(hpCantant.GETDATA));
					if (js.getString("code").equals(hpCantant.SUCCESS_CODE)) {
						Toast.makeText(SquareHDDetailActivity.this, "报名成功", 0)
								.show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	};
	private String activeid;// 活动id
	private RelativeLayout loadingLayout;
	private ScrollView mScroView;

	private ImageLoader imageLoader;

	private MyGridView gvMember;

	private CommonAdapter<UActiveDetail.Data.Member_list> mMemberAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_square_hd_detail);
		activeid = getIntent().getStringExtra("activeid");
		Log.i("activeid:", activeid);
		initView();
		getData();
	}

	/**
	 * 初始化UI界面
	 * 
	 * @param mData2
	 */
	protected void initUI(final Data data) {
		gvMember = (MyGridView) findViewById(R.id.mgv_hd_detail_bm_icon);
		SetBGFromImageByUrl(R.id.iv_hd_detail_icon, data.getIcon());
		setText(R.id.tv_hd_detail_title, data.getTitle());
		setText(R.id.tv_hd_detail_time, data.getBegintime());
		setText(R.id.tv_hd_detail_num, data.getNum());
		setText(R.id.tv_hd_detail_adress, data.getAddress());
		setText(R.id.tv_hd_detail_date,
				data.getBegintime() + "-" + data.getEndtime());
		setText(R.id.tv_hd_detail_style, data.getMember());
		setText(R.id.tv_hd_detail_summary, data.getSummary());
		setText(R.id.tv_hd_detail_fqr_name, data.getHostinfo().getNickname());
		// 已报名成员数及其头像
		setText(R.id.tv_hd_detail_bm_num, data.getMember_list().size() + "人");
		mMemberAdapter = new CommonAdapter<UActiveDetail.Data.Member_list>(
				this, data.getMember_list(), R.layout.item_square_hddetail_icon) {
			@Override
			public void convert(ViewHolder helper, Member_list item) {
				helper.setImageByUrl(R.id.iv_member, item.getIcon());
			}
		};
		// 添加并且显示
		gvMember.setAdapter(mMemberAdapter);
		((Button) findViewById(R.id.btn_hd_detail_chat))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(SquareHDDetailActivity.this,
								ChatActivity.class);
						intent.putExtra("groupid", data.getGroupid());
						startActivity(intent);
					}
				});

	}

	/**
	 * 初始化UI组件
	 */
	private void initView() {
		loadingLayout = (RelativeLayout) findViewById(R.id.square_hd_loading);
		mScroView = (ScrollView) findViewById(R.id.sv_hd_content);
		TopBar tBar = (TopBar) findViewById(R.id.topbar_hd_detail);
		tBar.setTitleText("活动");
		tBar.setRighttImageResource(R.drawable.icon_more);
		tBar.setOnTopBarClickListener(new topBarClickListener() {
			@Override
			public void rightClick() {
				Toast.makeText(SquareHDDetailActivity.this, "click", 1).show();
			}

			@Override
			public void leftClick() {
				finish();
			}
		});
		((Button) findViewById(R.id.btn_hd_detail_sign))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						ArrayMap<String, String> map = new ArrayMap<String, String>();
						map.put("activeid", activeid);
						map.put("countid", ApplicationController.getInstance()
								.getUser().getInt("countid", 0)
								+ "");
						CommonUtils.getData(mHandler, map,
								hpCantant.UACTIVESIGNUP_URL,
								hpCantant.LABLE_UACTIVESIGNUP);
					}
				});

	}

	private void getData() {
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("activeid", activeid);
		CommonUtils.getData(mHandler, map, hpCantant.UACTIVEDETAIL_URL,
				hpCantant.LABLE_UACTIVEDETAIL);
	}

	private void setText(int resid, String text) {
		TextView view = (TextView) findViewById(resid);
		view.setText(text);
	}

	public void setHDImageByUrl(int viewId, String url) {
		ImageView view = (ImageView) findViewById(viewId);
		if (imageLoader == null)
			imageLoader = ApplicationController.getInstance().getImageLoader();
		ImageListener listener = ImageLoader.getImageListener(view,
				R.drawable.user_null, R.drawable.user_null);
		imageLoader.get(url, listener);
	}

	@SuppressLint("NewApi")
	public void SetBGFromImageByUrl(int viewId, String url) {
		LinearLayout ll = (LinearLayout) findViewById(R.id.linearLayout1);
		ImageView view = (ImageView) findViewById(viewId);
		if (imageLoader == null)
			imageLoader = ApplicationController.getInstance().getImageLoader();
		ImageListener listener = ImageLoader.getImageListener(view,
				R.drawable.user_null, R.drawable.user_null);
		imageLoader.get(url, listener);
		view.buildDrawingCache();
		Bitmap bitmap = FastBlur.doBlur(view.getDrawingCache(), 10, false);
		ll.setBackground(new BitmapDrawable(bitmap));
	}

}
