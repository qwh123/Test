package com.hp.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.hp.R;
import com.hp.application.ApplicationController;
import com.hp.bean.UDynamicCommentList;
import com.hp.widget.CircleImageView;

/**
 * 评论列表适配器
 * 
 * @author wwj_748
 * 
 */
public class CommentAdapter extends BaseAdapter {
	private ViewHolder holder;
	private LayoutInflater layoutInflater;
	private Context context;
	private List<UDynamicCommentList.Data> list;

	private SpannableStringBuilder htmlSpannable;

	private ImageLoader imageLoader;

	private String replyText;

	public CommentAdapter(Context c) {
		super();
		layoutInflater = (LayoutInflater) LayoutInflater.from(c);
		list = new ArrayList<UDynamicCommentList.Data>();

		if (imageLoader == null)
			imageLoader = ApplicationController.getInstance().getImageLoader();
	}

	public void setList(List<UDynamicCommentList.Data> list) {
		this.list = list;
	}

	public void addList(List<UDynamicCommentList.Data> list) {
		this.list.addAll(list);
	}

	public void clearList() {
		this.list.clear();
	}

	public List<UDynamicCommentList.Data> getList() {
		return list;
	}

	public void removeItem(int position) {
		if (list.size() > 0) {
			list.remove(position);
		}
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		UDynamicCommentList.Data item = list.get(position); // 获取评论项
		if (null == convertView) {
			holder = new ViewHolder();
			switch (item.getPid()) {
			case 0: // 父项
				convertView = layoutInflater.inflate(
						R.layout.item_square_dt_detail_comment, null);
				holder.name = (TextView) convertView
						.findViewById(R.id.tv_squary_dt_comment_nickname);
				holder.content = (TextView) convertView
						.findViewById(R.id.tv_squary_dt_comment_contents);
				holder.date = (TextView) convertView
						.findViewById(R.id.tv_squary_dt_comment_time);
				// holder.reply = (TextView) convertView
				// .findViewById(R.id.replyCount);
				holder.userface = (CircleImageView) convertView
						.findViewById(R.id.iv_squary_dt_comment_icon);

				break;
			case 1: // 子项
				convertView = layoutInflater.inflate(
						R.layout.item_dt_detail_comment_reply, null);
				holder.name = (TextView) convertView
						.findViewById(R.id.replyNickname);
				holder.content = (TextView) convertView
						.findViewById(R.id.replyContent);
				// holder.commentNickname = (TextView)
				// convertView.findViewById(R.id.commentNickname);
				break;
			default:
				break;
			}
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (imageLoader == null)
			imageLoader = ApplicationController.getInstance().getImageLoader();

		if (null != item) {
			switch (item.getPid()) {
			case 0:
				holder.name.setText(item.getNickname());
				holder.content.setText(item.getContents());
				holder.date.setText(item.getDate());
				// holder.reply.setText(item.getReplyCount());
				ImageListener listener = ImageLoader.getImageListener(
						holder.userface, R.drawable.user_null,
						R.drawable.user_null);
				imageLoader.get(item.getIcon(), listener);
				break;
			case 1:
				holder.name.setText(item.getNickname());
				holder.content.setText(item.getContents());
				// holder.date.setText(item.getPostTime());
				break;
			default:
				break;
			}
		}
		return convertView;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		switch (list.get(position).getPid()) {
		case 0:
			return 0;
		default:
			return 1;
		}
	}

	@Override
	public boolean isEnabled(int position) {
		return true;
	}

	private class ViewHolder {
		TextView id;
		TextView date;
		TextView name;
		TextView content;
		CircleImageView userface;
		TextView commentNickname;
	}
}
