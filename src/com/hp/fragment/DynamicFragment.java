package com.hp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.hp.R;
import com.hp.activity.PublishDTActivity;
import com.hp.utils.getDynamic;

public class DynamicFragment extends Fragment {
	private SwipeRefreshLayout mSwipeRefreshLayout;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_dynamic, container, false);
	}

	private ListView mListView = null;
	private ProgressBar pgb = null;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		pgb = (ProgressBar) view.findViewById(R.id.pgb_dynamic);
		mSwipeRefreshLayout = (SwipeRefreshLayout) view
				.findViewById(R.id.dynamic_swipe_layout);
		mSwipeRefreshLayout.setColorSchemeResources(
				android.R.color.holo_blue_light,
				android.R.color.holo_red_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_green_light);
		mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				ArrayMap<String, String> map = new ArrayMap<String, String>();
				new getDynamic(mSwipeRefreshLayout, true, getActivity(), map,
						mListView);
			}
		});

		mListView = (ListView) view.findViewById(R.id.lv_square_dt);
		((ImageButton) view.findViewById(R.id.ibtn_publish_dt))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(getActivity(),
								PublishDTActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("title", "发布动态");
						bundle.putString("rightText", "发布");
						intent.putExtras(bundle);
						startActivityForResult(intent, 100);
					}
				});
		getData();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private void getData() {
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		// map.put("lat", ApplicationController.getInstance().getLat());
		// map.put("lng", ApplicationController.getInstance().getLng());
		new getDynamic(getActivity(), map, mListView, pgb);

	}

	@SuppressWarnings("static-access")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 100 && resultCode == getActivity().RESULT_OK) {
			getData();
		}
	}
}
