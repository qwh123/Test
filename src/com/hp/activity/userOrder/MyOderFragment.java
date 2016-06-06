package com.hp.activity.userOrder;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.hp.R;
import com.hp.alipay.pay.OpenZFB;
import com.hp.application.ApplicationController;
import com.hp.bean.UOrderinfoList;
import com.hp.bean.UOrderinfoList.Data;
import com.hp.commonAdapter.CommonAdapter;
import com.hp.dao.UOrderInfoDao;
import com.hp.dao.UOrderInfoDaoImpl;
import com.hp.utils.CommonUtils;
import com.hp.utils.UOrderManager;
import com.hp.utils.hpCantant;

public abstract class MyOderFragment extends Fragment {
	String countid;

	UOrderinfoList.Data mData;

	UOrderInfoDao mOrderInfoDao;
	public List<UOrderinfoList.Data> mList = null;
	protected CommonAdapter<UOrderinfoList.Data> mAdapter;
	public Handler mHandler = new Handler() {
		private JSONObject js;
		private Bundle bundle = null;

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == hpCantant.LABLE_UORDERINFO) {
				bundle = msg.getData();
				mOrderInfoDao = new UOrderInfoDaoImpl();
				try {
					js = new JSONObject(bundle.getString(hpCantant.GETDATA));
					if (js.getString("code").equals(hpCantant.SUCCESS_CODE)) {
						getActivity().runOnUiThread(new Runnable() {
							public void run() {
								mList = mOrderInfoDao.loadOrderList(bundle
										.getString(hpCantant.GETDATA));
								if (mList.size() > 0 || mList != null) {
									setMyOrderAdapter();
									lvMyOrder.setAdapter(mAdapter);
								}
							}
						});
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else if (msg.what == hpCantant.LABLE_UORDERPAY) {
				bundle = msg.getData();
				try {
					js = new JSONObject(bundle.getString(hpCantant.GETDATA));
					String data = js.getString("data");
					new OpenZFB(getActivity(), data);
				} catch (JSONException e) {
					e.printStackTrace();
					Toast.makeText(getActivity(), "支付宝打开失败", 0).show();
				}
			} else if (msg.what == UOrderManager.REFRESH) {
				getData();
				new NoPayFragment();
			}
		}
	};

	public ListView lvMyOrder;

	protected abstract void setMyOrderAdapter();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		countid = ApplicationController.getInstance().getUser()
				.getInt("countid", 0)
				+ "";
		getData();// 获取数据
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_myorder, container,
				false);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		lvMyOrder = (ListView) view.findViewById(R.id.lv_myOrder);
		lvMyOrder.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(getActivity(), mList.get(arg2).getTitle(), 0)
						.show();

			}
		});
	}

	private void getData() {
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("countid", countid);// 用户账号id
		map.put("pageindex", "1");// 页码
		map.put("pagesize", "20");// 每条页数
		CommonUtils.getData(mHandler, map, hpCantant.UORDERINFOLIST_URL,
				hpCantant.LABLE_UORDERINFO);
	}

}
