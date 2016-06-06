package com.tgj.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.hp.R;
import com.hp.activity.MainActivity_01;
import com.hp.application.ApplicationController;
import com.hp.utils.hpCantant;
import com.hp.widget.CircleImageView;
import com.tgj.activity.TGJUserCommentActivity;
import com.tgj.activity.TGJUserIncomeActivity;

public class TGJUserFragment extends Fragment implements OnClickListener {

	private RelativeLayout rlIncome;
	private RelativeLayout rlComment;
	// 商家头像及昵称
	private CircleImageView UserIcon;
	private TextView tvNickName;

	private Intent intent;
	private SharedPreferences userInfoPreferences;
	private View view;
	private RelativeLayout SwitchToTheUser;

	private ImageLoader imageLoader;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.tgj_fragment_user, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		init();
	}

	private void init() {
		userInfoPreferences = getActivity().getSharedPreferences(
				hpCantant.USERINFO_DATA, Context.MODE_PRIVATE);
		if (userInfoPreferences.getInt("countid", 0) != 0) {
			// 显示头像
			UserIcon = (CircleImageView) view.findViewById(R.id.iv_userIcon);
			tvNickName = (TextView) view.findViewById(R.id.tv_userNickName);
			if (imageLoader == null) {
				imageLoader = ApplicationController.getInstance()
						.getImageLoader();
			}
			ImageListener listener = ImageLoader.getImageListener(UserIcon,
					R.drawable.user_null, R.drawable.user_null);
			imageLoader
					.get(userInfoPreferences.getString("icon", ""), listener);
		}
		tvNickName.setText(userInfoPreferences.getString("nickname", ""));
	}

	private void initView() {
		rlIncome = (RelativeLayout) view.findViewById(R.id.rl_income);
		rlComment = (RelativeLayout) view.findViewById(R.id.rl_comment);
		SwitchToTheUser = (RelativeLayout) view
				.findViewById(R.id.SwitchToTheUser);

		rlIncome.setOnClickListener(this);
		rlComment.setOnClickListener(this);
		SwitchToTheUser.setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.rl_income:
			intent = new Intent(getActivity(), TGJUserIncomeActivity.class);
			break;
		case R.id.rl_comment:
			intent = new Intent(getActivity(), TGJUserCommentActivity.class);
			break;
		case R.id.SwitchToTheUser:
			intent = new Intent(getActivity(), MainActivity_01.class);
			startActivity(intent);
			getActivity().finish();
			intent = null;
			break;
		default:
			break;
		}
		if (intent != null)
			startActivity(intent);
	}

}
