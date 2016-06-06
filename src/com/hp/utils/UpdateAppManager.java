package com.hp.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.hp.R;

public class UpdateAppManager {
	// 文件分隔符
	private static final String FILE_SEPARATOR = "/";
	// 外存sdcard存放路径
	private static final String FILE_PATH = Environment
			.getExternalStorageDirectory()
			+ FILE_SEPARATOR
			+ "Update"
			+ FILE_SEPARATOR;
	// 下载应用存放全路径
	private static final String FILE_NAME = FILE_PATH + "hp.apk";
	// 更新应用版本标记
	private static final int UPDARE_TOKEN = 0x29;
	// 准备安装新版本应用标记
	private static final int INSTALL_TOKEN = 0x31;

	private Context context;
	private String message = null;
	private String code = null;
	// 下载应用的对话框
	private Dialog dialog;
	// 下载应用的进度条
	private ProgressBar progressBar;
	// 进度条的当前刻度值
	private int curProgress;
	// 用户是否取消下载
	private boolean isCancel;

	public UpdateAppManager(Context context) {
		this.context = context;
	}

	private final Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case hpCantant.LABLE_UUpdate:
				Bundle bundle = msg.getData();
				try {
					JSONObject js = new JSONObject(
							bundle.getString(hpCantant.GETDATA));
					message = js.getJSONObject("data").getString("summary");
					code = js.getJSONObject("data").getString("code");
					hpCantant.UPDATE_URL = js.getJSONObject("data").getString(
							"url");

					Log.i("code_VersionCode-->", code + "_" + getVersionCode());
					if (js.getString("code").equals("100")
							&& !js.getString("data").isEmpty()) {
						if (!code.equals(getVersionCode())) {
							showNoticeDialog(code);
						} else
							return;
					} else if (js.getString("code").equals("102")) {
						showNoticeDialog(code);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case UPDARE_TOKEN:
				progressBar.setProgress(curProgress);
				break;

			case INSTALL_TOKEN:
				installApp();
				break;
			}
		}
	};

	/**
	 * 检测应用更新信息
	 */
	public void checkUpdateInfo() {
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("code", "5");
		CommonUtils.getData(handler, map, hpCantant.UUpdate_URL,
				hpCantant.LABLE_UUpdate);
	}

	/**
	 * 显示提示更新对话框
	 * 
	 * @param code2
	 */
	private void showNoticeDialog(final String code2) {
		new AlertDialog.Builder(context).setTitle("软件版本更新").setMessage(message)
				.setPositiveButton("下载", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						showDownloadDialog();
					}
				}).setNegativeButton("以后再说", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						if (code2.equals("102")) {
							System.exit(0);
						}
					}
				}).create().show();
	}

	/**
	 * 显示下载进度对话框
	 */
	private void showDownloadDialog() {
		View view = LayoutInflater.from(context).inflate(
				R.layout.update_progress, null);
		progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("软件版本更新");
		builder.setView(view);
		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				isCancel = true;
			}
		});
		dialog = builder.create();
		dialog.show();
		downloadApp();
	}

	/**
	 * 下载新版本应用
	 */
	private void downloadApp() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				URL url = null;
				InputStream in = null;
				FileOutputStream out = null;
				HttpURLConnection conn = null;
				try {
					url = new URL(hpCantant.UPDATE_URL);
					conn = (HttpURLConnection) url.openConnection();
					conn.connect();
					long fileLength = conn.getContentLength();
					in = conn.getInputStream();
					File filePath = new File(FILE_PATH);
					if (!filePath.exists()) {
						filePath.mkdir();
					}
					out = new FileOutputStream(new File(FILE_NAME));
					byte[] buffer = new byte[1024];
					int len = 0;
					long readedLength = 0l;
					while ((len = in.read(buffer)) != -1) {
						// 用户点击“取消”按钮，下载中断
						if (isCancel) {
							break;
						}
						out.write(buffer, 0, len);
						readedLength += len;
						curProgress = (int) (((float) readedLength / fileLength) * 100);
						handler.sendEmptyMessage(UPDARE_TOKEN);
						if (readedLength >= fileLength) {
							dialog.dismiss();
							// 下载完毕，通知安装
							handler.sendEmptyMessage(INSTALL_TOKEN);
							break;
						}
					}
					out.flush();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (out != null) {
						try {
							out.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (in != null) {
						try {
							in.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (conn != null) {
						conn.disconnect();
					}
				}
			}
		}).start();
	}

	/**
	 * 安装新版本应用
	 */
	private void installApp() {
		File appFile = new File(FILE_NAME);
		if (!appFile.exists()) {
			return;
		}
		// 跳转到新版本应用安装页面
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + appFile.toString()),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	/**
	 * 获取版本号
	 * 
	 * @param context
	 * @return
	 */
	private int getVersionCode() {
		int versionCode = 0;
		try {
			// 获取软件版本号
			versionCode = context.getPackageManager().getPackageInfo("com.hp",
					1).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

}
