package com.hp.alipay.pay;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.hp.R;
import com.hp.application.ApplicationController;

public class BookFragment extends Fragment {
	private View view;
	private TextView tvDate;

	//private TimeSelector mSelector;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.aty_good_order, container, false);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
//		Date day = new Date();
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		tvDate = (TextView) view.findViewById(R.id.tv_order_date);
//
//		mSelector = new TimeSelector(getActivity(),
//				new TimeSelector.ResultHandler() {
//					@Override
//					public void handle(String time) {
//						Toast.makeText(getActivity(), time, Toast.LENGTH_LONG)
//								.show();
//						tvDate.setText(time);
//					}
//				}, df.format(day), "2016-10-01 23:59");
		((EditText) view.findViewById(R.id.edt_order_tel))
				.setHint(ApplicationController.getInstance().getUser()
						.getString("tel", "联系号码"));
	}

}
