package com.hp.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.hp.R;
import com.hp.application.ApplicationController;
import com.hp.utils.CommonUtils;
import com.hp.utils.FileUtil;
import com.hp.utils.UploadImageToken;
import com.hp.utils.hpCantant;
import com.hp.widget.SelectPicPop;
import com.hp.widget.SelectTimePop;
import com.hp.widget.TimerAlertDialog;
import com.hp.widget.TopBar;
import com.hp.widget.TopBar.topBarClickListener;

public class PublishHDActivity extends Activity implements OnClickListener {
	private int countid;
	private int classid = -1;
	private String begintime = null;
	private String lat = null;
	private String lng = null;
	private String num = null;
	private String fee = null;
	private String endtime = null;
	private String image = null;
	private String summary = null;
	private String telneed = "false";
	private String title = null;
	private String adress = null;

	private SelectPicPop menuWindow;
	private SelectTimePop mTimePop;

	private ArrayMap<String, String> map;
	private Handler mHandler = new Handler() {
		private JSONObject js;

		public void handleMessage(android.os.Message msg) {
			if (msg.what == hpCantant.LABLE_UACTIVECREATE) {
				Bundle bundle = msg.getData();
				Log.i("uUser:", bundle.getString(hpCantant.GETDATA));
				try {
					js = new JSONObject(bundle.getString(hpCantant.GETDATA));
					if (js.getString("code").equals(hpCantant.SUCCESS_CODE)) {
						Toast.makeText(PublishHDActivity.this, "发布成功",
								Toast.LENGTH_SHORT).show();
						PublishHDActivity.this.finish();
					} else {
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else if (msg.what == 999) {
				image = (String) msg.obj;
				Log.i("---------------", image);
				if (image.isEmpty()) {
					toSetText("获取照片出现未知错误");
					return;
				} else {
					map.put("image", image);
					map.put("countid", countid + "");
					map.put("classid", classid + "");
					map.put("begintime", begintime);
					map.put("address", adress);
					map.put("lat", lat);
					map.put("lng", lng);
					map.put("num", num);
					map.put("fee", fee);
					map.put("endtime", endtime);
					map.put("summary", summary);
					map.put("telneed", telneed);
					map.put("title", title);
					Log.i(getPackageName(), map.toString());

				}
				CommonUtils.getData(mHandler, map, hpCantant.UACTIVECREATE_URL,
						hpCantant.LABLE_UACTIVECREATE);

			}

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_publish_activity);
		initView();
		TopBar tBar = (TopBar) findViewById(R.id.topbar_pub_aty);
		tBar.setTitleText("发起");
		tBar.setRighttImageResource(R.drawable.icon_right_tj);
		tBar.setOnTopBarClickListener(new topBarClickListener() {
			@Override
			public void rightClick() {
				insertDate();
				map = new ArrayMap<String, String>();
				if (countid == 0) {
					toSetText("请先登录帐号");
					startActivity(new Intent(PublishHDActivity.this,
							UserLoginActivity.class));
				} else if (title.isEmpty()) {
					toSetText("主题不能为空");
				} else if (classid == -1) {
					toSetText("请选择活动类型");
				} else if (begintime.isEmpty()) {
					toSetText("请选择开始时间");
				} else if (endtime.isEmpty()) {
					toSetText("请选择结束时间");
				} else if (num.isEmpty()) {
					toSetText("请填写人数");
				} else if (adress.isEmpty()) {
					toSetText("请选择地点");
				} else if (fee.isEmpty()) {
					toSetText("请填写费用");
				} else if (summary.isEmpty()) {
					toSetText("请简要说明一下");
				} else if (urlpath.equals("") || urlpath.isEmpty()) {
					toSetText("必须添加一张照片");
				} else {
					UploadImageToken upload = new UploadImageToken(
							PublishHDActivity.this, urlpath, mHandler, "2");
				}

			}

			@Override
			public void leftClick() {
				finish();
			}
		});

	}

	/**
	 * 为上传参数填写值
	 */
	private void insertDate() {
		countid = ApplicationController.getInstance().getUser()
				.getInt("countid", 0);
		// 活动主题
		title = ((EditText) findViewById(R.id.edt_aty_title)).getText()
				.toString();
		begintime = tvBeginTime.getText().toString();
		endtime = tvEndTime.getText().toString();
		summary = ((EditText) findViewById(R.id.edt_aty_summary)).getText()
				.toString();
		num = ((EditText) findViewById(R.id.edt_aty_num)).getText().toString();
		fee = ((EditText) findViewById(R.id.edt_aty_fee)).getText().toString();
		((Switch) findViewById(R.id.switch_aty_telneed))
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton arg0,
							boolean isChecked) {
						if (isChecked)
							telneed = "true";
						else
							telneed = "false";
					}
				});
	}

	private TextView tvBeginTime;
	private TextView tvEndTime;
	private ImageView ivAddImage;
	private TextView tvAdress;

