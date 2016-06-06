package com.tgj.fragment;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.hp.R;
import com.hp.application.ApplicationController;
import com.hp.commonAdapter.CommonAdapter;
import com.hp.commonAdapter.ViewHolder;
import com.hp.utils.CommonUtils;
import com.hp.utils.TGJCanst;
import com.hp.utils.hpCantant;
import com.tgj.Dao.BOrderDao;
import com.tgj.Dao.BOrderDaoImpl;
import com.tgj.activity.OrderinfoDetailActivity;
import com.tgj.bean.BOrderinfo;
import com.tgj.bean.BOrderinfo.Data;

public class TGJSuccessFragment extends Fragment {

	private ListView mNewOrderListView;
	private List<BOrderinfo.Data> mList = null;
	private View view;
	CommonAdapter<BOrderinfo.Data> mAdapter;
	private Handler mHandler = new Handler() {
		private JSONObject js;
		private BOrderDao mBOrderDao;

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == TGJCanst.LABLE_BORDERINFO) {
				Bundle bundle = msg.getData();
				mBOrderDao = new BOrderDaoImpl();
				try {
					js = new JSONObject(bundle.getString(hpCantant.GETDATA));
					if (js.getString("code").equals(hpCantant.SUCCESS_CODE)) {
						mList = mBOrderDao.loadNewOrderInfo(bundle
								.getString(hpCantant.GETDATA));
						getActivity().runOnUiThread(new Runnable() {
							public void run() {
								initData(mList);
							}
						});

					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	};

	private SharedPreferences mUserInfo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (savedInstanceState == null)
			view = inflater.inflate(R.layout.tgj_fragment_order_neworder,
					container, false);
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		init();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
	}

	private void init() {
		mNewOrderListView = (ListView) view.findViewById(R.id.lv_neworder);
		mUserInfo = ApplicationController.getInstance().getUser();
		if (mUserInfo.getInt("countid", 0) != 0) {
			if (CommonUtils.isNetworkAvailable(getActivity())) {
				ArrayMap<String, String> map = new ArrayMap<String, String>();
				map.put("countid", mUserInfo.getInt("countid", 0) + "");
				map.put("state", "1020");
				CommonUtils.getTGJData(mHandler, map, TGJCanst.BORDERINFO_URL,
						TGJCanst.LABLE_BORDERINFO);
			}
		}

		mNewOrderListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent intent = new Intent(mActivity,
						OrderinfoDetailActivity.class);
				intent.putExtra("orderid", mList.get(position).getId());
				startActivity(intent);
			}
		});

	}

	private Activity mActivity;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.mActivity = activity;
	}

	/**
	 * 列表数据
	 * 
	 * @param mList
	 */
	private void initData(List<Data> mList) {
		mAdapter = new CommonAdapter<BOrderinfo.Data>(mActivity, mList,
				R.layout.tgj_item_order_list_new) {
			@Override
			public void convert(ViewHolder helper, Data item) {
				if (item.getState().equals("1020")) {
					helper.setImageByUrl(R.id.iv_tgj_neworder_icon,
							item.getIcon());
					helper.setText(R.id.tv_tgj_neworder_title, item.getTitle());
					helper.setText(R.id.tv_tgj_neworder_time,
							item.getBegintime() + item.getEndtime());
					helper.setText(R.id.tv_tgj_neworder_num, item.getItemname());
					helper.setText(R.id.tv_tgj_neworder_newprice,
							item.getPrice());
				}
			}
		};
		mNewOrderListView.setAdapter(mAdapter);
	}

}
