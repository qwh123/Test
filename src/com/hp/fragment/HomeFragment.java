package com.hp.fragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.hp.R;
import com.hp.activity.CityListActivity;
import com.hp.activity.GoodDetailActivity;
import com.hp.activity.LableDetailActivity;
import com.hp.activity.SearchActivity;
import com.hp.activity.WebActivity;
import com.hp.application.ApplicationController;
import com.hp.bean.UIndex;
import com.hp.bean.UIndex.Data.Banner;
import com.hp.bean.UIndex.Data.Goods;
import com.hp.bean.UIndex.Data.Label;
import com.hp.bean.UIndex.Data.Publish;
import com.hp.commonAdapter.CommonAdapter;
import com.hp.commonAdapter.ViewHolder;
import com.hp.dao.UIndexData;
import com.hp.fragment.CycleViewPagerFragment.ImageCycleViewListener;
import com.hp.utils.CommonUtils;
import com.hp.utils.ViewFactory;
import com.hp.utils.hpCantant;
import com.hp.widget.MyGridView;
import com.hp.widget.MyListView;
import com.hp.widget.PullToRefreshView;
import com.hp.widget.PullToRefreshView.OnFooterRefreshListener;
import com.hp.widget.PullToRefreshView.OnHeaderRefreshListener;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class HomeFragment extends Fragment implements OnHeaderRefreshListener,
		OnFooterRefreshListener {
	private boolean isRefresh = false;
	private View rootView;
	private Button btnCity;
	private String lngCityName;
	private LocationClient locationClient;
	private UIndexData mUindexData;
	private UIndex.Data mUIndex;
	CommonAdapter<UIndex.Data.Label> mLableAdapter;
	CommonAdapter<UIndex.Data.Goods> mGoodsAdapter;
	SharedPreferences mCityPreferences;
	SharedPreferences.Editor editor;

	private Handler mHandler = new Handler() {
		private JSONObject js;

		public void handleMessage(android.os.Message msg) {
			if (msg.what == hpCantant.LABLE_UINDEX) {
				Bundle bundle = msg.getData();
				mUindexData = new UIndexData();
				try {
					js = new JSONObject(bundle.getString(hpCantant.GETDATA));
					if (js.getString("code").equals(hpCantant.SUCCESS_CODE)) {
						mUIndex = mUindexData.loadUIndex(bundle
								.getString(hpCantant.GETDATA));
						mActivity.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								if (isRefresh) {
									mRefreshView.onHeaderRefreshComplete();
									isRefresh = false;
									Toast.makeText(getActivity(), "刷新成功", 0)
											.show();
								}
								initTop(mUIndex.getPublish());
								if (mUIndex.getLabel() != null
										&& mUIndex.getLabel().size() > 0)
									initLable(mUIndex.getLabel());
								if (mUIndex.getGoods() != null
										&& mUIndex.getGoods().size() > 0)
									initGoods(mUIndex.getGoods());
								if (mUIndex.getBanner() != null
										&& mUIndex.getBanner().size() > 0)
									initBanner(mUIndex.getBanner());
							}

						});
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	};

	private TextView tvTop;
	private MyGridView gvLable;
	private MyListView lvGoods;
	private Activity mActivity;
	private PullToRefreshView mRefreshView;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.fragment_home, container,
					false);
		}
		// 缓存的rootView需要判断是否已经被加过parent，
		// 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mCityPreferences = mActivity.getSharedPreferences("CurrentCity",
				mActivity.MODE_PRIVATE);
		editor = mCityPreferences.edit();

		mRefreshView = (PullToRefreshView) view.findViewById(R.id.sc_homeparty);
		mRefreshView.setOnHeaderRefreshListener(this);
		mRefreshView.setOnFooterRefreshListener(this);
		mRefreshView.setLastUpdated(new Date().toLocaleString());

		initView();
		initGps();
	}

	private void getUIndexData() {
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("lat", ApplicationController.getInstance().getLat());
		map.put("lng", ApplicationController.getInstance().getLng());
		CommonUtils.getData(mHandler, map, hpCantant.UINDEX_URL,
				hpCantant.LABLE_UINDEX);
	}

	/**
	 * 初始化UI组件
	 */
	private void initView() {
		btnCity = (Button) rootView.findViewById(R.id.btn_city);
		btnCity.setText(mCityPreferences.getString("city", "定位中..."));
		gvLable = (MyGridView) rootView.findViewById(R.id.gv_label);
		tvTop = (TextView) rootView.findViewById(R.id.tv_top);
		lvGoods = (MyListView) rootView.findViewById(R.id.lv_goods);
		// 进入城市选择列表
		btnCity.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(mActivity, CityListActivity.class);
				intent.putExtra("lngCity", lngCityName);
				startActivityForResult(intent, 99);
			}
		});
		// 进入搜索列表
		((ImageButton) rootView.findViewById(R.id.btn_search))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						startActivity(new Intent(mActivity,
								SearchActivity.class));
					}
				});
	}

	// private List<View> mDots;
	// private ViewPager adViewPager;
	// private int currentItem = 0; // 当前图片的索引号
	// private ScheduledExecutorService scheduledExecutorService;
	// private List<Banner> mBannerList;
	// private ImageLoader imageLoader;
	// // NetworkImageView集
	// private List<NetworkImageView> imageViewsList;
	// // 是否自动播放
	// private final static boolean isAutoPlay = true;
	// private Handler handler = new Handler() {
	// public void handleMessage(android.os.Message msg) {
	// adViewPager.setCurrentItem(currentItem);
	// };
	// };

	private List<ImageView> views = new ArrayList<ImageView>();
	private CycleViewPagerFragment cycleViewPager;

	/**
	 * 初始化轮播图
	 * 
	 * @param banner
	 */
	private void initBanner(List<Banner> banner) {
		if (views.size() == 0) {
			configImageLoader();
			cycleViewPager = (CycleViewPagerFragment) getActivity()
					.getFragmentManager().findFragmentById(
							R.id.fragment_cycle_viewpager_content);
			// 将最后一个ImageView添加进来
			views.add(ViewFactory.getImageView(getActivity(),
					banner.get(banner.size() - 1).getImagelink()));
			for (int i = 0; i < banner.size(); i++) {
				views.add(ViewFactory.getImageView(getActivity(), banner.get(i)
						.getImagelink()));
			}
			// 将第一个ImageView添加进来
			views.add(ViewFactory.getImageView(getActivity(), banner.get(0)
					.getImagelink()));
		}
		// 设置循环，在调用setData方法前调用
		cycleViewPager.setCycle(true);

		// 在加载数据前设置是否循环
		cycleViewPager.setData(views, banner, mAdCycleViewListener);
		// 设置轮播
		cycleViewPager.setWheel(true);

		// 设置轮播时间，默认5000ms
		cycleViewPager.setTime(3500);
		// 设置圆点指示图标组居中显示，默认靠右
		cycleViewPager.setIndicatorCenter();
		// if (imageLoader == null) {
		// imageLoader = ApplicationController.getInstance().getImageLoader();
		// }
		// mBannerList = banner;
		// imageViewsList = new ArrayList<NetworkImageView>();
		// mDots = new ArrayList<View>();
		// addDynamicView();
		// adViewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
		// adViewPager.setFocusable(true);
		// adViewPager.setAdapter(new MyAdapter());// 设置填充ViewPager页面的适配器
		// // 设置一个监听器，当ViewPager中的页面改变时调用
		// adViewPager.setOnPageChangeListener(new MyPageChangeListener());
		// if (isAutoPlay && imageViewsList.size() > 1) {
		// startPlay();
		// }
	}

	private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {

		@Override
		public void onImageClick(UIndex.Data.Banner info, int position,
				View imageView) {
			if (cycleViewPager.isCycle()) {
				Intent intent = new Intent(getActivity(), WebActivity.class);
				intent.putExtra("url", hpCantant.URL
						+ hpCantant.UBannerDetail_URL + info.getId());
				startActivity(intent);
			}

		}

	};

	/**
	 * 配置ImageLoder
	 */
	private void configImageLoader() {
		// 初始化ImageLoader
		@SuppressWarnings("deprecation")
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.logo_home) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.ic_exception_no_network) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.ic_exception_no_network) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
				.build(); // 创建配置过得DisplayImageOption对象

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getActivity()).defaultDisplayImageOptions(options)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);
	}

	//
	// /**
	// * 动态加载小圆点
	// *
	// * @return
	// */
	// private void addDynamicView() {
	// LinearLayout dotLayout = (LinearLayout) rootView
	// .findViewById(R.id.dotLayout);
	// dotLayout.removeAllViews();
	// for (int i = 0; i < mBannerList.size(); i++) {
	// NetworkImageView view = new NetworkImageView(mActivity);
	// view.setTag(mBannerList.get(i).getImagelink());
	// view.setScaleType(ScaleType.FIT_XY);
	// imageViewsList.add(view);
	// ImageView dotView = new ImageView(mActivity);
	// LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
	// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	// params.leftMargin = 4;
	// params.rightMargin = 4;
	// dotLayout.addView(dotView, params);
	// mDots.add(dotView);
	// }
	// }
	//
	// /**
	// * 开始播放
	// */
	// private void startPlay() {
	// scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
	// // 当Activity显示出来后，每4秒切换一次图片显示
	// scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 4,
	// TimeUnit.SECONDS);
	// }
	//
	// /**
	// * 停止轮播
	// */
	// private void stopPlay() {
	// if (imageViewsList != null && imageViewsList.size() > 1)
	// scheduledExecutorService.shutdownNow();
	// }
	//
	// private class ScrollTask implements Runnable {
	//
	// @Override
	// public void run() {
	// synchronized (adViewPager) {
	// currentItem = (currentItem + 1) % imageViewsList.size();
	// handler.obtainMessage().sendToTarget();
	// }
	// }
	// }
	//
	// private class MyPageChangeListener implements OnPageChangeListener {
	// boolean isAutoPlay = true;
	//
	// @Override
	// public void onPageScrollStateChanged(int arg0) {
	// switch (arg0) {
	// case 1:// 不自动播放
	// isAutoPlay = false;
	// break;
	// case 2:// 自动播放
	// isAutoPlay = true;
	// break;
	// case 0:
	// if (adViewPager.getCurrentItem() == adViewPager.getAdapter()
	// .getCount() - 1 && !isAutoPlay) {
	// adViewPager.setCurrentItem(0);
	// } else if (adViewPager.getCurrentItem() == 0 && !isAutoPlay) {
	// adViewPager.setCurrentItem(adViewPager.getAdapter()
	// .getCount() - 1);
	// }
	// break;
	// }
	// }
	//
	// @Override
	// public void onPageScrolled(int arg0, float arg1, int arg2) {
	// }
	//
	// @Override
	// public void onPageSelected(int position) {
	// currentItem = position;
	// for (int i = 0; i < mDots.size(); i++) {
	// if (i == position) {
	// ((View) mDots.get(position))
	// .setBackgroundResource(R.drawable.banner_dot_focus);
	// } else {
	// ((View) mDots.get(i))
	// .setBackgroundResource(R.drawable.banner_dot);
	// }
	// }
	// }
	// }
	//
	// /**
	// * 轮播图片的适配器
	// *
	// * @author qwh
	// *
	// */
	// private class MyAdapter extends PagerAdapter {
	// // @Override
	// // public void destroyItem(ViewGroup container, int position, Object
	// // object) {
	// // super.destroyItem(container, position, object);
	// // ((ViewPager) container).removeView(imageViewsList.get(position));
	// // }
	//
	// @Override
	// public int getCount() {
	// return mBannerList.size();
	// }
	//
	// @Override
	// public Object instantiateItem(ViewGroup container, final int position) {
	// NetworkImageView imageView = imageViewsList.get(position);
	// imageView.setDefaultImageResId(R.drawable.fail_image);
	// imageView.setErrorImageResId(R.drawable.fail_image);
	// Log.i("aaa", (String) imageView.getTag());
	// imageView.setImageUrl(imageView.getTag() + "", imageLoader);
	// ((ViewPager) container).addView(imageViewsList.get(position));
	//
	// imageView.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View arg0) {
	// stopPlay();
	//
	// Intent intent = new Intent(getActivity(), WebActivity.class);
	// intent.putExtra("url", mBannerList.get(position)
	// .getDetaillink());
	// startActivity(intent);
	// }
	// });
	//
	// return imageViewsList.get(position);
	// }
	//
	// @Override
	// public void destroyItem(View container, int arg1, Object arg2) {
	// ((ViewPager) container).removeView((View) arg2);
	// }
	//
	// @Override
	// public boolean isViewFromObject(View arg0, Object arg1) {
	// return arg0 == arg1;
	// }
	//
	// @Override
	// public void restoreState(Parcelable arg0, ClassLoader arg1) {
	//
	// }
	//
	// @Override
	// public Parcelable saveState() {
	// return null;
	// }
	//
	// @Override
	// public void startUpdate(View arg0) {
	//
	// }
	//
	// @Override
	// public void finishUpdate(View arg0) {
	//
	// }
	//
	// }
	//
	// @Override
	// public void onStop() {
	// super.onStop();
	// // 当Activity不可见的时候停止切换
	// if (imageViewsList != null && imageViewsList.size() > 1)
	// scheduledExecutorService.shutdown();
	// }

	/**
	 * 初始化商品列表
	 * 
	 * @param goods
	 */
	private void initGoods(List<Goods> goods) {
		mGoodsAdapter = new CommonAdapter<UIndex.Data.Goods>(mActivity, goods,
				R.layout.item_home_category_show) {
			@Override
			public void convert(ViewHolder helper, Goods item) {
				helper.setText(R.id.tv_home_category_title, item.getTitle());
				helper.setText(R.id.tv_home_category_acount,
						"数量:" + item.getAmount());
				helper.setText(R.id.tv_home_category_adress, item.getDistance()
						+ "m");
				if (item.getAllprice() != null) {
					if (item.getAllprice().equals("0.0")) {
						helper.setText(R.id.tv_home_category_price, "免费");
					} else
						helper.setText(R.id.tv_home_category_price,
								"¥" + item.getAllprice());
				}
				helper.setImageByUrl(R.id.iv_home_category_icon, item.getIcon());
				if (item.getClassid().equals("1")) {
					helper.setImageResource(R.id.iv_home_category_label,
							R.drawable.bg_label_cd);
					helper.setVisibility(R.id.tv_home_category_time, View.GONE);
					helper.setVisibility(R.id.ratingBar1, View.VISIBLE);
					try {
						if (item.getScore() != null
								&& Float.parseFloat(item.getScore()) >= 0.0f
								&& Float.parseFloat(item.getScore()) <= 5.0f) {
							helper.setRating(R.id.ratingBar1,
									Float.parseFloat(item.getScore()));
						} else {
							helper.setRating(R.id.ratingBar1, 5.0f);
						}

					} catch (Exception e) {
						helper.setRating(R.id.ratingBar1, 0.0f);
					}

				} else if (item.getClassid().equals("2")) {
					helper.setVisibility(R.id.ratingBar1, View.GONE);
					helper.setVisibility(R.id.tv_home_category_time,
							View.VISIBLE);
					helper.setText(R.id.tv_home_category_time, item.getTime());
					helper.setImageResource(R.id.iv_home_category_label,
							R.drawable.bg_label_hd);
				}
			}
		};
		lvGoods.setAdapter(mGoodsAdapter);
		lvGoods.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(mActivity, GoodDetailActivity.class);
				// Intent intent = new Intent(mActivity,
				// HomeGoodDetailActivity.class);
				// intent.putExtra("id", mList.get(postion).getId());
				// intent.putExtra("countid",
				// mList.get(postion).getCountid());
				Bundle bundle = new Bundle();
				bundle.putString("imageurl", mUIndex.getGoods().get(arg2)
						.getIcon());
				bundle.putString("id", mUIndex.getGoods().get(arg2).getId()
						+ "");
				String type = mUIndex.getGoods().get(arg2).getClassid();
				bundle.putString("classid", type + "");
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});

	}

	/**
	 * 初始化标签列表
	 * 
	 * @param label
	 */
	private void initLable(final List<Label> label) {
		mLableAdapter = new CommonAdapter<UIndex.Data.Label>(mActivity, label,
				R.layout.item_home_lable) {
			@Override
			public void convert(ViewHolder helper, Label item) {
				helper.setImageByUrl(R.id.iv_home_lable, item.getIcon());
				helper.setText(R.id.tv_home_lable, item.getName());
			}
		};
		// 添加并且显示
		gvLable.setAdapter(mLableAdapter);
		// 添加消息处理
		gvLable.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(getActivity(),
						LableDetailActivity.class);
				intent.putExtra("lableName", label.get(arg2).getName());
				intent.putExtra("lableid", label.get(arg2).getId());
				startActivity(intent);
			}
		});
	}

	/**
	 * 初始化今日头条
	 * 
	 * @param top
	 */
	private void initTop(final Publish top) {
		if (top != null)
			tvTop.setText(top.getTitle());
		else
			tvTop.setText("暂无推荐");
		tvTop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(), WebActivity.class);
				intent.putExtra("url", hpCantant.URL
						+ hpCantant.UPublishDetail_URL + top.getId());
				startActivity(intent);
			}
		});
	};

	/**
	 * GPS定位服务
	 */
	private void initGps() {
		try {
			MyLocationListenner myListener = new MyLocationListenner();
			locationClient = new LocationClient(mActivity);
			locationClient.registerLocationListener(myListener);
			LocationClientOption option = new LocationClientOption();
			option.setOpenGps(true);
			option.setAddrType("all");
			option.setCoorType("bd09ll");
			option.setScanSpan(5000);
			locationClient.setLocOption(option);
			locationClient.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

	}

	// 定位当前城市
	private class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {

			if (location == null)
				return;
			ApplicationController.getInstance().setLat(
					location.getLatitude() + "");
			ApplicationController.getInstance().setLng(
					location.getLongitude() + "");
			Log.i("hf", ApplicationController.getInstance().getLat() + "|||"
					+ ApplicationController.getInstance().getLng());
			StringBuffer sb = new StringBuffer(256);
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				// sb.append(location.getAddrStr());
				sb.append(location.getCity());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append(location.getCity());
			}
			if (sb.toString() != null && sb.toString().length() > 0) {
				lngCityName = sb.toString();
				if (lngCityName != null)
					btnCity.setText(lngCityName);
			}
			getUIndexData();
			editor.putString("city", lngCityName);
			editor.commit();
			locationClient.stop();
		}

		public void onReceivePoi(BDLocation poiLocation) {

		}
	}

	// 选择城市获得返回结果
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		try {
			switch (resultCode) {
			case 99:
				btnCity.setText(data.getStringExtra("lngCityName"));
				editor.putString("city", data.getStringExtra("lngCityName"));
				editor.commit();
				getUIndexData();
				break;

			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	//
	// @Override
	// public void onFooterRefresh(PullToRefreshView view) {
	// mRefreshView.postDelayed(new Runnable() {
	// @Override
	// public void run() {
	// mRefreshView.onHeaderRefreshComplete("更新于:"
	// + Calendar.getInstance().getTime().toLocaleString());
	// mRefreshView.onFooterRefreshComplete();
	//
	// Toast.makeText(getActivity(), "暂无更新", 0).show();
	// }
	//
	// }, 3000);
	//
	// }

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		isRefresh = true;
		getUIndexData();
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mRefreshView.onFooterRefreshComplete();
	}

}
