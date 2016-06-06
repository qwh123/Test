package com.hp.fragment;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hp.R;
import com.hp.activity.SquareHDDetailActivity;
import com.hp.bean.UActiveList;
import com.hp.commonAdapter.CommonAdapter;
import com.hp.commonAdapter.ViewHolder;
import com.hp.dao.UActiveDao;
import com.hp.dao.UActiveDaoImpl;
import com.hp.utils.CommonUtils;
import com.hp.utils.hpCantant;

public class SquareHDFragment extends Fragment {
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
						if (mList.size() > 0 || mList != null) {
							try {

								mAdapter = new CommonAdapter<UActiveList.Data>(
										getActivity().getApplicationContext(),
										mList, R.layout.item_square_activity) {
									@Override
									public void convert(ViewHolder helper,
											com.hp.bean.UActiveList.Data item) {
										helper.setText(
												R.id.tv_square_activity_title,
												item.getTitle());
										helper.setText(
												R.id.tv_square_activity_lable,
												item.getSummary() + "");
										helper.setText(
												R.id.tv_square_activity_time,
												item.getDate() + "");
										helper.setText(
												R.id.tv_square_activity_adress,
												item.getAddress());
										helper.setImageByUrl(
												R.id.iv_square_activity_icon,
												item.getIcon());
									}
								};
								mAdapter.notifyDataSetChanged();
								mListView.setAdapter(mAdapter);
							} catch (Exception e) {
							}
						}
					}
					loadingLayout.setVisibility(View.GONE);
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
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long arg3) {
				Toast.makeText(getActivity(), position + "->you click", 1)
						.show();
				Intent intent = new Intent(getActivity(),
						SquareHDDetailActivity.class);
				intent.putExtra("activeid", mList.get(position).getId());
				startActivity(intent);
			}
		});
	}

	private void getData() {
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("pageindex", "1");
		map.put("pagesize", "20");
		map.put("lat", "120.0");
		map.put("lng", "112.0");
		CommonUtils.getData(mHandler, map, hpCantant.UACTIVE_URL, hpCantant.LABLE_UACTIVE);
	}

}
