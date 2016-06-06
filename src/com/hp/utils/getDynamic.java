package com.hp.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.hp.R;
import com.hp.activity.DTUserInfoActivity;
import com.hp.activity.GoodDetailActivity;
import com.hp.activity.SquareDTDetailActivity;
import com.hp.activity.UserLoginActivity;
import com.hp.application.ApplicationController;
import com.hp.bean.UDynamicList;
import com.hp.bean.UDynamicList.Data;
import com.hp.commonAdapter.CommonAdapter;
import com.hp.dao.UDynamicListDao;
import com.hp.fragment.ImagePagerActivity;
import com.hp.utils.volley.ShowImage;
import com.hp.widget.CircleImageView;
import com.hp.widget.MyGridView;

public class getDynamic {
	private boolean isRefresh = false;
	private SwipeRefreshLayout mLayout;
	private Context mContext;
	private ArrayMap<String, String> map;
	private ProgressBar pgb = null;

	public getDynamic() {
		// TODO Auto-generated constructor stub
	}

	public getDynamic(SwipeRefreshLayout mLayout, boolean isRefresh,
			final Context mContext, ArrayMap<String, String> map,
			ListView mListView) {
		this.mLayout = mLayout;
		this.isRefresh = isRefresh;
		this.mContext = mContext;
		this.map = map;
		this.mListView = mListView;
		getData();
	}

	public getDynamic(final Context mContext, ArrayMap<String, String> map,
			ListView mListView, ProgressBar pgb) {
		this.mContext = mContext;
		this.map = map;
		this.mListView = mListView;
		this.pgb = pgb;
		getData();

	}

	UDynamicListDao mDynamicListDao;
	private List<UDynamicList.Data> mList;

	private myDTAdapter mAdapter;
	private ListView mListView;
	private String distance = "__Km";// 显示距离

	private int countid;

