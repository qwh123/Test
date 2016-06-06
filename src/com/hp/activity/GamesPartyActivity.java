package com.hp.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hp.R;

public class GamesPartyActivity extends Activity {
//	private ListView rListView;
//	private List<storeInfo> mList;
//	private View view;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.aty_games_paty);
//		mList = hptCanst.mStoreInfos;
//		TextView title = (TextView) findViewById(R.id.id_tv_title);
//		title.setText("聚会玩");
//		init();
//
//	}
//
//	/**
//	 * 返回趣游界面
//	 */
//	public void doBack(View v) {
//		this.finish();
//	}
//
//	public void init() {
//
//		getData();
//
//		rListView = (ListView) findViewById(R.id.id_store_listVew);
//		rListView.setAdapter(new MyAdapter());
//		rListView.setOnItemClickListener(new OnItemClickListener() {
//			public void onItemClick(AdapterView<?> arg0, View view,
//					int postion, long arg3) {
//				Toast.makeText(GamesPartyActivity.this,
//						mList.get(postion).getContext(), Toast.LENGTH_SHORT)
//						.show();
//				Intent intent = new Intent(GamesPartyActivity.this,
//						StoreDetail.class);
//				intent.putExtra("storeId", postion);
//				startActivity(intent);
//			}
//		});
//	}
//
//	private void getData() {
//		hptCanst.mStoreInfos = new ArrayList<storeInfo>();
//		storeInfo info = new storeInfo();
//		// info.set_id(0);
//		info.setIcon_id(R.drawable.guide_350_04);
//		info.setGameName("石头剪子布");
//		hptCanst.mStoreInfos.add(info);
//		hptCanst.mStoreInfos.add(info);
//		storeInfo info1 = new storeInfo();
//		// info.set_id(0);
//		info1.setIcon_id(R.drawable.guide_350_03);
//		info1.setGameName("穿牙签");
//		info1.setContext("美国耕地面积约占国土总面积的20%，人均接近0.6公顷，农业的机械化耕作和规模"
//				+ "经营程度非常高。我国耕地面积排世界第三，仅次于美国和印度，但由于我国人口众多，"
//				+ "人均耕地面积排在126位以后，还不到世界人均耕地面积的一半，"
//				+ "目前大多数地方仍然是精耕细作的小农经营模式，尤其是在一些不发达地区。"
//				+ "如何能够在有限的资源下养活中国的老百姓？美国的农业发展技术、"
//				+ "经济机制构成等等都对我国有着借鉴意义。迄今为止，中美农业科技合作成果丰硕，"
//				+ "中国广阔的市场已成为美国农业科技创新的动力。");
//		hptCanst.mStoreInfos.add(info1);
//
//		mList = hptCanst.mStoreInfos;
//	}
//
//	private class MyAdapter extends BaseAdapter {
//
//		@Override
//		public int getCount() {
//			return mList.size();
//		}
//
//		@Override
//		public Object getItem(int position) {
//			return mList.get(position);
//		}
//
//		@Override
//		public long getItemId(int position) {
//			return position;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			ViewHolder viewHolder = null;
//			storeInfo info = mList.get(position);
//			if (convertView == null) {
//				LayoutInflater inflater = LayoutInflater
//						.from(GamesPartyActivity.this);
//				convertView = inflater.inflate(R.layout.store_listview_item,
//						null);
//				viewHolder = new ViewHolder();
//
//				viewHolder.GameName = (TextView) convertView
//						.findViewById(R.id.id_tv_GameName);
//
//				convertView.setTag(viewHolder);
//			} else {
//
//				viewHolder = (ViewHolder) convertView.getTag();
//			}
//
//			viewHolder.GameName.setText(info.getGameName());
//			return convertView;
//		}
//	}
//
//	public static class ViewHolder {
//		public TextView GameName;
//	}

}
