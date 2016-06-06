package com.hp.activity.userOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.hp.R;
import com.hp.activity.PublishDTActivity;
import com.hp.application.ApplicationController;
import com.hp.bean.UOrderinfoList;
import com.hp.bean.UOrderinfoList.Data;
import com.hp.commonAdapter.CommonAdapter;
import com.hp.commonAdapter.ViewHolder;
import com.hp.dao.UOrderInfoDao;
import com.hp.dao.UOrderInfoDaoImpl;
import com.hp.utils.hpCantant;
import com.hp.utils.volley.VolleyUtils;
import com.hp.utils.volley.VolleyUtilsImpl;

/**
 * 已完成Fragment的界面 www.javaapk.com test
 * 
 * @author qwh
 */
public class SuccessFragment extends MyOderFragment {
	private int COMMENT_ORDER = 10;
	private List<UOrderinfoList.Data> mSuccessList = new ArrayList<UOrderinfoList.Data>();
	private Activity mActivity;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = activity;
	}

	@Override
	protected void setMyOrderAdapter() {
		mSuccessList.clear();
		for (UOrderinfoList.Data item : mList) {
			if (item.getState().equals("1020")) {
				mSuccessList.add(item);
			}
		}
		if (getActivity() != null)
			mAdapter = new CommonAdapter<UOrderinfoList.Data>(mActivity,
					mSuccessList, R.layout.item_order_listview) {
				@Override
				public void convert(ViewHolder helper, UOrderinfoList.Data item) {
					initUI(helper, item);
				}
			};
	}

	public void initUI(ViewHolder helper, Data item) {
		helper.setText(R.id.tv_myorder_title, item.getTitle());
		helper.setText(R.id.tv_myorder_time,
				item.getBegintime() + "~" + item.getEndtime());
		helper.setText(R.id.tv_myorder_price, item.getPrice() + "");
		helper.setImageByUrl(R.id.iv_myorder_icon, item.getIcon());
		helper.setVisibility(R.id.btn_myorder_del, View.GONE);
		helper.setText(R.id.btn_myorder_pay, "待评价");
		helper.setOnClickListener(// daipingjia
				R.id.btn_myorder_pay, new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(getActivity(),
								PublishDTActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("title", "评价");
						intent.putExtras(bundle);
						startActivityForResult(intent, COMMENT_ORDER);
					}
				});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == COMMENT_ORDER) {
			Toast.makeText(getActivity(), data.getStringExtra("result"), 0)
					.show();
		}
	}
}