	private Handler mHandler = new Handler() {
		private JSONObject js;

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == hpCantant.LABLE_UDYNAMICLIST) {
				if (pgb != null)
					pgb.setVisibility(View.GONE);
				Bundle bundle = msg.getData();
				mDynamicListDao = new UDynamicListDao();
				try {
					js = new JSONObject(bundle.getString(hpCantant.GETDATA));
					if (js.getString("code").equals(hpCantant.SUCCESS_CODE)) {
						if (isRefresh) {
							isRefresh = false;
							mLayout.setRefreshing(false);
							Toast.makeText(mContext, "完成刷新", 0).show();
						}
						mList = mDynamicListDao.loadUDynamic(bundle
								.getString(hpCantant.GETDATA));
						try {
							mAdapter = new myDTAdapter(mContext);
							mAdapter.notifyDataSetChanged();
							mListView.setAdapter(mAdapter);
						} catch (Exception e) {
						}
					} else {
						if (isRefresh) {
							isRefresh = false;
							mLayout.setRefreshing(false);
							Toast.makeText(mContext, "刷新失败", 0).show();
						}
					}

				} catch (JSONException e) {
					Toast.makeText(mContext, "error", 0).show();
				}
			} else if (msg.what == hpCantant.LABLE_UDYNAMICPRAISE) {
				Bundle bundle = msg.getData();
				try {
					js = new JSONObject(bundle.getString(hpCantant.GETDATA));
					if (js.getString("code").equals(hpCantant.SUCCESS_CODE)) {
						Toast.makeText(mContext, js.getString("summary"),
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		}
	};

	private void getData() {
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				ToDetail(position, Constants.DYNAMIC_TYPE.NATIVE);

			}

		});
		CommonUtils.getData(mHandler, map, hpCantant.UDYNAMIC_URL,
				hpCantant.LABLE_UDYNAMICLIST);
	}

	// private Map<String, String> getMap() {
	// Map<String, String> map = new HashMap<String, String>();
	// map.put("lat", ApplicationController.getInstance().getLat());// 页码
	// map.put("lng", ApplicationController.getInstance().getLng());// 每条页数
	// return null;
	// }

	private void ToDetail(int position, int type) {
		Intent intent = new Intent();
		intent.setClass(mContext, SquareDTDetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("style", type + "");
		bundle.putString("countid", mList.get(position).getCountid());
		bundle.putString("id", mList.get(position).getId());
		bundle.putString("nickname", mList.get(position).getNickname());
		bundle.putString("icon", mList.get(position).getIcon());
		bundle.putString("contents", mList.get(position).getContents());
		bundle.putString("date", mList.get(position).getDate());
		bundle.putString("distance", distance);
		bundle.putString("likes", mList.get(position).getLikes());
		bundle.putString("classid", mList.get(position).getClassid());
		bundle.putString("images", mList.get(position).getImages());
		bundle.putString("goodicon", mList.get(position).getGoodicon());
		bundle.putString("goodclass", mList.get(position).getGoodclass());
		bundle.putString("goodid", mList.get(position).getGoodid());
		bundle.putString("goodtitle", mList.get(position).getGoodtitle());
		intent.putExtras(bundle);
		mContext.startActivity(intent);
	}

	private class myDTAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private Context mContext;
		private ShowImage mShowImage;

		public myDTAdapter(Context context) {
			this.inflater = LayoutInflater.from(context);
			mContext = context;
			mShowImage = new ShowImage(context);
		}

		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public Object getItem(int arg0) {
			return mList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View view, ViewGroup arg2) {
			ViewHolder holder = null;
			UDynamicList.Data data = mList.get(position);
			if (view == null) {
				view = inflater.inflate(R.layout.item_squary_dt, null);
				holder = new ViewHolder();
				holder.name = (TextView) view
						.findViewById(R.id.tv_squary_dt_name);
				holder.date = (TextView) view
						.findViewById(R.id.tv_squary_dt_time);
				holder.contents = (TextView) view
						.findViewById(R.id.tv_squary_dt_contents);
				holder.distance1 = (TextView) view
						.findViewById(R.id.tv_squary_dt_distance);
				holder.icon = (CircleImageView) view
						.findViewById(R.id.iv_squary_dt);
				// holder.share = (ImageButton) view
				// .findViewById(R.id.ibtn_squary_dt_share);
				holder.comment = (ImageButton) view
						.findViewById(R.id.ibtn_squary_dt_comment);
				holder.prise = (CheckBox) view
						.findViewById(R.id.cb_squary_dt_dz);
				// holder.likes = (TextView) view
				// .findViewById(R.id.tv_squary_dz_num);

				holder.llShare = (LinearLayout) view
						.findViewById(R.id.ll_squary_dt_share);
				holder.tvShareTitle = (TextView) view
						.findViewById(R.id.tv_squary_dt_share_title);
				holder.ivShareIcon = (ImageView) view
						.findViewById(R.id.iv_squary_dt_share_icon);
				holder.gvImages = (MyGridView) view
						.findViewById(R.id.gv_squary_dt_images);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();// 取出ViewHolder对象
			}
			initData(holder, position, data);
			return view;
		}

		private void initData(ViewHolder holder, final int position,
				final Data data) {
			mShowImage.showImageByUrl(holder.icon, data.getIcon(),
					R.drawable.user_null);
			holder.name.setText(data.getNickname());
			holder.date.setText(data.getDate());
			holder.contents.setText(data.getContents());
			if (data.getLikes().equals("0"))
				holder.prise.setChecked(false);
			else
				holder.prise.setChecked(true);
			// holder.distance.setText("distance;" +
			// data.getLat());
			try {
				double lat1 = Double.valueOf(data.getLat());
				double lng1 = Double.valueOf(data.getLng());
				double lat2 = Double.valueOf(ApplicationController
						.getInstance().getLat());
				double lng2 = Double.valueOf(ApplicationController
						.getInstance().getLng());
				if (CommonUtils.Distance(lng1, lat1, lng2, lat2) < 500.0)
					distance = "<500 m";
				else if (CommonUtils.Distance(lng1, lat1, lng2, lat2) < 1000.0)
					distance = "<1000 m";
				else if (CommonUtils.Distance(lng1, lat1, lng2, lat2) > 2000.0)
					distance = ">2km";
				else {
					DecimalFormat df = new DecimalFormat("#.0");

					distance = df.format(CommonUtils.Distance(lng1, lat1, lng2,
							lat2) / 1000.0) + " km";
				}
			} catch (Exception e) {
			}
			holder.distance1.setText(distance);
			switch (Integer.parseInt(data.getClassid())) {
			case Constants.DYNAMIC_TYPE.NATIVE:
				holder.llShare.setVisibility(View.GONE);
				holder.gvImages.setVisibility(View.GONE);
				ArrayList<String> images = null;
				if (data.getImages() != null && !data.getImages().equals("")) {
					images = getImage(data.getImages());
					holder.gvImages.setVisibility(View.VISIBLE);
					holder.gvImages
							.setAdapter(new CommonAdapter<String>(mContext,
									images, R.layout.item_square_dt_gridview) {
								@Override
								public void convert(
										com.hp.commonAdapter.ViewHolder helper,
										String item) {
									helper.setImageByUrl(R.id.iv_dt_image, item);
								}
							});
					holder.gvImages
							.setOnItemClickListener(new OnItemClickListener() {
								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int position1, long arg3) {
									Intent intent = new Intent(mContext,
											ImagePagerActivity.class);
									intent.putExtra(
											ImagePagerActivity.EXTRA_IMAGE_URLS,
											getImage(data.getImages()));
									intent.putExtra(
											ImagePagerActivity.EXTRA_IMAGE_INDEX,
											position1);
									mContext.startActivity(intent);
								}
							});
				}
				break;
			case Constants.DYNAMIC_TYPE.SHARE:
				holder.gvImages.setVisibility(View.GONE);
				holder.llShare.setVisibility(View.VISIBLE);
				holder.tvShareTitle.setText(data.getGoodtitle());
				mShowImage.showImageByUrl(holder.ivShareIcon,
						data.getGoodicon(), R.drawable.logo_homeparty);
				holder.llShare.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(mContext,
								GoodDetailActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("imageurl", data.getGoodicon());
						bundle.putString("id", data.getGoodid());
						bundle.putString("classid", data.getGoodclass());
						intent.putExtras(bundle);
						mContext.startActivity(intent);
					}
				});
				break;
			default:
				holder.gvImages.setVisibility(View.GONE);
				holder.llShare.setVisibility(View.GONE);
				break;
			}
			/** 为Button添加点击事件 */
			// holder.share.setOnClickListener(new OnClickListener() {
			// @Override
			// public void onClick(View v) {
			// Toast.makeText(mContext, "第三方分享暂未启动", Toast.LENGTH_SHORT)
			// .show();
			// }
			// });
			holder.icon.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(mContext,
							DTUserInfoActivity.class);
					intent.putExtra("nickname", data.getNickname());
					intent.putExtra("userid", data.getCountid());
					mContext.startActivity(intent);
				}
			});
			holder.comment.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ToDetail(position, 3);
				}
			});
			holder.prise
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton arg0,
								boolean arg1) {
							countid = ApplicationController.getInstance()
									.getUser().getInt("countid", 0);
							if (countid == 0) {
								Intent mIntent = new Intent();
								mIntent.setClass(mContext,
										UserLoginActivity.class);
								mContext.startActivity(mIntent);
							} else {
								ArrayMap<String, String> map = new ArrayMap<String, String>();
								map.put("countid", countid + "");// 页码
								map.put("dynamicid", data.getId());// 每条页数
								CommonUtils.getData(mHandler, map,
										hpCantant.UDYNAMICPRAISE_URL,
										hpCantant.LABLE_UDYNAMICPRAISE);
							}
						}
					});
		}
	}

	public class ViewHolder {
		TextView name, date, contents, distance1;
		// TextView likes;
		CircleImageView icon;
		ImageButton comment;
		CheckBox prise;
		MyGridView gvImages;

		/**
		 * 分享的内容
		 */
		ImageView ivShareIcon;
		TextView tvShareTitle;
		LinearLayout llShare;
	}

	private ArrayList<String> getImage(String images2) {
		ArrayList<String> image = new ArrayList<String>();
		// if ((data.getClassid().equals("1"))
		// && !TextUtils.isEmpty(data.getImages())) {
		StringTokenizer st = new StringTokenizer(images2, ";");
		while (st.hasMoreTokens()) {
			image.add(st.nextToken());
		}
		return image;
	}
}
