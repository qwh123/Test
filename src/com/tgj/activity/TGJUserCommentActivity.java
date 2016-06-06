package com.tgj.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.hp.R;
import com.hp.activity.MainActivity;
import com.hp.activity.UserLoginActivity;
import com.hp.widget.TopBar;
import com.hp.widget.TopBar.topBarClickListener;

public class TGJUserCommentActivity extends Activity {
	private ListView mListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tgj_aty_user_comment_manage);
		TopBar tBar = (TopBar) findViewById(R.id.topbar_tgj_comment);
		tBar.setTitleText("评论回复");
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
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Toast.makeText(TGJUserCommentActivity.this,
						"��ת������ҳ��" + position, 1).show();
			}
		});

	}

	private void initView() {
		mListView = (ListView) findViewById(R.id.lv_comment);
	}
}