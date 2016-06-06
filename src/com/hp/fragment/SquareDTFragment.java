package com.hp.fragment;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hp.R;
import com.hp.application.ApplicationController;
import com.hp.utils.getDynamic;

public class SquareDTFragment extends Fragment {
	private ListView mListView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_square_dongtai, container,
				false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mListView = (ListView) view.findViewById(R.id.lv_square_dt);

		Map<String, String> map = new HashMap<String, String>();
		map.put("lat", ApplicationController.getInstance().getLat());
		map.put("lng", ApplicationController.getInstance().getLng());

		//new getDynamic(getActivity(), map, mListView);

	}

}
