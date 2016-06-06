package com.hp.activity.userOrder;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.hp.R;
import com.hp.activity.userOrder.util.PagerSlidingTabStrip;
import com.hp.widget.TopBar;
import com.hp.widget.TopBar.topBarClickListener;

public class UserOrderFramentActivity extends FragmentActivity {

	/**
	 * 待付款的Fragment
	 */
	private NoPayFragment mNoPayFragment;

	/**
	 * 已订购的Fragment
	 */
	private OrderedFragment mOrderedFragment;

	/**
	 * 已完成的Fragment
	 */
	private SuccessFragment mSuccessFragment;

	/**
	 * PagerSlidingTabStrip的实例
	 */
	private PagerSlidingTabStrip tabs;

	/**
	 * 获取当前屏幕的密度
	 */
	private DisplayMetrics dm;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_user_order);
		initView();
		init();
	}

	private void init() {
		dm = getResources().getDisplayMetrics();
		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		pager.setOffscreenPageLimit(3);
		tabs.setViewPager(pager);
		setTabsValue();
	}

	private void initView() {
		TopBar tBar = (TopBar) findViewById(R.id.topbar_hd);
		tBar.setTitleText("我的订单");
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
	}

	/**
	 * 对PagerSlidingTabStrip的各项属性进行赋值。
	 */
	private void setTabsValue() {
		// 设置Tab是自动填充满屏幕的
		tabs.setShouldExpand(true);
		// 设置Tab的分割线是透明的
		tabs.setDividerColor(Color.TRANSPARENT);
		// 设置Tab底部线的高度
		tabs.setUnderlineHeight((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 1, dm));
		// 设置Tab Indicator的高度
		tabs.setIndicatorHeight((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 2, dm));
		// 设置Tab标题文字的大小
		tabs.setTextSize((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, 16, dm));
		// 设置Tab Indicator的颜色
		tabs.setIndicatorColor(Color.parseColor("#E51C23"));
		// 设置选中Tab文字的颜色 (这是我自定义的一个方法)
		tabs.setSelectedTextColor(Color.parseColor("#E51C23"));
		// 取消点击Tab时的背景色
		tabs.setTabBackground(0);
	}

	public class MyPagerAdapter extends FragmentStatePagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		private final String[] titles = { "待付款", "已订购", "待评价" };

		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position];
		}

		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				if (mNoPayFragment == null) {
					mNoPayFragment = new NoPayFragment();
				}
				return mNoPayFragment;
			case 1:
				if (mOrderedFragment == null) {
					mOrderedFragment = new OrderedFragment();
				}
				return mOrderedFragment;
			case 2:
				if (mSuccessFragment == null) {
					mSuccessFragment = new SuccessFragment();
				}
				return mSuccessFragment;
			default:
				return null;
			}
		}

	}

}
