package com.tgj.activity;

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

import com.hp.R;
import com.tgj.fragment.MessageFragment;
import com.tgj.fragment.OrderFragment;
import com.tgj.fragment.TGJUserFragment;

public class TGJMainActivity extends FragmentActivity implements
		OnCheckedChangeListener {
	// 定义4个Fragment的对象
	private OrderFragment mOrderFragment;
	private MessageFragment mMessageFragment;
	private TGJUserFragment mTgjUserFragment;

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
		setContentView(R.layout.tgj_aty_main);
	//	initView();
		group = (RadioGroup) findViewById(R.id.tgj_main_tab_bar);
		group.setOnCheckedChangeListener(this);
		// fragments = new ArrayList<Fragment>();
		// fragments.add(mOrderFragment);
		// fragments.add(mMessageFragment);
		// fragments.add(mTgjUserFragment);
		if (getIntent().getIntExtra("FragmentType", 0) != -1) {
			fManager = getSupportFragmentManager();
			FragmentTransaction transaction = fManager.beginTransaction();
			mTgjUserFragment = new TGJUserFragment();
			transaction.add(R.id.tgj_content, mTgjUserFragment);
			transaction.commit();
		}
	}

	private void initView() {
		mOrderFragment = new OrderFragment();
		mMessageFragment = new MessageFragment();
		mTgjUserFragment = new TGJUserFragment();
	}

	// 隐藏所有Fragment
	private void hidtFragment(FragmentTransaction fragmentTransaction) {
		if (mOrderFragment != null) {
			fragmentTransaction.hide(mOrderFragment);
		}
		if (mMessageFragment != null) {
			fragmentTransaction.hide(mMessageFragment);
		}
		if (mTgjUserFragment != null) {
			fragmentTransaction.hide(mTgjUserFragment);
		}
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
			if (mOrderFragment == null) {
				mOrderFragment = new OrderFragment();
				// fragment = fragments.get(0);
				transaction.add(R.id.tgj_content, mOrderFragment);
			} else {
				transaction.show(mOrderFragment);
			}
			break;
		case 1:
			if (mMessageFragment == null) {
				mMessageFragment = new MessageFragment();
				// fragment = fragments.get(0);
				transaction.add(R.id.tgj_content, mMessageFragment);
			} else {
				transaction.show(mMessageFragment);
			}
			break;
		case 2:
			if (mTgjUserFragment == null) {
				mTgjUserFragment = new TGJUserFragment();
				// fragment = fragments.get(0);
				transaction.add(R.id.tgj_content, mTgjUserFragment);
			} else {
				transaction.show(mTgjUserFragment);
			}
			break;

		default:
			break;
		}
		transaction.commit();
	}

	// 返回桌面，不退出程序
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			moveTaskToBack(true);
		}
		return super.onKeyDown(keyCode, event);

	}
}
