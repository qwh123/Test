package com.hp.commonAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.hp.R;
import com.hp.application.ApplicationController;
import com.hp.utils.CommonUtils;
import com.hp.utils.volley.ShowImage;
import com.hp.widget.CircleImageView;

public class ViewHolder {
	private final SparseArray<View> mViews;
	private int mPosition;
	private View mConvertView;
	private ImageLoader imageLoader;
	private Context mContext;

	private ViewHolder(Context context, ViewGroup parent, int layoutId,
			int position) {
		this.mContext = context;
		this.mPosition = position;
		this.mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
				false);
		// setTag
		mConvertView.setTag(this);
	}

	/**
	 * 拿到一个ViewHolder对象
	 * 
	 * @param context
	 * @param convertView
	 * @param parent
	 * @param layoutId
	 * @param position
	 * @return
	 */
	public static ViewHolder get(Context context, View convertView,
			ViewGroup parent, int layoutId, int position) {
		if (convertView == null) {
			return new ViewHolder(context, parent, layoutId, position);
		}
		return (ViewHolder) convertView.getTag();
	}

	public View getConvertView() {
		return mConvertView;
	}

	/**
	 * 通过控件的Id获取对于的控件，如果没有则加入views
	 * 
	 * @param viewId
	 * @return
	 */
	public <T extends View> T getView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	/**
	 * 设置控件是否可见
	 * 
	 * @param viewId
	 * @param visibility
	 * @return
	 */
	public ViewHolder setVisibility(int viewId, int visibility) {
		getView(viewId).setVisibility(visibility);
		return this;
	}

	/**
	 * 为RatingBar设置显示星形数量
	 * 
	 * @param viewId
	 * @param rating
	 * @return
	 */
	public ViewHolder setRating(int viewId, float rating) {
		RatingBar view = getView(viewId);
		view.setRating(rating);
		return this;
	}

	/**
	 * 为TextView设置字符串
	 * 
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ViewHolder setText(int viewId, String text) {
		TextView view = getView(viewId);
		view.setText(text);
		return this;
	}

	/**
	 * 为Button设置字符串
	 * 
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ViewHolder setButtonText(int viewId, String text) {
		Button view = getView(viewId);
		view.setText(text);
		return this;
	}

	/**
	 * 为Button设置是否可见
	 * 
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ViewHolder setButtonVis(int viewId, boolean boo) {
		Button view = getView(viewId);
		if (boo)
			view.setVisibility(View.VISIBLE);
		else
			view.setVisibility(View.GONE);
		return this;
	}

	/**
	 * 为Button设置OnClickListener事件
	 * 
	 * @param viewId
	 * @param OnClickListener
	 * @return
	 */
	public ViewHolder setOnClickListener(int viewId,
			android.view.View.OnClickListener OnClickListener) {
		Button view = getView(viewId);
		view.setOnClickListener(OnClickListener);
		return this;
	}

	/**
	 * 为CheckBoxn设置点击事件
	 * 
	 * @param viewId
	 * @param OnClickListener
	 * @return
	 */
	public ViewHolder setOnCheckedChangeListener(int viewId,
			android.widget.CompoundButton.OnCheckedChangeListener listener) {
		CheckBox view = getView(viewId);
		view.setOnCheckedChangeListener(listener);
		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolder setImageResource(int viewId, int drawableId) {
		ImageView view = getView(viewId);
		view.setImageResource(drawableId);

		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
		ImageView view = getView(viewId);
		view.setImageBitmap(bm);
		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolder setImageByUrl(int viewId, String url) {
		ImageView view = getView(viewId);
		ShowImage mImage = new ShowImage(mContext);
		mImage.showImageByUrl(view, url, R.drawable.logo_homeparty);
		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolder setCircleImageByUrl(int viewId, String url) {
		CircleImageView view = getView(viewId);
		ShowImage mImage = new ShowImage(mContext);
		mImage.showImageByUrl(view, url, R.drawable.fail_image);
		return this;
	}

	/**
	 * 为ImageView设置图片及其大小
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolder setImageAndSizeByUrl(int viewId, String url, int width,
			int height) {
		ImageView view = getView(viewId);
		ShowImage mImage = new ShowImage(mContext);
		mImage.showImageByUrlSetSize(view, url, width, height,
				R.drawable.fail_image);
		return this;
	}

	public int getPosition() {
		return mPosition;
	}

}
