package com.tgj.fragment;

import java.lang.reflect.Field;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.hp.R;
import com.hp.activity.userOrder.util.PagerSlidingTabStrip;
import com.tgj.activity.BarCodeTestActivity;
import com.tgj.activity.CaptureActivity;

public class OrderFragment extends Fragment {
	private final static int SCANNIN_GREQUEST_CODE = 1;
	/**
	 * 新订单的Fragment
	 */
	private TGJNewOrderFragment mTgjNewOrderFragment;

	/**
	 * 已完成的Fragment
	 */
	private TGJSuccessFragment mTgjSuccessFragment;
	/**
	 * PagerSlidingTabStrip的实例
	 */
	private PagerSlidingTabStrip tabs;
	/**
	 * 获取当前屏幕的密度
	 */
	private DisplayMetrics dm;

	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.tgj_fragment_order, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
		setTabsValue();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		((ImageView) view.findViewById(R.id.ibtn_tgj_erweima))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Intent openCameraIntent = new Intent(getActivity(),
								CaptureActivity.class);
						startActivityForResult(openCameraIntent, 0);
					}
				});
	}
	@SuppressWarnings("static-access")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 处理扫描结果（在界面上显示）
		if (resultCode == getActivity().RESULT_OK) {
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result");
			Toast.makeText(getActivity(), scanResult, Toast.LENGTH_SHORT)
					.show();
		}
	}

	private void init() {
		dm = getResources().getDisplayMetrics();
		ViewPager pager = (ViewPager) view.findViewById(R.id.tgj_pager);
		tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tgj_tabs);
		pager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
		pager.setOffscreenPageLimit(2);
		tabs.setViewPager(pager);
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
		tabs.setTabPaddingLeftRight(12);
		tabs.setTextColor(getResources().getColor(R.color.textcolor1));
		// 设置Tab Indicator的颜色
		tabs.setIndicatorColor(getResources().getColor(R.color.SysColor));
		// 设置选中Tab文字的颜色 (这是我自定义的一个方法)
		tabs.setSelectedTextColor(getResources().getColor(R.color.SysColor));
		// 取消点击Tab时的背景色
		tabs.setTabBackground(0);
	}

	public class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		private final String[] titles = { "新订单", "已完成" };

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
				if (mTgjNewOrderFragment == null) {
					mTgjNewOrderFragment = new TGJNewOrderFragment();
				}
				return mTgjNewOrderFragment;
			case 1:
				if (mTgjSuccessFragment == null) {
					mTgjSuccessFragment = new TGJSuccessFragment();
				}
				return mTgjSuccessFragment;
			default:
				return null;
			}
		}

	}

	@Override
	public void onDetach() {
		super.onDetach();
		try {
			Field childFragmentManager = Fragment.class
					.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);

		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}

	}
}
