package com.hp.activity.userOrder;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.hp.R;
import com.hp.bean.UOrderinfoList;
import com.hp.bean.UOrderinfoList.Data;
import com.hp.commonAdapter.CommonAdapter;
import com.hp.commonAdapter.ViewHolder;
import com.hp.utils.CommonUtils;
import com.hp.utils.UOrderManager;
import com.hp.utils.hpCantant;

/**
 * 待付款Fragment的界面
 */
public class NoPayFragment extends MyOderFragment {
	private Activity activity;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}

	private List<UOrderinfoList.Data> mNoPayList = new ArrayList<UOrderinfoList.Data>();

	@Override
	protected void setMyOrderAdapter() {
		mNoPayList.clear();
		for (UOrderinfoList.Data item : mList) {
			if (item.getState().equals("0")) {
				mNoPayList.add(item);
			}
		}
		mAdapter = new CommonAdapter<UOrderinfoList.Data>(activity, mNoPayList,
				R.layout.item_order_listview) {

			@Override
			public void convert(ViewHolder helper, UOrderinfoList.Data item) {
				initUI(helper, item);
			}
		};
	}

	public void initUI(ViewHolder helper, final Data item) {
		helper.setText(R.id.tv_myorder_title, item.getTitle());
		helper.setText(R.id.tv_myorder_time,
				item.getBegintime() + "~" + item.getEndtime());
		helper.setText(R.id.tv_myorder_price, item.getPrice() + "");
		helper.setImageByUrl(R.id.iv_myorder_icon, item.getIcon());
		helper.setOnClickListener(R.id.btn_myorder_del, new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getActivity(), "del", 0).show();
				ArrayMap<String, String> map = new ArrayMap<String, String>();
				map.put("countid", countid);
				map.put("orderid", item.getId() + "");
				map.put("state", "1");
				new UOrderManager(getActivity(), map, mHandler);
			}
		});
		helper.setOnClickListener(
		// 为未付款的订单付款
				R.id.btn_myorder_pay, new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Toast.makeText(getActivity(), item.getBegintime(), 0)
								.show();
						mData = item;
						ArrayMap<String, String> map = new ArrayMap<String, String>();
						map.put("countid", countid);// 用户账号id
						map.put("orderid", item.getId() + "");// 订单id
						CommonUtils.getData(mHandler, map,
								hpCantant.UORDERPAY_URL,
								hpCantant.LABLE_UORDERPAY);
					}
				});
	}
}
