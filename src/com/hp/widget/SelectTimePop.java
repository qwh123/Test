package com.hp.widget;

import java.util.Calendar;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hp.R;
import com.hp.datepicker.wheelview.OnWheelScrollListener;
import com.hp.datepicker.wheelview.WheelView;
import com.hp.datepicker.wheelview.adapter.NumericWheelAdapter;

public class SelectTimePop extends PopupWindow {
	private LayoutInflater inflater = null;
	private WheelView year;
	private WheelView month;
	private WheelView day;
	private WheelView hour;
	private WheelView min;

	private int mYear ;
	private int mMonth;
	private int mDay ;

	private View view;
	private Context mContext;

	private Button btnSelect;
	private TextView tvDate;

	public SelectTimePop(Context context, TextView txtView) {
		tvDate = txtView;
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.pop_select_date, null);
		btnSelect=(Button) view.findViewById(R.id.btn_select);
		// 设置SelectPicPopupWindow的View
		this.setContentView(view);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.PopupAnimation);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x80000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		// mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		getDataPick();

	}

	private View getDataPick() {
		Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		 mMonth = c.get(Calendar.MONTH) + 1;//通过Calendar算出的月数要+1
		 mDay = c.get(Calendar.DATE);
		int curYear = mYear;
		int curMonth = mMonth ;
		int curDate = mDay;
		year = (WheelView) view.findViewById(R.id.date_year);
		NumericWheelAdapter numericWheelAdapter1 = new NumericWheelAdapter(
				mContext, mYear, 2050);
		numericWheelAdapter1.setLabel("年");
		year.setViewAdapter(numericWheelAdapter1);
		year.setCyclic(true);// 是否可循环滑动
		year.addScrollingListener(scrollListener);

		month = (WheelView) view.findViewById(R.id.date_month);
		NumericWheelAdapter numericWheelAdapter2 = new NumericWheelAdapter(
				mContext, 1, 12, "%02d");
		numericWheelAdapter2.setLabel("月");
		month.setViewAdapter(numericWheelAdapter2);
		month.setCyclic(true);
		month.addScrollingListener(scrollListener);

		day = (WheelView) view.findViewById(R.id.date_day);
		initDay(curYear, curMonth);
		day.setCyclic(true);
		day.addScrollingListener(scrollListener);


		hour = (WheelView) view.findViewById(R.id.date_hour);
		NumericWheelAdapter numericWheelAdapter3 = new NumericWheelAdapter(
				mContext, 1, 23, "%02d");
		numericWheelAdapter3.setLabel("时");
		hour.setViewAdapter(numericWheelAdapter3);
		hour.setCyclic(true);
		hour.addScrollingListener(scrollListener);

		min = (WheelView) view.findViewById(R.id.date_min);
		NumericWheelAdapter numericWheelAdapter4 = new NumericWheelAdapter(
				mContext, 1, 59, "%02d");
		numericWheelAdapter4.setLabel("分");
		min.setViewAdapter(numericWheelAdapter4);
		min.setCyclic(true);
		min.addScrollingListener(scrollListener);

		year.setVisibleItems(7);// 设置显示行数
		month.setVisibleItems(7);
		day.setVisibleItems(7);
		hour.setVisibleItems(7);
		min.setVisibleItems(7);

		year.setCurrentItem(curYear - mYear);
		month.setCurrentItem(curMonth - 1);
		day.setCurrentItem(curDate - 1);

		return view;
	}

	private String datetime;
	OnWheelScrollListener scrollListener = new OnWheelScrollListener() {

		@Override
		public void onScrollingStarted(WheelView wheel) {

		}

		@Override
		public void onScrollingFinished(WheelView wheel) {
			int n_year = year.getCurrentItem() + mYear;// 年
			int n_month = month.getCurrentItem() + 1;// 月

			initDay(n_year, n_month);

			datetime = new StringBuilder()
					.append((year.getCurrentItem() + mYear))
					.append("-")
					.append((month.getCurrentItem() + 1) < 10 ? "0"
							+ (month.getCurrentItem() + 1) : (month
							.getCurrentItem() + 1))
					.append("-")
					.append(((day.getCurrentItem() + 1) < 10) ? "0"
							+ (day.getCurrentItem() + 1) : (day
							.getCurrentItem() + 1)).toString();
			// tv1.setText("出生日期:	" + birthday);
			// tv2.setText("星		座:	" + star);
			btnSelect.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					dismiss();
					tvDate.setText(datetime);
				}
			});
		}
	};

	/**
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	private int getDay(int year, int month) {
		int day = 30;
		boolean flag = false;
		switch (year % 4) {
		case 0:
			flag = true;
			break;
		default:
			flag = false;
			break;
		}
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;
		case 2:
			day = flag ? 29 : 28;
			break;
		default:
			day = 30;
			break;
		}
		return day;
	}

	/**
	 */
	private void initDay(int arg1, int arg2) {
		NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(
				mContext, 1, getDay(arg1, arg2), "%02d");
		numericWheelAdapter.setLabel("日");
		day.setViewAdapter(numericWheelAdapter);
	}

}
