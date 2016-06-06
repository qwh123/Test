package com.hp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easemob.easeui.ui.EaseConversationListFragment;
import com.hp.R;
import com.hp.activity.MessageConversionActivity;
import com.hp.activity.MessageZanDetailActivity;
import com.hp.activity.UserLoginActivity;
import com.hp.application.ApplicationController;
import com.hp.utils.Constants;
import com.hp.widget.BadgeView;
import com.hp.widget.MyListView;
import com.hp.widget.TopBar;

public class MessageFragment extends Fragment implements OnClickListener {
	private View view;
	// 赞，评论，聊天
	private RelativeLayout rlZan, rlComment, rlLt;
	// 赞人消息数，评论消息数，聊天消息数
	private TextView tvZanNum, tvCommentNum, tvLtNum;
	private int countid;

	// 系统消息列表
	// private MyListView lvSysMess;
	// 清空
	// private Button btnClear;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			view = inflater
					.inflate(R.layout.fragment_message, container, false);
		}
		// 缓存的view需要判断是否已经被加过parent，
		// 如果有parent需要从parent删除，要不然会发生这个view已经有parent的错误。
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		countid = ApplicationController.getInstance().getUser()
				.getInt("countid", 0);

		rlZan = (RelativeLayout) view.findViewById(R.id.rl_message_zan);
		rlZan.setOnClickListener(this);
		rlComment = (RelativeLayout) view.findViewById(R.id.rl_message_comment);
		rlComment.setOnClickListener(this);
		rlLt = (RelativeLayout) view.findViewById(R.id.rl_message_lt);
		rlLt.setOnClickListener(this);
		// btnClear = (Button) view.findViewById(R.id.btn_message_clear);
		// tvZanNum = (TextView) view.findViewById(R.id.tv_message_zan_num);
		// tvCommentNum = (TextView) view
		// .findViewById(R.id.tv_message_comment_num);
		// tvLtNum = (TextView) view.findViewById(R.id.tv_message_lt_num);
		// lvSysMess = (MyListView) view.findViewById(R.id.lv_message_sys);

		// 测试
		// setNum(tvCommentNum, "12");
		// setNum(tvZanNum, "100");
		// setNum(tvLtNum, "13");
	}

	private void setNum(TextView view, String num) {
		BadgeView bView = new BadgeView(getActivity(), view);
		bView.setBackgroundColor(getResources().getColor(R.color.SysColor));
		bView.setText(num);
		bView.setBackgroundResource(R.drawable.bg_message_badg);
		bView.setTextColor(getResources().getColor(R.color.white));
		bView.setBadgePosition(BadgeView.POSITION_CENTER);
		bView.show();
	}

	@Override
	public void onClick(View arg0) {
		Intent intent = null;
		switch (arg0.getId()) {
		case R.id.rl_message_zan:
			if (countid == 0) {
				intent = new Intent(getActivity(), UserLoginActivity.class);
			} else {
				intent = new Intent(getActivity(),
						MessageZanDetailActivity.class);
				intent.putExtra("title", "赞我的");
				intent.putExtra("classid", Constants.MESSAGE_TYPE.ZAN);
			}
			startActivity(intent);
			break;
		case R.id.rl_message_comment:
			if (countid == 0) {
				intent = new Intent(getActivity(), UserLoginActivity.class);
			} else {
				intent = new Intent(getActivity(),
						MessageZanDetailActivity.class);
				intent.putExtra("title", "评论我的");
				intent.putExtra("classid", Constants.MESSAGE_TYPE.COMMENT);
			}
			startActivity(intent);
			break;

		case R.id.rl_message_lt:
			if (countid == 0) {
				intent = new Intent(getActivity(), UserLoginActivity.class);
				startActivity(intent);
			} else {
				startActivity(new Intent(getActivity(),
						MessageConversionActivity.class));
			}
			break;

		default:
			break;
		}

	}

}
