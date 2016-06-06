package com.hp.widget;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.hp.R;
import com.hp.application.ApplicationController;
import com.hp.bean.UBanner;
import com.hp.dao.BannerDaoImpl;
import com.hp.utils.hpCantant;
import com.hp.utils.volley.VolleyUtils;
import com.hp.utils.volley.VolleyUtilsImpl;

/**
 * 图片轮播
 * 
 * @author qwh
 * 
 */
public class BannerSlideShowView extends FrameLayout {
	private PopupWindow pop = null;

	private ImageLoader imageLoader;

	// 图片数量
	private final static int IMAGE_COUNT = 5;
	// 是否自动播放
	private final static boolean isAutoPlay = true;

	// UBanner集
	private List<UBanner> uBList = new ArrayList<UBanner>();
	// private String[] imageUrls;
	private List<String> imageUrls = new ArrayList<String>();
	private Handler handlerToData;

	// NetworkImageView集
	private List<NetworkImageView> imageViewsList;
	// 右下角小圆点集
	private List<View> dotViewsList;

	private ViewPager viewPager;
	// 当前位置
	private int currentItem = 0;
	// 定时周期指令集合任务
	private ScheduledExecutorService scheduledExecutorService;

	private Context context;

	// Handler
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			viewPager.setCurrentItem(currentItem);
		}

	};

	public BannerSlideShowView(Context context) {
		this(context, null);
	}

	public BannerSlideShowView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BannerSlideShowView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		initData();
		if (isAutoPlay) {
			startPlay();
		}

	}

	/**
	 * 开始轮播
	 */
	private void startPlay() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 4,
				TimeUnit.SECONDS);
	}

	/**
	 * 停止轮播
	 */
	private void stopPlay() {
		scheduledExecutorService.shutdownNow();
	}

	/**
	 * 获取数据
	 */
	private void initData() {
		imageViewsList = new ArrayList<NetworkImageView>();
		dotViewsList = new ArrayList<View>();

		handlerToData = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what == hpCantant.LABLE_BANNER) {
					Bundle bundle = msg.getData();
					BannerDaoImpl uBanner = new BannerDaoImpl();
					Log.i("--------------", bundle.getString(hpCantant.GETDATA));
					uBList = uBanner.loadBanner(bundle
							.getString(hpCantant.GETDATA));

					for (UBanner data : uBList) {
						imageUrls.add(data.getImagelink());
					}

					initUI(context);
				} else {
					Log.i(hpCantant.TAG, "获取轮播图失败");
				}
			};

		};
		VolleyUtils mUtils = new VolleyUtilsImpl();
		mUtils.getRequestString(hpCantant.URL + hpCantant.UBANNER_URL,
				handlerToData, hpCantant.LABLE_BANNER);
	}

	/**
	 * 初始化ui
	 */
	private void initUI(Context context) {
		if (imageUrls == null || imageUrls.size() == 0)
			return;

		if (imageLoader == null) {
			imageLoader = ApplicationController.getInstance().getImageLoader();
		}
		LayoutInflater.from(context).inflate(R.layout.banner_slideshow, this,
				true);

		LinearLayout dotLayout = (LinearLayout) findViewById(R.id.dotLayout);
		dotLayout.removeAllViews();

		//
		for (int i = 0; i < imageUrls.size(); i++) {
			NetworkImageView view = new NetworkImageView(context);
			view.setTag(imageUrls.get(i));
			view.setScaleType(ScaleType.FIT_XY);
			imageViewsList.add(view);

			ImageView dotView = new ImageView(context);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.leftMargin = 4;
			params.rightMargin = 4;
			dotLayout.addView(dotView, params);
			dotViewsList.add(dotView);
		}

		viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.setFocusable(true);

		viewPager.setAdapter(new MyPagerAdapter());
		viewPager.setOnPageChangeListener(new MyPageChangeListener());

	}

	/**
	 * PagerAdapter
	 * 
	 */
	private class MyPagerAdapter extends PagerAdapter {

		@Override
		public void destroyItem(View container, int position, Object object) {
			// ((ViewPag.er)container).removeView((View)object);
			((ViewPager) container).removeView(imageViewsList.get(position));
		}

		@Override
		public Object instantiateItem(View container, final int position) {
			NetworkImageView imageView = imageViewsList.get(position);
			imageView.setDefaultImageResId(R.drawable.icon_addpic);
			imageView.setErrorImageResId(R.drawable.ic_exception_no_network);
			imageView.setImageUrl(imageView.getTag() + "", imageLoader);
			((ViewPager) container).addView(imageViewsList.get(position));

			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {

					Toast.makeText(getContext(),
							"详情链接" + uBList.get(position).getDetaillink(),
							Toast.LENGTH_SHORT).show();
					Log.i("aaaaaaaa", uBList.get(position).getDetaillink());
					stopPlay();
//					if (pop == null)
//						pop = new PopupWindow(BannerSlideShowView.this);
//					View mView = inflate(context,
//							R.layout.pop_home_banner_detail, null);
//					RelativeLayout parent = (RelativeLayout) mView
//							.findViewById(R.id.parent);
//					NetworkImageView ivDetail = (NetworkImageView) mView
//							.findViewById(R.id.iv_banner_detail);
//					ivDetail.setImageUrl(uBList.get(position).getDetaillink(),
//							imageLoader);
//					pop.setWidth(LayoutParams.MATCH_PARENT);
//					pop.setHeight(LayoutParams.MATCH_PARENT);
//					pop.setBackgroundDrawable(new BitmapDrawable());
//					pop.setFocusable(true);
//					pop.setOutsideTouchable(true);
//					pop.setContentView(mView);
//					pop.showAtLocation(getRootView(), Gravity.CENTER, 0, 0);
//					parent.setOnClickListener(new View.OnClickListener() {
//						@Override
//						public void onClick(View arg0) {
//							startPlay();
//							pop.dismiss();
//						}
//					});

				}
			});

			return imageViewsList.get(position);
		}

		@Override
		public int getCount() {
			return imageViewsList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

		@Override
		public void finishUpdate(View arg0) {

		}

	}

	/**
	 * ViewPager锟侥硷拷锟斤拷锟斤拷 锟斤拷ViewPager锟斤拷页锟斤拷锟阶刺拷锟斤拷锟斤拷谋锟绞憋拷锟斤拷锟�
	 * 
	 */
	private class MyPageChangeListener implements OnPageChangeListener {

		boolean isAutoPlay = true;

		@Override
		public void onPageScrollStateChanged(int arg0) {
			switch (arg0) {
			case 1:// 不自动播放
				isAutoPlay = false;
				break;
			case 2:// 自动播放
				isAutoPlay = true;
				break;
			case 0:
				if (viewPager.getCurrentItem() == viewPager.getAdapter()
						.getCount() - 1 && !isAutoPlay) {
					viewPager.setCurrentItem(0);
				} else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
					viewPager
							.setCurrentItem(viewPager.getAdapter().getCount() - 1);
				}
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int pos) {

			currentItem = pos;
			for (int i = 0; i < dotViewsList.size(); i++) {
				if (i == pos) {
					((View) dotViewsList.get(pos))
							.setBackgroundResource(R.drawable.banner_dot_focus);
				} else {
					((View) dotViewsList.get(i))
							.setBackgroundResource(R.drawable.banner_dot);
				}
			}
		}

	}

	/**
	 * 
	 */
	private class SlideShowTask implements Runnable {

		@Override
		public void run() {
			synchronized (viewPager) {
				currentItem = (currentItem + 1) % imageViewsList.size();
				handler.obtainMessage().sendToTarget();
			}
		}

	}

}