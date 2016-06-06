package com.hp.widget;

import com.hp.R;

import android.R.attr;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TopBar extends RelativeLayout {
	private topBarClickListener listener;

	public interface topBarClickListener {
		public void leftClick();

		public void rightClick();
	}

	public void setOnTopBarClickListener(topBarClickListener listener) {
		this.listener = listener;
	}

	protected RelativeLayout leftLayout;
	protected ImageView leftImage;
	protected RelativeLayout rightLayout;
	protected ImageView rightImage;
	protected TextView titleView;
	protected RelativeLayout titleLayout;

	public TopBar(Context context) {
		super(context);
		init(context, null);
	}

	public TopBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	public TopBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		LayoutInflater.from(context).inflate(R.layout.top_bar, this);
		leftLayout = (RelativeLayout) findViewById(R.id.rl_topbar_left);
		leftImage = (ImageView) findViewById(R.id.iv_topbar_left);
		rightLayout = (RelativeLayout) findViewById(R.id.rl_topbar_right);
		rightImage = (ImageView) findViewById(R.id.iv_toptar_right);
		titleView = (TextView) findViewById(R.id.tv_topbar_title);
		titleLayout = (RelativeLayout) findViewById(R.id.topbar);

		if (attrs != null) {
			TypedArray ta = context.obtainStyledAttributes(attrs,
					R.styleable.TopBar);
			String title = ta.getString(R.styleable.TopBar_titleName);
			titleView.setText(title);
			int color = ta.getColor(R.styleable.TopBar_titleColor, 0);
			titleView.setTextColor(getResources().getColor(R.color.white));
			Drawable leftDrawable = ta
					.getDrawable(R.styleable.TopBar_leftBackground);
			if (null != leftDrawable) {
				leftImage.setImageDrawable(leftDrawable);
			}
			Drawable rightDrawable = ta
					.getDrawable(R.styleable.TopBar_rightBackground);
			if (null != rightDrawable) {
				rightImage.setImageDrawable(rightDrawable);
			}

			Drawable background = ta
					.getDrawable(R.styleable.TopBar_topbarBackground);
			if (null != background) {
				titleLayout.setBackgroundDrawable(background);
			}

			ta.recycle();
		}

		leftLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				listener.leftClick();
			}
		});

		rightLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				listener.rightClick();
			}
		});
	}

	/**
	 * 设置背景颜色
	 * 
	 * @param drawable
	 */
	public void setBackground(int color) {
		titleLayout.setBackgroundColor(getResources().getColor(color));
	}

	/**
	 * 设置左按钮是否可见 true为可见 flase为不可见（GONE）
	 * 
	 * @param flag
	 */
	public void setLeftIsVisable(boolean flag) {
		if (flag) {
			leftImage.setVisibility(View.VISIBLE);
		} else {
			leftImage.setVisibility(View.GONE);
		}

	}

	/**
	 * 设置右按钮是否可见 true为可见 flase为不可见（GONE）
	 * 
	 * @param flag
	 */
	public void setRightIsVisable(boolean flag) {
		if (flag) {
			rightImage.setVisibility(View.VISIBLE);
		} else {
			rightImage.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置左按钮背景
	 * 
	 * @param drawable
	 */
	public void setLefttImageResource(int resId) {
		leftImage.setImageResource(resId);
	}

	/**
	 * 设置右按钮背景
	 * 
	 * @param drawable
	 */
	public void setRighttImageResource(int resId) {
		rightImage.setImageResource(resId);
	}

	/**
	 * 设置标题
	 */
	public void setTitleText(String text) {
		titleView.setText(text);
	}

	/**
	 * 设置标题
	 */
	public void setTitleTextColor(int color) {
		titleView.setTextColor(color);
	}

}
