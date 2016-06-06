package com.hp.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hp.R;
import com.hp.widget.TopBar;
import com.hp.widget.TopBar.topBarClickListener;

/**
 * Created by Joyin on 2015/9/22.
 */
public class WebActivity extends Activity {

	private WebView webview_info;
	private ProgressBar progressBar;
	private String url = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_info);
		TopBar tBar = (TopBar) findViewById(R.id.topbar_web);
		tBar.setTitleText("");
		tBar.setRightIsVisable(false);
		tBar.setOnTopBarClickListener(new topBarClickListener() {
			@Override
			public void rightClick() {
				Toast.makeText(WebActivity.this, "click", 1).show();
			}

			@Override
			public void leftClick() {
				finish();
			}
		});
		initView();
	}

	void initView() {
		url = getIntent().getExtras().getString("url");
		webview_info = (WebView) findViewById(R.id.webview_info);
		progressBar = (ProgressBar) findViewById(R.id.progress_bar);
		initWebView(url);
	}

	private void initWebView(String url) {

		webview_info.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress == 100) {
					progressBar.setVisibility(View.GONE);
				} else {
					progressBar.setProgress(newProgress);
					if (progressBar.getVisibility() == View.GONE) {
						progressBar.setVisibility(View.VISIBLE);
					}
				}
				super.onProgressChanged(view, newProgress);
			}

			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
			}
		});
		webview_info.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onReceivedSslError(WebView view,
					SslErrorHandler handler, SslError error) {
				handler.proceed();
			}
		});
		setDefault();
		webview_info.loadUrl(url);
	}

	@SuppressLint("NewApi")
	private void setDefault() {
		WebSettings webSettings = webview_info.getSettings();
		webSettings.setJavaScriptEnabled(true);

		// User settings

		webSettings.setJavaScriptEnabled(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setUseWideViewPort(true);// 关键点

		webSettings
				.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

		webSettings.setDisplayZoomControls(false);
		webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
		webSettings.setAllowFileAccess(true); // 允许访问文件
		webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
		webSettings.setSupportZoom(true); // 支持缩放

		webSettings.setLoadWithOverviewMode(true);

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int mDensity = metrics.densityDpi;
		if (mDensity == 240) {
			webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
		} else if (mDensity == 160) {
			webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
		} else if (mDensity == 120) {
			webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
		} else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
			webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
		} else if (mDensity == DisplayMetrics.DENSITY_TV) {
			webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
		} else {
			webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
		}

		/**
		 * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：
		 * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
		 */
		webSettings
				.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
	}

}
