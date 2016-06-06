package com.hp.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.hp.R;
import com.hp.activity.CityList.adapter.CityAdapter;
import com.hp.activity.CityList.data.CityData;
import com.hp.activity.CityList.model.CityItem;
import com.hp.activity.CityList.widget.ContactItemInterface;
import com.hp.activity.CityList.widget.ContactListViewImpl;
import com.hp.widget.TopBar;
import com.hp.widget.TopBar.topBarClickListener;

public class CityListActivity extends Activity implements TextWatcher {
	private Context context_ = CityListActivity.this;
	private final static int RETURN_CODE = 99;

	private ContactListViewImpl listview;

	private EditText searchBox;
	private String searchString;

	private Object searchLock = new Object();
	boolean inSearchMode = false;

	private final static String TAG = "CityListActivity";

	private LocationClient locationClient = null;

	List<ContactItemInterface> contactList;// 显示的城市列表
	List<ContactItemInterface> filterList;// 搜索的城市列表
	private SearchListTask curSearchTask = null;
	private LinearLayout lng_city_lay;// 定位当前城市
	private String lngCityName = "";// 存放返回的城市名
	private TextView lng_city;// 定位显示城市名

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.citylist);
		lng_city_lay = (LinearLayout) findViewById(R.id.lng_city_lay);
		lng_city = (TextView) findViewById(R.id.lng_city);
		lngCityName = getIntent().getStringExtra("lngCity");

		lng_city.setText(lngCityName);
		if (lngCityName == null) {
			// initGps();
			BDLocation b = new BDLocation();
			lngCityName = b.getAddrStr();
		}

		filterList = new ArrayList<ContactItemInterface>();
		contactList = CityData.getSampleContactList();

		CityAdapter adapter = new CityAdapter(this, R.layout.city_item,
				contactList);

		listview = (ContactListViewImpl) this.findViewById(R.id.listview);
		listview.setFastScrollEnabled(true);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView parent, View v, int position,
					long id) {
				List<ContactItemInterface> searchList = inSearchMode ? filterList
						: contactList;
				Intent intent = new Intent();
				intent.putExtra("lngCityName", searchList.get(position)
						.getDisplayInfo());
				setResult(RETURN_CODE, intent);
				finish();
			}
		});
		lng_city_lay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("lngCityName", lngCityName);
				setResult(RETURN_CODE, intent);
				finish();
			}
		});

		searchBox = (EditText) findViewById(R.id.input_search_query);
		searchBox.addTextChangedListener(this);
		TopBar tBar = (TopBar) findViewById(R.id.topbar_citylist);
		tBar.setTitleText("选择城市");
		tBar.setRightIsVisable(false);
		tBar.setOnTopBarClickListener(new topBarClickListener() {
			@Override
			public void rightClick() {
			}

			@Override
			public void leftClick() {
				finish();
			}
		});
	}

	@Override
	public void afterTextChanged(Editable s) {
		searchString = searchBox.getText().toString().trim().toUpperCase();

		if (curSearchTask != null
				&& curSearchTask.getStatus() != AsyncTask.Status.FINISHED) {
			try {
				curSearchTask.cancel(true);
			} catch (Exception e) {
				Log.i(TAG, "Fail to cancel running search task");
			}

		}
		curSearchTask = new SearchListTask();
		curSearchTask.execute(searchString);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// do nothing
	}

	private class SearchListTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			filterList.clear();

			String keyword = params[0];

			inSearchMode = (keyword.length() > 0);

			if (inSearchMode) {
				// get all the items matching this
				for (ContactItemInterface item : contactList) {
					CityItem contact = (CityItem) item;

					boolean isPinyin = contact.getFullName().toUpperCase()
							.indexOf(keyword) > -1;
					boolean isChinese = contact.getNickName().indexOf(keyword) > -1;

					if (isPinyin || isChinese) {
						filterList.add(item);
					}

				}

			}
			return null;
		}

		protected void onPostExecute(String result) {

			synchronized (searchLock) {

				if (inSearchMode) {

					CityAdapter adapter = new CityAdapter(context_,
							R.layout.city_item, filterList);
					adapter.setInSearchMode(true);
					listview.setInSearchMode(true);
					listview.setAdapter(adapter);
				} else {
					CityAdapter adapter = new CityAdapter(context_,
							R.layout.city_item, contactList);
					adapter.setInSearchMode(false);
					listview.setInSearchMode(false);
					listview.setAdapter(adapter);
				}
			}

		}
	}

	// private void initGps() {
	// try {
	// MyLocationListenner myListener = new MyLocationListenner();
	// locationClient = new LocationClient(CityListActivity.this);
	// locationClient.registerLocationListener(myListener);
	// LocationClientOption option = new LocationClientOption();
	// option.setOpenGps(true);
	// option.setAddrType("all");
	// option.setCoorType("bd09ll");
	// option.setScanSpan(5000);
	// locationClient.setLocOption(option);
	// locationClient.start();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	// @Override
	// public void onDestroy() {
	// // TODO Auto-generated method stub
	// super.onDestroy();
	// if (locationClient != null)
	// locationClient.stop();
	// }
	//
	// // 定位当前城市
	// private class MyLocationListenner implements BDLocationListener {
	// @Override
	// public void onReceiveLocation(BDLocation location) {
	//
	// if (location == null)
	// return;
	// StringBuffer sb = new StringBuffer(256);
	// if (location.getLocType() == BDLocation.TypeGpsLocation) {
	// // sb.append(location.getAddrStr());
	// sb.append(location.getCity());
	// } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
	// sb.append(location.getCity());
	// }
	// if (sb.toString() != null && sb.toString().length() > 0) {
	// lngCityName = sb.toString();
	// lng_city.setText(lngCityName);
	// }
	//
	// }
	//
	// public void onReceivePoi(BDLocation poiLocation) {
	//
	// }
	// }

}
