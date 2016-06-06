package com.hp.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.hp.R;
import com.hp.adapter.CommentAdapter;
import com.hp.application.ApplicationController;
import com.hp.bean.UDynamicCommentList;
import com.hp.bean.UDynamicCommentList.Data;
import com.hp.commonAdapter.CommonAdapter;
import com.hp.dao.UDynamicCommentDao;
import com.hp.fragment.ImagePagerActivity;
import com.hp.utils.CommonUtils;
import com.hp.utils.Constants;
import com.hp.utils.hpCantant;
import com.hp.utils.volley.VolleyUtils;
import com.hp.utils.volley.VolleyUtilsImpl;
import com.hp.widget.CircleImageView;
import com.hp.widget.MyGridView;
import com.hp.widget.MyListView;
import com.hp.widget.SelectPicPop;
import com.hp.widget.TopBar;
import com.hp.widget.TopBar.topBarClickListener;

public class SquareDTDetailActivity extends Activity implements
		OnClickListener, OnItemClickListener {
	private CommonAdapter<String> mImageAdapter;// 显示图片的adapter
	// private CommonAdapter<UDynamicCommentList.Data> mCommentAdapter;//
	// 显示评论列表的adapter
	// private CommonAdapter<UDynamicCommentList.Data> mReplyAdapter;//
	// 显示评论列表的adapter
	ArrayList<String> mImages;// 图片集
	private List<UDynamicCommentList.Data> mDatas;// 评论列表的数据集
	private ImageButton ibtnComment;// 分享，评论按钮

	private LinearLayout llComment;// 底部评论线性布局
	private MyListView mLvComment, mLvReply;// 父评论列表，子评论列表
	private CheckBox ckLikes;
	private Button btnSend;// 发送按钮
	private EditText edtComment;

	int PID = 0;

	private Handler mHandler = new Handler() {
		private JSONObject js;
		private UDynamicCommentDao mUDynamicComment;

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle bundle = null;
			switch (msg.what) {
			case hpCantant.LABLE_UDYNAMICCOMMENTLIST:
				bundle = msg.getData();
				mUDynamicComment = new UDynamicCommentDao();
				try {
					js = new JSONObject(bundle.getString(hpCantant.GETDATA));
					if (js.getString("code").equals(hpCantant.SUCCESS_CODE)) {
						mDatas = mUDynamicComment.getComments(bundle
								.getString(hpCantant.GETDATA));
						if (!mDatas.isEmpty() && mDatas != null)
							runOnUiThread(new Runnable() {
								public void run() {
									initCommentUI(mDatas);
								}
							});
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;
			case hpCantant.LABLE_UDynamicComment:
				bundle = msg.getData();
				try {
					js = new JSONObject(bundle.getString(hpCantant.GETDATA));
					llComment.setVisibility(View.GONE);
					onFocusChange(false);
					Toast.makeText(SquareDTDetailActivity.this,
							js.getString("code"), 0).show();
					getData();
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				break;
			case hpCantant.LABLE_UDYNAMICPRAISE:
				bundle = msg.getData();
				try {
					js = new JSONObject(bundle.getString(hpCantant.GETDATA));
					if (js.getString("code").equals(hpCantant.SUCCESS_CODE)) {
						Toast.makeText(SquareDTDetailActivity.this,
								js.getString("summary"), Toast.LENGTH_SHORT)
								.show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		}

	};
	private String classid = null;
	private String dynamicid = null;
	private String nickname = null;
	private String icon = null;
	private String contents = null;
	private String date = null;
	private String distance = null;
	private String images = null;
	// 打开方式
	private String style = "1";
	private String userid = null;
	private String likes = null;
	private String goodid = null;
	private String goodicon = null;
	private String goodtitle = null;
	private String goodclass = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_square_dt_detail);
		Bundle bundle = getIntent().getExtras();
		style = bundle.getString("style");
		userid = bundle.getString("countid");
		dynamicid = bundle.getString("id");
		classid = bundle.getString("classid");
		nickname = bundle.getString("nickname");
		icon = bundle.getString("icon");
		contents = bundle.getString("contents");
		date = bundle.getString("date");
		distance = bundle.getString("distance");
		likes = bundle.getString("likes");
		images = bundle.getString("images");
		goodid = bundle.getString("goodid");
		goodicon = bundle.getString("goodicon");
		goodtitle = bundle.getString("goodtitle");
		goodclass = bundle.getString("goodclass");
		initView();
		getData();
	}

	private void getData() {
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("dynamicid", dynamicid);
		CommonUtils.getData(mHandler, map, hpCantant.UDYNAMICCOMMENTLIST_URL,
				hpCantant.LABLE_UDYNAMICCOMMENTLIST);
	}

	private SelectPicPop menuWindow;
	private MyGridView gvImage;
	private LinearLayout llShare;
	private ImageView ivGood;

	private void initView() {
		TopBar tBar = (TopBar) findViewById(R.id.topbar_dtdetail);
		tBar.setTitleText("动态详情");
		tBar.setRighttImageResource(R.drawable.icon_more);
		tBar.setOnTopBarClickListener(new topBarClickListener() {
			@Override
			public void rightClick() {
				String[] titles = { "分享", "举报", "取消" };
				// 更换头像点击事件
				menuWindow = new SelectPicPop(SquareDTDetailActivity.this,
						titles, more);
				menuWindow.showAtLocation(findViewById(R.id.ll_dt_detail),
						Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
			}

			@Override
			public void leftClick() {
				finish();
			}
		});
		setImageByUrl1(R.id.iv_squary_dt, icon);
		((CircleImageView) findViewById(R.id.iv_squary_dt))
				.setOnClickListener(this);
		setText(R.id.tv_squary_dt_name, nickname);
		setText(R.id.tv_squary_dt_time, date);
		setText(R.id.tv_squary_dt_contents, contents);
		setText(R.id.tv_squary_dt_distance, distance);
		// ibtnShare = (ImageButton) findViewById(R.id.ibtn_squary_dt_share);
		ibtnComment = (ImageButton) findViewById(R.id.ibtn_squary_dt_comment);
		llShare = (LinearLayout) findViewById(R.id.ll_squary_dt_share);
		mLvComment = (MyListView) findViewById(R.id.lv_square_dt_comment);
		llComment = (LinearLayout) findViewById(R.id.ll_dt_detail_comment);
		btnSend = (Button) findViewById(R.id.btn_dt_detail_send);
		edtComment = (EditText) findViewById(R.id.edt_dt_detail);
		ckLikes = (CheckBox) findViewById(R.id.cb_squary_dt_dz);
		if (likes.equals("0")) {
			ckLikes.setChecked(false);
		} else {
			ckLikes.setChecked(true);
		}
		/**
		 * 动态点赞，取消赞
		 */
		ckLikes.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			private int countid;

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				countid = ApplicationController.getInstance().getUser()
						.getInt("countid", 0);
				if (countid == 0) {
					Intent mIntent = new Intent();
					mIntent.setClass(SquareDTDetailActivity.this,
							UserLoginActivity.class);
					SquareDTDetailActivity.this.startActivity(mIntent);
				} else {
					ArrayMap<String, String> map = new ArrayMap<String, String>();
					map.put("countid", countid + "");//
					map.put("dynamicid", dynamicid);//
					CommonUtils.getData(mHandler, map,
							hpCantant.UDYNAMICPRAISE_URL,
							hpCantant.LABLE_UDYNAMICPRAISE);
				}
			}
		});

		gvImage = ((MyGridView) findViewById(R.id.gv_squary_dt_images));
		if (classid.equals(Constants.DYNAMIC_TYPE.NATIVE) && !images.isEmpty()) {
			StringTokenizer st = new StringTokenizer(images, ";");
			mImages = new ArrayList<String>();
			while (st.hasMoreTokens()) {
				mImages.add(st.nextToken());
			}
			mImageAdapter = new CommonAdapter<String>(
					SquareDTDetailActivity.this, mImages,
					R.layout.item_square_dt_gridview) {
				@Override
				public void convert(com.hp.commonAdapter.ViewHolder helper,
						String item) {
					helper.setImageByUrl(R.id.iv_dt_image, item);
				}
			};
			gvImage.setAdapter(mImageAdapter);
			gvImage.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					Intent intent = new Intent(SquareDTDetailActivity.this,
							ImagePagerActivity.class);
					// 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
					intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS,
							mImages);
					intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX,
							position);
					SquareDTDetailActivity.this.startActivity(intent);
				}
			});

		} else if (classid.equals(Constants.DYNAMIC_TYPE.SHARE)) {
			gvImage.setVisibility(View.GONE);
			llShare.setVisibility(View.VISIBLE);
			ivGood = (ImageView) findViewById(R.id.iv_squary_dt_share_icon);
			((TextView) findViewById(R.id.tv_squary_dt_share_title))
					.setText(goodtitle);
			ImageListener listener1 = ImageLoader.getImageListener(ivGood,
					R.drawable.logo_homeparty, R.drawable.logo_homeparty);
			imageLoader.get(goodicon, listener1);
			llShare.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(SquareDTDetailActivity.this,
							GoodDetailActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("imageurl", goodicon);
					bundle.putString("id", goodid);
					bundle.putString("classid", goodclass);
					intent.putExtras(bundle);
					SquareDTDetailActivity.this.startActivity(intent);
				}
			});
		}

		mLvComment.setOnItemClickListener(this);

		// ibtnShare.setOnClickListener(this);
		ibtnComment.setOnClickListener(this);
		btnSend.setOnClickListener(this);
		if (style.equals("3")) {
			llComment.setVisibility(View.VISIBLE);
			onFocusChange(true);
		}
	}

	// 分享或者举报
	private OnClickListener more = new OnClickListener() {
		@Override
		public void onClick(View v) {
			menuWindow.dismiss();
			switch (v.getId()) {
			// 拍照
			case R.id.takePhotoBtn:
				Toast.makeText(SquareDTDetailActivity.this, "获取第三方引用失败", 0)
						.show();
				break;
			// 相册选择图片
			case R.id.pickPhotoBtn:
				Toast.makeText(SquareDTDetailActivity.this, "举报", 0).show();
				break;
			default:
				break;
			}
		}
	};
	private ImageLoader imageLoader;

	protected void setText(int id, String text) {
		TextView view = (TextView) findViewById(id);
		view.setText(text);
	}

	private void setImageByUrl1(int ivSquaryDt, String icon2) {
		ImageView view = (ImageView) findViewById(ivSquaryDt);
		if (imageLoader == null)
			imageLoader = ApplicationController.getInstance().getImageLoader();
		ImageListener listener = ImageLoader.getImageListener(view,
				R.drawable.fail_image, R.drawable.fail_image);
		imageLoader.get(icon2, listener);
	}

	private void initCommentUI(List<Data> mDatas) {
		CommentAdapter mAdapter = new CommentAdapter(this);
		mAdapter.setList(mDatas);
		mLvComment.setAdapter(mAdapter);
	}

	protected void publishComment(int pid) {
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("countid", ApplicationController.getInstance().getUser()
				.getInt("countid", 0)
				+ "");
		map.put("contents", edtComment.getText().toString());
		map.put("dynamicid", dynamicid);
		map.put("pid", pid + "");
		CommonUtils.getData(mHandler, map, hpCantant.UDynamicComment_URL,
				hpCantant.LABLE_UDynamicComment);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.iv_squary_dt:
			Intent intent = new Intent(SquareDTDetailActivity.this,
					DTUserInfoActivity.class);
			intent.putExtra("nickname", nickname);
			intent.putExtra("userid", userid);
			startActivity(intent);
			break;
		// case R.id.ibtn_squary_dt_share:
		// Toast.makeText(SquareDTDetailActivity.this, "获取第三方引用失败", 0).show();
		// break;
		case R.id.ibtn_squary_dt_comment:
			llComment.setVisibility(View.VISIBLE);
			edtComment.setHint("回复" + nickname);
			onFocusChange(true);
			break;
		case R.id.btn_dt_detail_send:
			if (edtComment.getText().toString().isEmpty()) {
				Toast.makeText(SquareDTDetailActivity.this, "评论不能为空", 0).show();
				return;
			}
			publishComment(PID);
			edtComment.setText("");
			PID = 0;
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long arg3) {
		llComment.setVisibility(View.VISIBLE);
		edtComment.setHint("回复" + mDatas.get(position).getNickname());
		onFocusChange(true);
		PID = Integer.parseInt(mDatas.get(position).getCountid());

	}

	/**
	 * 显示或隐藏输入法
	 */
	public void onFocusChange(boolean hasFocus) {
		final boolean isFocus = hasFocus;
		(new Handler()).postDelayed(new Runnable() {
			public void run() {
				InputMethodManager imm = (InputMethodManager) edtComment
						.getContext().getSystemService(INPUT_METHOD_SERVICE);
				if (isFocus) {
					// 显示输入法
					imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
				} else {
					// 隐藏输入法
					imm.hideSoftInputFromWindow(edtComment.getWindowToken(), 0);
				}
			}
		}, 100);
	}

}
