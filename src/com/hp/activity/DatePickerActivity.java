package com.hp.activity;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hp.R;
import com.hp.datepicker.wheelview.OnWheelScrollListener;
import com.hp.datepicker.wheelview.WheelView;
import com.hp.datepicker.wheelview.adapter.NumericWheelAdapter;
import com.hp.widget.TopBar;
import com.hp.widget.TopBar.topBarClickListener;

public class DatePickerActivity extends Activity {

	private LayoutInflater inflater = null;
	private WheelView year;
	private WheelView month;
	private WheelView day;
	private WheelView time;
	private WheelView hour;

	private int mYear = 1996;
	private int mMonth = 0;
	private int mDay = 1;

	LinearLayout ll;
	TextView tv1, tv2;

	View view = null;
	
	private String birthday;
	private int index;
	private String star;

	boolean isMonthSetted = false, isDaySetted = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_datepicker);
		inflater = (LayoutInflater) this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		ll = (LinearLayout) findViewById(R.id.ll);
		ll.addView(getDataPick());
		tv1 = (TextView) findViewById(R.id.tv1);// 年龄
		tv2 = (TextView) findViewById(R.id.tv2);// 星座
		initView();

	}

	private void initView() {
		TopBar tBar = (TopBar) findViewById(R.id.topbar_datepicker);
		tBar.setTitleText("出生日");
		tBar.setRighttImageResource(R.drawable.icon_right_tj);
		tBar.setOnTopBarClickListener(new topBarClickListener() {
			@Override
			public void rightClick() {
				Toast.makeText(getApplicationContext(), "you click me !",
						Toast.LENGTH_SHORT).show();
				Intent intent = getIntent();
				Bundle bundle=new Bundle();
				bundle.putString("birthday", birthday);
				bundle.putInt("starid", index);
				bundle.putString("star", star);
				intent.putExtras(bundle);
				setResult(3, intent);
				finish();
			}

			@Override
			public void leftClick() {
				finish();
			}
		});

	}

	private View getDataPick() {
		Calendar c = Calendar.getInstance();
		int norYear = c.get(Calendar.YEAR);
		// int curMonth = c.get(Calendar.MONTH) + 1;//通过Calendar算出的月数要+1
		// int curDate = c.get(Calendar.DATE);

		int curYear = mYear;
		int curMonth = mMonth + 1;
		int curDate = mDay;

		view = inflater.inflate(R.layout.wheel_date_picker, null);

		year = (WheelView) view.findViewById(R.id.year);
		NumericWheelAdapter numericWheelAdapter1 = new NumericWheelAdapter(
				this, 1950, norYear);
		numericWheelAdapter1.setLabel("年");
		year.setViewAdapter(numericWheelAdapter1);
		year.setCyclic(true);// 是否可循环滑动
		year.addScrollingListener(scrollListener);

		month = (WheelView) view.findViewById(R.id.month);
		NumericWheelAdapter numericWheelAdapter2 = new NumericWheelAdapter(
				this, 1, 12, "%02d");
		numericWheelAdapter2.setLabel("月");
		month.setViewAdapter(numericWheelAdapter2);
		month.setCyclic(true);
		month.addScrollingListener(scrollListener);

		day = (WheelView) view.findViewById(R.id.day);
		initDay(curYear, curMonth);
		day.setCyclic(true);
		day.addScrollingListener(scrollListener);

		// time= (WheelView) view.findViewById(R.id.time);
		// String[] times = {"上午","下午"} ;
		// ArrayWheelAdapter<String> arrayWheelAdapter=new
		// ArrayWheelAdapter<String>(MainActivity.this,times );
		// time.setViewAdapter(arrayWheelAdapter);
		// time.setCyclic(false);
		// time.addScrollingListener(scrollListener);

		hour = (WheelView) view.findViewById(R.id.hour);
		NumericWheelAdapter numericWheelAdapter3 = new NumericWheelAdapter(
				this, 1, 23, "%02d");
		numericWheelAdapter3.setLabel("时");
		hour.setViewAdapter(numericWheelAdapter3);
		hour.setCyclic(true);
		hour.addScrollingListener(scrollListener);


		year.setVisibleItems(7);// 设置显示行数
		month.setVisibleItems(7);
		day.setVisibleItems(7);
		// time.setVisibleItems(7);
		hour.setVisibleItems(7);

		year.setCurrentItem(curYear - 1950);
		month.setCurrentItem(curMonth - 1);
		day.setCurrentItem(curDate - 1);

		return view;
	}

	OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
		@Override
		public void onScrollingStarted(WheelView wheel) {

		}
		@Override
		public void onScrollingFinished(WheelView wheel) {
			int n_year = year.getCurrentItem() + 1950;// 年
			int n_month = month.getCurrentItem() + 1;// 月

			initDay(n_year, n_month);

			birthday = new StringBuilder()
					.append((year.getCurrentItem() + 1950))
					.append("-")
					.append((month.getCurrentItem() + 1) < 10 ? "0"
							+ (month.getCurrentItem() + 1) : (month
							.getCurrentItem() + 1))
					.append("-")
					.append(((day.getCurrentItem() + 1) < 10) ? "0"
							+ (day.getCurrentItem() + 1) : (day
							.getCurrentItem() + 1)).toString();
			star=getAstro(month.getCurrentItem() + 1,
					day.getCurrentItem() + 1);
			tv1.setText("出生日期:	" + birthday);
			tv2.setText("星		座:	"
					+ star);
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
		NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(this,
				1, getDay(arg1, arg2), "%02d");
		numericWheelAdapter.setLabel("日");
		day.setViewAdapter(numericWheelAdapter);
	}

	/**
	 * 根据月日计算星座
	 * 
	 * @param month
	 * @param day
	 * @return
	 */
	public String getAstro(int month, int day) {
		String[] astro = new String[] { "摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座",
				"双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座" };
		int[] arr = new int[] { 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22 };// 两个星座分割日
		index = month;
		// 所查询日期在分割日之前，索引-1，否则不变
		if (day < arr[month - 1]) {
			index = index - 1;
		}
		// 返回索引指向的星座string
		return astro[index];
	}

}