	/**
	 * 初始化UI组件
	 */
	private void initView() {
		// 活动类型
		((RelativeLayout) findViewById(R.id.rl_aty_class))
				.setOnClickListener(this);
		// 开始时间
		((RelativeLayout) findViewById(R.id.rl_aty_begintime))
				.setOnClickListener(this);
		tvBeginTime = (TextView) findViewById(R.id.tv_aty_begintime);
		// 结束时间
		((RelativeLayout) findViewById(R.id.rl_aty_endtime))
				.setOnClickListener(this);
		tvEndTime = (TextView) findViewById(R.id.tv_aty_endtime);
		ivAddImage = (ImageView) findViewById(R.id.iv_aty_image);
		tvAdress = (TextView) findViewById(R.id.tv_aty_adress);
		// 选择地点
		((RelativeLayout) findViewById(R.id.rl_order_lable))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO跳转至百度地图
						Intent intent = new Intent(PublishHDActivity.this,
								BaiduMapActivity.class);
						startActivityForResult(intent, 999);

					}

				});
		ivAddImage.setOnClickListener(this);
	}

	private void setText(int id, String text) {
		TextView view = (TextView) findViewById(id);
		view.setText(text);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.iv_aty_image:
			String[] titles = { "拍照", "从相册选择", "取消" };
			// 更换头像点击事件
			menuWindow = new SelectPicPop(PublishHDActivity.this, titles,
					addImage);
			menuWindow.showAtLocation(findViewById(R.id.activity_layout),
					Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
			break;
		case R.id.rl_aty_class:
			String[] style = { "轰趴管", "活动管", "取消" };
			// 更换头像点击事件
			menuWindow = new SelectPicPop(PublishHDActivity.this, style,
					chooseClass);
			menuWindow.showAtLocation(findViewById(R.id.activity_layout),
					Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
			break;
		case R.id.rl_aty_begintime:
			new TimerAlertDialog(this, tvBeginTime);
			// mTimePop=new SelectTimePop(this, tvBeginTime);
			// mTimePop.showAtLocation(findViewById(R.id.activity_layout),
			// Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
			break;
		case R.id.rl_aty_endtime:
			new TimerAlertDialog(this, tvEndTime);

			break;

		default:
			break;
		}
	}

	/**
	 * 选择活动类型
	 */
	private OnClickListener chooseClass = new OnClickListener() {
		@Override
		public void onClick(View v) {
			menuWindow.dismiss();
			switch (v.getId()) {
			// 男
			case R.id.takePhotoBtn:
				setText(R.id.tv_aty_class, "轰趴管");
				classid = 1;
				break;
			// 女
			case R.id.pickPhotoBtn:
				setText(R.id.tv_aty_class, "活动管");
				classid = 2;
				break;
			default:
				break;
			}
		}
	};

	private static final String myfilePath = Environment
			.getExternalStorageDirectory().getPath() + "/" + "active.jpg";// 头像文件名称
	private String urlpath; // 图片本地路径
	private static final int REQUESTCODE_PICK = 0; // 相册选图标记
	private static final int REQUESTCODE_TAKE = 1; // 相机拍照标记
	/**
	 * 添加一张照片
	 */
	private OnClickListener addImage = new OnClickListener() {
		@Override
		public void onClick(View v) {
			menuWindow.dismiss();
			switch (v.getId()) {
			// 拍照
			case R.id.takePhotoBtn:
				Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				// 下面这句指定调用相机拍照后的照片存储的路径
				takeIntent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(new File(myfilePath)));
				startActivityForResult(takeIntent, REQUESTCODE_TAKE);
				break;
			// 相册选择图片
			case R.id.pickPhotoBtn:
				Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
				// 如果朋友们要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
				pickIntent
						.setDataAndType(
								MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
								"image/*");
				startActivityForResult(pickIntent, REQUESTCODE_PICK);
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
		case REQUESTCODE_PICK:// 直接从相册获取
			ContentResolver resolver = getContentResolver();
			// 照片的原始资源地址
			try {
				Uri originalUri = data.getData();
				// 使用ContentProvider通过URI获取原始图片
				Bitmap photo = MediaStore.Images.Media.getBitmap(resolver,
						originalUri);

				String[] proj = { MediaStore.Images.Media.DATA };
				// 好像是android多媒体数据库的封装接口，具体的看Android文档
				Cursor cursor = managedQuery(originalUri, proj, null, null,
						null);
				// 按我个人理解 这个是获得用户选择的图片的索引值
				int column_index = cursor
						.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				// 将光标移至开头 ，这个很重要，不小心很容易引起越界
				cursor.moveToFirst();
				// 最后根据索引值获取图片路径
				urlpath = cursor.getString(column_index);
				ivAddImage.setImageBitmap(photo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case REQUESTCODE_TAKE:// 调用相机拍照
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(myfilePath);
				Bitmap bitmap = BitmapFactory.decodeStream(fis);
				urlpath = FileUtil.saveFile(PublishHDActivity.this, myfilePath,
						bitmap);
				ivAddImage.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					fis.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
			break;
		case 999:
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				adress = bundle.getString("adress");
				lat = bundle.getString("lat");
				lng = bundle.getString("lng");
				tvAdress.setText(adress);
				Log.i(getPackageName(), adress);
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void toSetText(String text) {
		Toast.makeText(PublishHDActivity.this, text, Toast.LENGTH_SHORT).show();
	}
}
