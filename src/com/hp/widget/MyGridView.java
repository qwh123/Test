package com.hp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * 自定义gridview，解决ListView中嵌套gridview显示不正常的问题（1行半）
 * 
 */
public class MyGridView extends GridView {
	public MyGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyGridView(Context context) {
		super(context);
	}

	public MyGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int heightSpec;

		if (getLayoutParams().height == LayoutParams.WRAP_CONTENT) {
			heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
					MeasureSpec.AT_MOST);
		} else {
			heightSpec = heightMeasureSpec;
		}

		super.onMeasure(widthMeasureSpec, heightSpec);

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_MOVE) {

			return true; // 禁止GridView滑动

		}

		return super.dispatchTouchEvent(ev);
	}
}