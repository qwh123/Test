package com.hp.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.hp.R;
import com.hp.fragment.DynamicFragment;
import com.hp.fragment.HomeFragment;
import com.hp.fragment.MessageFragment;
import com.hp.fragment.UserFragment;
import com.hp.utils.UpdateAppManager;

public class MainActivity_01 extends FragmentActivity implements
		OnCheckedChangeListener {

	private long exitTime = 0;
	// 定义4个Fragment的对象
	private HomeFragment mHomeFragment;
	private DynamicFragment mDynamicFragment;
	private MessageFragment mGamesFragment;
	private UserFragment mUserFragment;

	private ArrayList<Fragment> fragments;
	private RadioGroup group;

	// 定义FragmentManager对象
	FragmentManager fManager;
	FragmentTransaction transaction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		UpdateAppManager mAppManager=new UpdateAppManager(this);
		mAppManager.checkUpdateInfo();
		group = (RadioGroup) findViewById(R.id.main_tab_bar);
		group.setOnCheckedChangeListener(this);
		// fragments = new ArrayList<Fragment>();
		// if (fragments.size() == 0) {
		// fragments.add(mHomeFragment);
		// fragments.add(mDynamicFragment);
		// fragments.add(mGamesFragment);
		// fragments.add(mUserFragment);
		// }
		if (getIntent().getIntExtra("FragmentType", 0) != -1) {
			fManager = getSupportFragmentManager();
			FragmentTransaction transaction = fManager.beginTransaction();
			mHomeFragment = new HomeFragment();
			transaction.add(R.id.content, mHomeFragment);
			transaction.commit();
		}
	}

	// 隐藏所有Fragment
	private void hidtFragment(FragmentTransaction fragmentTransaction) {
		if (mHomeFragment != null) {
			fragmentTransaction.hide(mHomeFragment);
		}
		if (mDynamicFragment != null) {
			fragmentTransaction.hide(mDynamicFragment);
		}
		if (mGamesFragment != null) {
			fragmentTransaction.hide(mGamesFragment);
		}
		if (mUserFragment != null) {
			fragmentTransaction.hide(mUserFragment);
		}
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
		hidtFragment(transaction);

		switch (checkedIndex) {
		case 0:
			if (mHomeFragment == null) {
				mHomeFragment = new HomeFragment();
				// fragment = fragments.get(0);
				transaction.add(R.id.content, mHomeFragment);
			} else {
				transaction.show(mHomeFragment);
			}
			break;
		case 1:
			if (mDynamicFragment == null) {
				mDynamicFragment = new DynamicFragment();
				// fragment = fragments.get(1);
				transaction.add(R.id.content, mDynamicFragment);
			} else {
				transaction.show(mDynamicFragment);
			}
			break;
		case 2:
			if (mGamesFragment == null) {
				mGamesFragment = new MessageFragment();
				// fragment = fragments.get(2);
				transaction.add(R.id.content, mGamesFragment);
			} else {
				transaction.show(mGamesFragment);
			}
			break;
		case 3:
			if (mUserFragment == null) {
				mUserFragment = new UserFragment();
				// fragment = fragments.get(3);
				transaction.add(R.id.content, mUserFragment);
			} else {
				transaction.show(mUserFragment);
			}
			break;
		default:
			break;
		}
		transaction.commit();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
		//	moveTaskToBack(true);
			 exit();
			 return true;
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
