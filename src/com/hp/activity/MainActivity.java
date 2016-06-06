package com.hp.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.hp.R;
import com.hp.fragment.MessageFragment;
import com.hp.fragment.HomeFragment;
import com.hp.fragment.SquareFragment;
import com.hp.fragment.UserFragment;

public class MainActivity extends FragmentActivity implements
		OnCheckedChangeListener {
	
	private long exitTime = 0;
	// 定义4个Fragment的对象
	private HomeFragment mHomeFragment;
	private SquareFragment mSquareFragment;
	private MessageFragment mGamesFragment;
	private UserFragment mUserFragment;

	private ArrayList<Fragment> fragments;
	private RadioGroup group;
	private RadioButton imageView;

	// 定义FragmentManager对象
	FragmentManager fManager;
	FragmentTransaction transaction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.aty_main);
		initView();

		group = (RadioGroup) findViewById(R.id.main_tab_bar);
		group.setOnCheckedChangeListener(this);
		fragments = new ArrayList<Fragment>();
		fragments.add(mHomeFragment);
		fragments.add(mSquareFragment);
		fragments.add(mGamesFragment);
		fragments.add(mUserFragment);
		if (getIntent().getIntExtra("FragmentType", 0) != -1) {
			fManager = getSupportFragmentManager();
			FragmentTransaction transaction = fManager.beginTransaction();
			transaction.replace(R.id.content, fragments.get(0));
			transaction.commit();
		}
	}

	private MoreWindow mMoreWindow;

	private void initView() {
		mHomeFragment = new HomeFragment();
		mSquareFragment = new SquareFragment();
		mGamesFragment = new MessageFragment();
		mUserFragment = new UserFragment();
		imageView = (RadioButton) findViewById(R.id.main_add);
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mMoreWindow = new MoreWindow(MainActivity.this);
				mMoreWindow.init();
				mMoreWindow.showMoreWindow(arg0);
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	public void onCheckedChanged(RadioGroup view, int checkId) {
		int childCount = group.getChildCount();
		int checkedIndex = 0;
		RadioButton btnButton = null;
		for (int i = 0; i < childCount; i++) {
			btnButton = (RadioButton) group.getChildAt(i);
			if (btnButton.isChecked()) {
				checkedIndex = i;
				break;
			}
		}

		fManager = getSupportFragmentManager();
		transaction = fManager.beginTransaction();
		Fragment fragment = null;
		switch (checkedIndex) {
		case 0:
			fragment = fragments.get(0);
			transaction.replace(R.id.content, fragment);
			transaction.commit();
			break;
		case 1:
			fragment = fragments.get(1);
			transaction.replace(R.id.content, fragment);
			transaction.commit();
			break;
		case 2:
			break;
		case 3:
			fragment = fragments.get(2);
			transaction.replace(R.id.content, fragment);
			transaction.commit();
			break;
		case 4:
			fragment = fragments.get(3);
			transaction.replace(R.id.content, fragment);
			transaction.commit();
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			moveTaskToBack(true);
			// exit();
			// return true;
		}
		return super.onKeyDown(keyCode, event);

	}

	@SuppressWarnings("deprecation")
	private void exit() {
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			Toast.makeText(getApplicationContext(), "再按一次退出程序",
					Toast.LENGTH_SHORT).show();
			exitTime = System.currentTimeMillis();
		} else {
			// ActivityManager am=(ActivityManager)
			// getSystemService(Context.ACTIVITY_SERVICE);
			// am.restartPackage(getPackageName());
			// System.exit(0);
			this.finish();
		}
	}

}
