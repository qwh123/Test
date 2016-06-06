package com.hp.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hp.R;
import com.hp.bean.UGoodinfoDetail;
import com.hp.widget.MyListView;

public class GoodDetailHuiAdapter extends BaseAdapter {
	private Context mContext;
	List<UGoodinfoDetail.Data.Item> mItems;
	private LayoutInflater mInflater;

	public GoodDetailHuiAdapter(Context mContext,
			List<UGoodinfoDetail.Data.Item> mItems) {
		this.mContext = mContext;
		this.mItems = mItems;
		this.mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mItems.size() > 0 ? mItems.size() : 0;
	}

	@Override
	public Object getItem(int arg0) {
		return mItems.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_home_detail_hui_1,
					null);
			holder = new ViewHolder();
			/* 得到各个控件的对象 */
			holder.title = (TextView) convertView
					.findViewById(R.id.tv_home_detail_hui_1);
			holder.mListView = (MyListView) convertView
					.findViewById(R.id.lv_pop_home_detail_hui_2);
			convertView.setTag(holder);// 绑定ViewHolder对象
		} else {
			holder = (ViewHolder) convertView.getTag();// 取出ViewHolder对象
		}
		/* 设置TextView显示的内容，即我们存放在动态数组中的数据 */
		holder.title.setText(mItems.get(position).getName());
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();/* 在数组中存放数据 */
		for (int i = 0; i < mItems.get(position).getCheap().size(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("text", mItems.get(position).getCheap().get(i).getText());
			listItem.add(map);
		}
		holder.mListView.setAdapter(new SimpleAdapter(mContext, listItem,
				R.layout.item_home_detail_hui_2, new String[] { "text" },
				new int[] { R.id.tv_home_detail_hui_2 }));
		return convertView;

	}

	/* 存放控件 */
	private class ViewHolder {
		public TextView title;
		public MyListView mListView;
	}
}
