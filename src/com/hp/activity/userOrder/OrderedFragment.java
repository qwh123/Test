package com.hp.activity.userOrder;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.hp.R;
import com.hp.bean.UOrderinfoList;
import com.hp.bean.UOrderinfoList.Data;
import com.hp.commonAdapter.CommonAdapter;
import com.hp.commonAdapter.ViewHolder;

/**
 * 已订购Fragment的界面
 * 
 */
public class OrderedFragment extends MyOderFragment {
	private List<UOrderinfoList.Data> mOrderList = new ArrayList<UOrderinfoList.Data>();

	@Override
	protected void setMyOrderAdapter() {
		mOrderList.clear();
		for (UOrderinfoList.Data item : mList) {
			if (item.getState().equals("1010")) {
				mOrderList.add(item);
			}
		}
		mAdapter = new CommonAdapter<UOrderinfoList.Data>(getActivity(),
				mOrderList, R.layout.item_order_listview) {

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
		helper.setVisibility(R.id.btn_myorder_del, View.GONE);
		helper.setText(R.id.btn_myorder_pay, "验票");
		helper.setOnClickListener(// 扫码验证
				R.id.btn_myorder_pay, new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(getActivity(),
								QRGenActivity.class);
						intent.putExtra("orderid", item.getId());
						startActivity(intent);
					}
				});

	}

}
