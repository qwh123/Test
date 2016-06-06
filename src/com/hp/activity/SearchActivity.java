package com.hp.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.util.ArrayMap;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hp.R;
import com.hp.application.ApplicationController;
import com.hp.bean.UGoodInfoList;
import com.hp.bean.UGoodInfoList.Data;
import com.hp.commonAdapter.CommonAdapter;
import com.hp.commonAdapter.ViewHolder;
import com.hp.dao.UGoodInfoDao;
import com.hp.utils.CommonUtils;
import com.hp.utils.hpCantant;
import com.hp.utils.volley.VolleyUtils;
import com.hp.utils.volley.VolleyUtilsImpl;
import com.hp.widget.MyGridView;

public class SearchActivity extends Activity implements OnClickListener {
	private static final String SEARCH = "搜索";
	private static final String CANCEL = "取消";
	private String inputString;
	private List<String> mHotList;
	// 搜索结果列表view
	private ListView lvResults;
	// 搜索框
	private EditText edtInput;
	private Button btnCancel;
	private ImageView ivDel;

	// 获取搜索结果
	private List<UGoodInfoList.Data> mDatas;
	private UGoodInfoDao mDao;
	// 搜索结果列表adapter
	private CommonAdapter<UGoodInfoList.Data> resultAdapter;
	// 热词结果列表adapter
	private CommonAdapter<String> hotAdapter;
	private LinearLayout hotView;

	private Handler mHandler = new Handler() {

		private JSONObject js;

		public void handleMessage(android.os.Message msg) {
			if (msg.what == hpCantant.LABLE_UHotKey) {
				Bundle bundle = msg.getData();
				StringTokenizer hot = new StringTokenizer(
						bundle.getString(hpCantant.GETDATA), ";");
				if (hot != null) {
					while (hot.hasMoreTokens()) {
						mHotList.add(hot.nextToken());
					}
				}
				runOnUiThread(new Runnable() {
					public void run() {
						initViews();
					}
				});
			} else if (msg.what == hpCantant.LABLE_UGoodSearch) {
				Bundle bundle = msg.getData();
				mDao = new UGoodInfoDao();
				try {
					js = new JSONObject(bundle.getString(hpCantant.GETDATA));
					if (js.getString("code").equals(hpCantant.SUCCESS_CODE)
							&& !js.getString("data").isEmpty()) {
						mDatas = mDao.loadGood(bundle
								.getString(hpCantant.GETDATA));
						initUI(mDatas);
					} else {
						Toast.makeText(SearchActivity.this, "暂无结果",
								Toast.LENGTH_SHORT).show();
						hotView.setVisibility(View.VISIBLE);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	};
	private MyGridView gvHot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search);
		mHotList = new ArrayList<String>();
		initHotData();
	}

	/**
	 * 初始化视图
	 */
	private void initViews() {
		edtInput = (EditText) findViewById(R.id.edt_search_input);
		btnCancel = (Button) findViewById(R.id.btn_search_back);
		btnCancel.setOnClickListener(this);
		ivDel = (ImageView) findViewById(R.id.iv_search_delete);
		ivDel.setOnClickListener(this);
		// 初始化热词界面
		hotView =(LinearLayout) findViewById(R.id.ll_search_headview);
		if (mHotList.size() > 0)
			initHot();
		else {
			((TextView) findViewById(R.id.tv_search_hot_null))
					.setVisibility(View.VISIBLE);
		}
		edtInput.addTextChangedListener(new EditChangedListener());

		lvResults = (ListView) findViewById(R.id.main_lv_search_results);
		gvHot.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				hotView.setVisibility(View.GONE);
				Log.i(getPackageName(), mHotList.get(position) + " ");
				search(mHotList.get(position));
			}
		});

	}
	private void initUI(List<Data> mData) {
		hotView.setVisibility(View.GONE);
		new initGoodsView(SearchActivity.this, lvResults, mData);
	};

	/**
	 * 初始化热词界面
	 */
	private void initHot() {

		gvHot = (MyGridView) hotView.findViewById(R.id.gv_search_hot);
		hotAdapter = new CommonAdapter<String>(SearchActivity.this, mHotList,
				R.layout.item_search_hot) {
			@Override
			public void convert(ViewHolder helper, String item) {
				helper.setText(R.id.btn_search_hot, item);
			}
		};
		gvHot.setAdapter(hotAdapter);

	}

	/**
	 * 初始化数据
	 */
	private void initHotData() {
		VolleyUtils mUtils = new VolleyUtilsImpl();
		mUtils.getRequestString(hpCantant.URL + hpCantant.UHotKey_URL,
				mHandler, hpCantant.LABLE_UHotKey);
	}

	class EditChangedListener implements TextWatcher {
		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			Log.d(getPackageResourcePath(), " onTextChanged--------------->");
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			Log.d(getPackageName(), " beforeTextChanged--------------->");
		}

		@Override
		public void afterTextChanged(Editable arg0) {
			Log.d(getPackageName(), "afterTextChanged--------------->");
			inputString = edtInput.getText().toString();
			if (!inputString.isEmpty()) {
				btnCancel.setText(SEARCH);
				ivDel.setVisibility(View.VISIBLE);
			} else {
				btnCancel.setText(CANCEL);
				ivDel.setVisibility(View.GONE);
			}
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_search_back:
			if (btnCancel.getText().equals(SEARCH)) {
				search(inputString);
			} else {
				SearchActivity.this.finish();
			}

			break;
		case R.id.iv_search_delete:
			edtInput.setText("");
			break;
		default:
			break;
		}
	}

	private void search(String key) {
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("key", "玩");
		map.put("lat", ApplicationController.getInstance().getLat());
		map.put("lng", ApplicationController.getInstance().getLng());
		map.put("pageindex", "1");
		map.put("pagesize", "5");
		Log.i(getPackageName(), map.toString());
		CommonUtils.getData(mHandler, map, hpCantant.UGoodSearch_URL,
				hpCantant.LABLE_UGoodSearch);
	}

}
