package com.hp.activity.userActivity;

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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hp.R;
import com.hp.application.ApplicationController;
import com.hp.bean.UActiveList;
import com.hp.commonAdapter.CommonAdapter;
import com.hp.commonAdapter.ViewHolder;
import com.hp.dao.UActiveDao;
import com.hp.dao.UActiveDaoImpl;
import com.hp.utils.CommonUtils;
import com.hp.utils.hpCantant;

public class UserJoinFragment extends Fragment {
	RelativeLayout loadingLayout;
	private View view;
	ListView mListView;
	UActiveDao mActiveDao;
	private List<UActiveList.Data> mList;
	private Handler mHandler = new Handler() {
		private JSONObject js;
		private CommonAdapter<UActiveList.Data> mAdapter;

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == hpCantant.LABLE_UACTIVE) {
				Bundle bundle = msg.getData();
				mActiveDao = new UActiveDaoImpl();
				try {
					js = new JSONObject(bundle.getString(hpCantant.GETDATA));
					if (js.getString("code").equals(hpCantant.SUCCESS_CODE)) {
						mList = mActiveDao.loadActive(bundle
								.getString(hpCantant.GETDATA));
						try {
							mAdapter = new CommonAdapter<UActiveList.Data>(
									getActivity(), mList,
									R.layout.item_square_activity) {

								@Override
								public void convert(ViewHolder helper,
										com.hp.bean.UActiveList.Data item) {
									helper.setText(
											R.id.tv_square_activity_title,
											item.getTitle())
											.setText(
													R.id.tv_square_activity_lable,
													item.getSummary() + "")
											.setText(
													R.id.tv_square_activity_time,
													item.getDate() + "")
											.setText(
													R.id.tv_square_activity_adress,
													item.getAddress())
											.setImageByUrl(
													R.id.iv_square_activity_icon,
													item.getId());
								}
							};
							mListView.setAdapter(mAdapter);
						} catch (Exception e) {
							Toast.makeText(getActivity(), "尚未参与任何活动",
									Toast.LENGTH_SHORT).show();
						}

						loadingLayout.setVisibility(View.GONE);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_square_huodong, container,
				false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mListView = (ListView) view.findViewById(R.id.lv_square_huodong);
		loadingLayout = (RelativeLayout) view.findViewById(R.id.hd_loading);
		getData();
	}

	private void getData() {
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("pageindex", "1");// 页面
		map.put("pagesize", "20");// 页面大小
		map.put("lat", "120.0");// 经度
		map.put("lng", "112.0");// 维度
		map.put("countid", ApplicationController.getInstance().getUser()
				.getInt("countid", 0)
				+ "");// 用户id
		map.put("way", "1");// 获取方式1.为我举办的 2为我参与的
		CommonUtils.getData(mHandler, map, hpCantant.UACTIVE_URL,
				hpCantant.LABLE_UACTIVE);
	}

}
