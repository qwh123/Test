package com.hp.activity;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.hp.R;
import com.hp.utils.FastBlur;
import com.hp.utils.KickBackAnimator;

public class MoreWindow extends PopupWindow implements OnClickListener {

	private String TAG = MoreWindow.class.getSimpleName();
	Activity mContext;
	private int mWidth;
	private int mHeight;
	private int statusBarHeight;
	private Bitmap mBitmap = null;
	private Bitmap overlay = null;

	private Handler mHandler = new Handler();
	private Intent intent;

	public MoreWindow(Activity context) {
		this.mContext = context;
	}

	public void init() {
		Rect frame = new Rect();
		mContext.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		statusBarHeight = frame.top;
		DisplayMetrics metrics = new DisplayMetrics();
		mContext.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		mWidth = metrics.widthPixels;
		mHeight = metrics.heightPixels;

		setWidth(mWidth);
		setHeight(mHeight);
	}

	private Bitmap blur() {
		if (null != overlay) {
			return overlay;
		}
		long startMs = System.currentTimeMillis();
		View view = mContext.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache(true);
		mBitmap = view.getDrawingCache();

		float scaleFactor = 8;// Í¼Æ¬Ëõ·Å±ÈÀý£»
		float radius = 10;// Ä£ºý³Ì¶È
		int width = mBitmap.getWidth();
		int height = mBitmap.getHeight();

		overlay = Bitmap.createBitmap((int) (width / scaleFactor),
				(int) (height / scaleFactor), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(overlay);
		canvas.scale(1 / scaleFactor, 1 / scaleFactor);
		Paint paint = new Paint();
		paint.setFlags(Paint.FILTER_BITMAP_FLAG);
		canvas.drawBitmap(mBitmap, 0, 0, paint);

		overlay = FastBlur.doBlur(overlay, (int) radius, true);
		Log.i(TAG, "blur time is:" + (System.currentTimeMillis() - startMs));
		view.setDrawingCacheEnabled(false);// 一定要设置，否则下次进入时画布缓存还在，图像不能及时更新
		return overlay;
	}

	private Animation showAnimation1(final View view, int fromY, int toY) {
		AnimationSet set = new AnimationSet(true);
		TranslateAnimation go = new TranslateAnimation(0, 0, fromY, toY);
		go.setDuration(300);
		TranslateAnimation go1 = new TranslateAnimation(0, 0, -10, 2);
		go1.setDuration(100);
		go1.setStartOffset(250);
		set.addAnimation(go1);
		set.addAnimation(go);

		set.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationEnd(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationStart(Animation animation) {

			}

		});
		return set;
	}

	public void showMoreWindow(View anchor) {
		final RelativeLayout layout = (RelativeLayout) LayoutInflater.from(
				mContext).inflate(R.layout.center_more_window, null);
		setContentView(layout);
		((Button) layout.findViewById(R.id.btn_activity))
				.setOnClickListener(this);
		((Button) layout.findViewById(R.id.btn_publish))
				.setOnClickListener(this);
		ImageView close = (ImageView) layout
				.findViewById(R.id.center_music_window_close);

		close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isShowing()) {
					closeAnimation(layout);
				}
			}

		});

		showAnimation(layout);
		setBackgroundDrawable(new BitmapDrawable(mContext.getResources(),
				blur()));
		setOutsideTouchable(true);
		setFocusable(true);
		showAtLocation(anchor, Gravity.BOTTOM, 0, statusBarHeight);
	}

	private void showAnimation(ViewGroup layout) {
		for (int i = 0; i < layout.getChildCount(); i++) {
			final View child = layout.getChildAt(i);
			if (child.getId() == R.id.center_music_window_close) {
				continue;
			}
			mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					child.setVisibility(View.VISIBLE);
					ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child,
							"translationY", 600, 0);
					fadeAnim.setDuration(300);
					KickBackAnimator kickAnimator = new KickBackAnimator();
					kickAnimator.setDuration(150);
					fadeAnim.setEvaluator(kickAnimator);
					fadeAnim.start();
				}
			}, i * 50);
		}

	}

	private void closeAnimation(ViewGroup layout) {
		for (int i = 0; i < layout.getChildCount(); i++) {
			final View child = layout.getChildAt(i);
			child.setOnClickListener(this);
			mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					child.setVisibility(View.VISIBLE);
					ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child,
							"translationY", 0, 600);
					fadeAnim.setDuration(200);
					KickBackAnimator kickAnimator = new KickBackAnimator();
					kickAnimator.setDuration(100);
					fadeAnim.setEvaluator(kickAnimator);
					fadeAnim.start();
					fadeAnim.addListener(new AnimatorListener() {

						@Override
						public void onAnimationStart(Animator animation) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onAnimationRepeat(Animator animation) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onAnimationEnd(Animator animation) {
							child.setVisibility(View.INVISIBLE);
						}

						@Override
						public void onAnimationCancel(Animator animation) {
							// TODO Auto-generated method stub

						}
					});
				}
			}, (layout.getChildCount() - i - 1) * 30);

			if (child.getId() == R.id.center_music_window_close) {
				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						dismiss();
					}
				}, (layout.getChildCount() - i) * 30 + 80);
			}
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_publish:
			intent = new Intent(mContext, PublishDTActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("title", "发布动态");
			bundle.putString("rightText", "发布");
			intent.putExtras(bundle);
			break;
		case R.id.btn_activity:
			intent = new Intent(mContext, PublishHDActivity.class);
			break;

		default:
			break;
		}
		dismiss();
		mContext.startActivity(intent);
	}

	public void destroy() {
		if (null != overlay) {
			overlay.recycle();
			overlay = null;
			System.gc();
		}
		if (null != mBitmap) {
			mBitmap.recycle();
			mBitmap = null;
			System.gc();
		}
	}

}