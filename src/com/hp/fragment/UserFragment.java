package com.hp.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.hp.R;
import com.hp.activity.UserCollectActivity;
import com.hp.activity.UserDataActivity;
import com.hp.activity.UserDynamicActivity;
import com.hp.activity.UserLoginActivity;
import com.hp.activity.UserSettingActivity;
import com.hp.activity.WebActivity;
import com.hp.activity.userOrder.UserOrderFramentActivity;
import com.hp.application.ApplicationController;
import com.hp.bean.UJumpBussiness.Data;
import com.hp.dao.UJumpBussinessDao;
import com.hp.utils.CommonUtils;
import com.hp.utils.hpCantant;
import com.hp.widget.CircleImageView;
import com.tgj.activity.TGJMainActivity;

public class UserFragment extends Fragment implements OnClickListener {
	private Activity mActivity;
	private View rootView;
	private ImageButton btnSetting;// 消息按钮
	// private RelativeLayout activity;// 我的活动
	private RelativeLayout order;// 我的订单
	private RelativeLayout collect;// 我的收藏
	private RelativeLayout publish;// 我的动态
	// private RelativeLayout qiandao;// 签到

	private TextView tvNickName;// 昵称
	private TextView tvPerSign;// 简介
	private CircleImageView ivUserIcon;

	// 用户登陆显示的状态
	private LinearLayout rlUserYes;
	// 用户未登陆显示的状态
	private LinearLayout llUserNo;

