package com.hp.utils;

import java.io.File;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.widget.Toast;

import com.hp.bean.PUploadImageToken;
import com.hp.dao.PUploadImageTokenDao;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

/**
 * 上传图像至七牛云
 * 
 * @author qwh
 * 
 */
public class UploadImageToken {
	private Context mContext;
	private String urlpath;
	private String act;

	PUploadImageToken.Data mToken;
	private ProgressDialog progress;
	private Handler mHandler, mHandler2;

	/**
	 * 
	 * @param mContext
	 *            上下文
	 * @param mHandler2
	 * @param urlpath图片路径
	 * @param act
	 *            图片上传方式商品评论 act=1 发布活动 act=2 发布动态 act=3 上传头像 act=4
	 */
	public UploadImageToken(Context mContext, String urlpath,
			Handler mHandler2, String act) {
		this.mContext = mContext;
		this.urlpath = urlpath;
		this.act = act;
		this.mHandler2 = mHandler2;
		UpLoad();
	}

	public void UpLoad() {
		progress = new ProgressDialog(mContext);
		progress.setMessage("图片上传中...");
		progress.show();
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what == hpCantant.LABLE_PUPLOADIMAGETOKEN) {
					Bundle bundle = msg.getData();
					PUploadImageTokenDao pTokenDao = new PUploadImageTokenDao();
					mToken = pTokenDao.getQiNiuToken(bundle
							.getString(hpCantant.GETDATA));
					UploadManager();

				} else {
					Toast.makeText(mContext, "获取TOKEN失败", 1).show();
				}
			};

		};
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("act", act);
		CommonUtils.getData(mHandler, map, hpCantant.PUPLOADIMAGETOKEN_URL,
				hpCantant.LABLE_PUPLOADIMAGETOKEN);

	}

	/**
	 * 保存用户修改的数据
	 */
	private void UploadManager() {
		UploadManager uploadManager = new UploadManager();
		File file = new File(urlpath);
		String token = mToken.getToken();
		uploadManager.put(file, mToken.getName(), token,
				new UpCompletionHandler() {
					@Override
					public void complete(String key, ResponseInfo info,
							JSONObject res) {
						// res 包含hash、key等信息，具体字段取决于上传策略的设置。
						progress.dismiss();
						if (info.statusCode == 200) {
							// Toast.makeText(mContext, "完成上传", 0).show();
							Message message = new Message();
							message.obj = mToken.getName();
							message.what = 999;
							mHandler2.sendMessage(message);
						} else {
							Toast.makeText(mContext, "上传失败", 0).show();
						}
					}
				}, null);

	}
}
