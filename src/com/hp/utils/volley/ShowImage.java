package com.hp.utils.volley;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.hp.application.ApplicationController;

public class ShowImage {
	private ImageLoader mImageLoader;
	private ImageListener listener = null;

	public ShowImage(Context mContext) {
		if (mImageLoader == null)
			mImageLoader = ApplicationController.getInstance().getImageLoader();
	}

	/**
	 * @param imageView
	 * @param strImgUrl
	 * @param defaultImageResId
	 */
	public void showImageByUrl(ImageView imageView, String strImgUrl,
			int defaultImageResId) {
		listener = ImageLoader
				.getImageListener(imageView, defaultImageResId, 0);
		mImageLoader.get(strImgUrl, listener);

	}

	public void showImageByUrlSetSize(ImageView imageView, String strImgUrl,
			int maxWidth, int maxHeight, int defaultImageResId) {
		listener = ImageLoader
				.getImageListener(imageView, defaultImageResId, 0);
		mImageLoader.get(strImgUrl, listener, maxHeight, maxHeight);

	}

}
