package com.hp.activity;

import com.hp.R;
import com.hp.utils.hpCantant;
import com.hp.widget.TopBar;
import com.hp.widget.TopBar.topBarClickListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class CateGoryActivity extends Activity {
	private TextView title = null;
	private ImageButton back;
	private ListView lvCateGory;
	private String Title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_home_category);
		Title=getIntent().getStringExtra(hpCantant.TITLE);
		initView();
	}

	private void initView() {
		TopBar tBar = (TopBar) findViewById(R.id.topbar_category);
		tBar.setTitleText(Title);
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
		lvCateGory = (ListView) findViewById(R.id.lv_category);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				CateGoryActivity.this.finish();
			}
		});
	}
}
