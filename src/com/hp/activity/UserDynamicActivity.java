package com.hp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hp.R;
import com.hp.application.ApplicationController;
import com.hp.utils.getDynamic;
import com.hp.widget.TopBar;
import com.hp.widget.TopBar.topBarClickListener;

public class UserDynamicActivity extends Activity {

	private ListView mListView;
	private ProgressBar pgb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_user_dynamic);
		pgb = (ProgressBar) findViewById(R.id.pgb_user_dynamic);

		TopBar tBar = (TopBar) findViewById(R.id.topbar_user_dynamic);
		tBar.setTitleText("我的动态");
		tBar.setRightIsVisable(false);
		tBar.setOnTopBarClickListener(new topBarClickListener() {
			@Override
			public void rightClick() {
				Toast.makeText(UserDynamicActivity.this, "click", 1).show();
			}

			@Override
			public void leftClick() {
				finish();
			}
		});
		mListView = (ListView) findViewById(R.id.lv_user_dynamic);

		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("cpuntid", ApplicationController.getInstance().getUser()
				.getInt("countid", 0)
				+ "");
		new getDynamic(this, map, mListView, pgb);
	}

}
