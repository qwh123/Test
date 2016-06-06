package com.hp.activity;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.hp.R;
import com.hp.bean.UGoodInfoList;
import com.hp.bean.UGoodInfoList.Data;
import com.hp.commonAdapter.CommonAdapter;
import com.hp.commonAdapter.ViewHolder;

public class initGoodsView {
	private Context mContext = null;
	private ListView mListView = null;
	private List<Data> mList = null;

	public initGoodsView(Context mContext, ListView mView, List<Data> mList) {
		this.mContext = mContext;
		this.mListView = mView;
		this.mList = mList;
		if (mList != null)
			initData();
	}

	public void initData() {
		CommonAdapter<UGoodInfoList.Data> mAdapter = new CommonAdapter<UGoodInfoList.Data>(
				mContext, mList, R.layout.item_home_category_show) {
			@Override
			public void convert(ViewHolder helper, Data item) {
				helper.setText(R.id.tv_home_category_title, item.getTitle());
				helper.setText(R.id.tv_home_category_acount,
						"数量：" + item.getAmount());
				helper.setText(R.id.tv_home_category_adress, item.getDistance()
						+ "");
				if (item.getAllprice() != null) {
					if (item.getAllprice().equals("0")) {
						helper.setText(R.id.tv_home_category_price, "免费");
					} else
						helper.setText(R.id.tv_home_category_price,
								"￥ " + item.getAllprice());
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
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(mContext, GoodDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("imageurl", mList.get(arg2).getIcon());
				bundle.putString("id", mList.get(arg2).getId() + "");
				String type = mList.get(arg2).getClassid();
				bundle.putString("classid", type + "");
				intent.putExtras(bundle);
				mContext.startActivity(intent);
			}
		});
	}
}