	private ImageLoader imageLoader;
	private SharedPreferences userPre;
	private int countid;
	private RelativeLayout SwitchToTheMerchant;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.fragment_user, container,
					false);
		}
		// 缓存的rootView需要判断是否已经被加过parent，
		// 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}

		return rootView;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initUI();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		rlUserYes = (LinearLayout) view.findViewById(R.id.rl_user);
		llUserNo = (LinearLayout) view.findViewById(R.id.ll_no_user);

		btnSetting = (ImageButton) view.findViewById(R.id.btn_setting);
		order = (RelativeLayout) view.findViewById(R.id.rl_userOrder);
		collect = (RelativeLayout) view.findViewById(R.id.id_rl_collect);
		publish = (RelativeLayout) view.findViewById(R.id.id_rl_publish);
		// activity = (RelativeLayout) view.findViewById(R.id.id_rl_active);
		// qiandao = (RelativeLayout) view.findViewById(R.id.id_rl_qiandao);
		tvNickName = (TextView) view.findViewById(R.id.tv_userNickName);
		tvPerSign = (TextView) view.findViewById(R.id.tv_userPerSign);
		SwitchToTheMerchant = (RelativeLayout) view
				.findViewById(R.id.SwitchToTheMerchant);

	}

	/**
	 * 初始化UI
	 */
	private void initUI() {
		userPre = ApplicationController.getInstance().getUser();
		countid = userPre.getInt("countid", 0);
		if (countid != 0) {
			rlUserYes.setVisibility(View.VISIBLE);
			llUserNo.setVisibility(View.GONE);

			if (imageLoader == null)
				imageLoader = ApplicationController.getInstance()
						.getImageLoader();
			// 显示头像
			ivUserIcon = (CircleImageView) rootView
					.findViewById(R.id.iv_userIcon);
			if (userPre.getString("icon", "") != null) {
				ImageListener listener = ImageLoader.getImageListener(
						ivUserIcon, R.drawable.user_null, R.drawable.user_null);
				imageLoader.get(userPre.getString("icon", ""), listener);
			}
			tvNickName.setText(userPre.getString("nickname", ""));
			if (userPre.getInt("sex", 0) == 0) {
				tvNickName.setLeft(R.drawable.icon_gril);
			} else if (userPre.getInt("sex", 0) == 1) {
				tvNickName.setLeft(R.drawable.icon_boy);
			} else {
				tvNickName.setLeft(R.drawable.icon_gril);
			}
			tvPerSign.setText(userPre.getString("persign", ""));
			rlUserYes.setOnClickListener(this);

		} else {
			rlUserYes.setVisibility(View.GONE);
			llUserNo.setVisibility(View.VISIBLE);
			llUserNo.setOnClickListener(this);
		}

		btnSetting.setOnClickListener(this);
		order.setOnClickListener(this);
		collect.setOnClickListener(this);
		publish.setOnClickListener(this);
		// activity.setOnClickListener(this);
		// qiandao.setOnClickListener(this);
		SwitchToTheMerchant.setOnClickListener(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		initUI();
	}

	/**
	 * 响应事件
	 */
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.rl_user:// 修改资料
			// 用户已登陆，进入修改界面
			intent.setClass(getActivity(), UserDataActivity.class);
			startActivity(intent);
			break;
		case R.id.ll_no_user:// 修改资料
			// 用户未登录进入登陆界面
			intent.setClass(getActivity(), UserLoginActivity.class);
			startActivity(intent);
			break;

		// case R.id.id_rl_active:// 我的活动
		// Toast.makeText(getActivity(), "我的活动", Toast.LENGTH_SHORT).show();
		// if (countid != 0) {
		// intent.setClass(getActivity(),
		// UserHuoDongFragmentActivity.class);
		// startActivity(intent);
		// } else {
		// intent.setClass(getActivity(), UserLoginActivity.class);
		// startActivity(intent);
		// }
		// break;
		case R.id.rl_userOrder:// 查看订单
			if (countid != 0) {
				intent.setClass(getActivity(), UserOrderFramentActivity.class);
				startActivity(intent);
			} else {
				intent.setClass(getActivity(), UserLoginActivity.class);
				startActivity(intent);
			}
			break;
		case R.id.id_rl_collect:// 我的收藏
			if (countid != 0) {
				intent.setClass(getActivity(), UserCollectActivity.class);
				startActivity(intent);
			} else {
				intent.setClass(getActivity(), UserLoginActivity.class);
				startActivity(intent);
			}
			break;
		case R.id.id_rl_publish:// 我的动态
			intent.setClass(getActivity(), UserDynamicActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_setting:// 设置按钮
			intent.setClass(getActivity(), UserSettingActivity.class);
			startActivity(intent);
			break;
		// case R.id.id_rl_qiandao:// 签到
		// Toast.makeText(getActivity(), "签到", Toast.LENGTH_SHORT).show();
		// break;

		case R.id.SwitchToTheMerchant:// 切换成商家版
			if (countid == 0) {
				Intent mIntent = new Intent();
				mIntent.setClass(getActivity(), UserLoginActivity.class);
				startActivity(mIntent);
			} else
				SwitchToTheMerchant();
			break;

		default:
			break;
		}
		// startActivity(intent);
	}

	/**
	 * 切换到商家
	 */
	ProgressDialog mDialog;

	private void SwitchToTheMerchant() {
		mDialog = ProgressDialog.show(getActivity(), "切换到商家版", "切换中...");
		mDialog.show();
		Handler mHandler = new Handler() {
			private UJumpBussinessDao ujb;
			private JSONObject js;
			private Data mUData;

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what == hpCantant.LABLE_UJumpBussiness) {
					Bundle bundle = msg.getData();
					ujb = new UJumpBussinessDao();
					mDialog.dismiss();
					try {
						js = new JSONObject(bundle.getString(hpCantant.GETDATA));
						if (js.getString("code").equals(hpCantant.SUCCESS_CODE)) {
							mUData = (Data) ujb.loadUJB(bundle
									.getString(hpCantant.GETDATA));
							// state==1则为商家，可跳转到商家页面，state==0则不是商家，跳转到链接
							if (mUData.getState().equals("1")) {
								Toast.makeText(getActivity(), "切换成商家版",
										Toast.LENGTH_SHORT).show();
								Intent intent = new Intent(getActivity(),
										TGJMainActivity.class);
								startActivity(intent);
								mActivity.finish();
							} else {
								Toast.makeText(getActivity(), "您还不是商家",
										Toast.LENGTH_SHORT).show();
								Intent intent = new Intent(getActivity(),
										WebActivity.class);
								intent.putExtra("url", mUData.getLink());
								startActivity(intent);
							}
						} else {
							Toast.makeText(getActivity(),
									js.getString("summary"), 1).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {

				}
			}
		};
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("countid", countid + "");
		CommonUtils.getData(mHandler, map, hpCantant.UJumpBussiness,
				hpCantant.LABLE_UJumpBussiness);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
