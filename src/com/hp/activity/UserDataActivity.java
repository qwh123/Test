package com.hp.activity;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.hp.R;
import com.hp.application.ApplicationController;
import com.hp.bean.PUploadImageToken;
import com.hp.utils.CommonUtils;
import com.hp.utils.FileUtil;
import com.hp.utils.UploadImageToken;
import com.hp.utils.hpCantant;
import com.hp.utils.volley.VolleyUtils;
import com.hp.utils.volley.VolleyUtilsImpl;
import com.hp.widget.CustomDialog;
import com.hp.widget.SelectPicPop;
import com.hp.widget.TopBar;
import com.hp.widget.TopBar.topBarClickListener;

public class UserDataActivity extends Activity implements OnClickListener {
	public static String CHANGE_PSW = "修改密码";
	// 判断是否有修改个人资料
	private boolean IsAlert = false;
	private Dialog dialog;
	// --------------頭像修改s
	private SelectPicPop menuWindow; // 自定义的头像编辑弹出框
	// 上传服务器的路径【一般不硬编码到程序中】
	private static final String IMAGE_FILE_NAME = "userIcon.jpg";// 头像文件名称
	private String urlpath = null; // 图片本地路径
	private static final int REQUESTCODE_PICK = 0; // 相册选图标记
	private static final int REQUESTCODE_TAKE = 1; // 相机拍照标记
	private static final int REQUESTCODE_CUTTING = 2; // 图片裁切标记

	// 出生日，星座
	private static final int REQUESTCODE_BIRTHDAY_OR_STAR = 3;
	// 个人简介
	private static final int REQUESTCODE_PERSIGN = 4;
	// --------------头像修改n
	PUploadImageToken.Data mToken;
	/**
	 * 头像，昵称，性别，个人简介，地点，出生日，星座，学校，工作，情感状态 修改密码 RelativeLayout
	 */
	private RelativeLayout rlChangehead, rlNickName, rlSex, rlPer, rlAdress,
			rlBorn, rlStar, rlSchool, rlJob, rlState, rlAlertPsw;
	// 头像
	private ImageView ivHead;
	/**
	 * 昵称，性别，个人简介，地点，出生日，学校，工作，情感状态等的TextView
	 */
	private TextView tvNick, tvSex, tvAdress, tvPer, tvBorn, tvStar, tvSchool,
			tvJob, tvState;
	/**
	 * 头像，昵称，性别，个人简介，地点，出生日，星座id，学校，工作，情感状态等String
	 */
	private String icon = null, nickname = null, sex = null, industry = null,
			job = null, school = null, borndate = null, btypeid = null,
			persign = null;
	private ImageLoader imageLoader;
	private Handler mHandler;

	SharedPreferences uInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_user_update);
		uInfo = ApplicationController.getInstance().getUser();
		initView();
	}

	/**
	 * 为各组件赋值
	 */
	private void initUI() {
		if (imageLoader == null)
			imageLoader = ApplicationController.getInstance().getImageLoader();
		ImageListener listener = ImageLoader.getImageListener(ivHead,
				R.drawable.user_null, R.drawable.user_null);
		imageLoader.get(uInfo.getString("icon", ""), listener);
		tvNick.setHint(uInfo.getString("nickname", ""));
		if (uInfo.getInt("sex", -1) == 0) {
			tvSex.setHint("保密");
		} else if (uInfo.getInt("sex", -1) == 1) {
			tvSex.setHint("男");
		} else {
			tvSex.setHint("女");
		}
		tvAdress.setHint(uInfo.getString("industry", ""));
		tvPer.setHint(uInfo.getString("persign", ""));
		tvBorn.setHint(uInfo.getString("borndate", ""));
		// tvStar.setHint(uInfo.getString("industry", ""));
		tvSchool.setHint(uInfo.getString("school", ""));
		tvJob.setHint(uInfo.getString("job", ""));
		// tvState.setHint(uInfo.getString("industry", ""));
	}

	/**
	 * 获取各组件
	 */
	private void initView() {

		TopBar tBar = (TopBar) findViewById(R.id.topbar_data);
		tBar.setTitleText("个人信息");
		tBar.setRighttImageResource(R.drawable.icon_right_complete);
		tBar.setOnTopBarClickListener(new topBarClickListener() {
			@Override
			public void rightClick() {
				SaveUserInfo();
			}

			@Override
			public void leftClick() {
				if (IsAlert)
					AlertDialog();// 弹出提示框
				else
					UserDataActivity.this.finish();
			}
		});
		rlChangehead = (RelativeLayout) findViewById(R.id.rl_userChangeHead);
		rlNickName = (RelativeLayout) findViewById(R.id.rl_userChange_nick);
		rlSex = (RelativeLayout) findViewById(R.id.rl_userChange_sex);
		rlAdress = (RelativeLayout) findViewById(R.id.rl_userChange_adress);
		rlPer = (RelativeLayout) findViewById(R.id.rl_userChange_intro);
		rlBorn = (RelativeLayout) findViewById(R.id.rl_userChange_birthday);
		rlStar = (RelativeLayout) findViewById(R.id.rl_userChange_star);
		rlSchool = (RelativeLayout) findViewById(R.id.rl_userChange_school);
		rlJob = (RelativeLayout) findViewById(R.id.rl_userChange_work);
		rlState = (RelativeLayout) findViewById(R.id.rl_userChange_state);
		rlAlertPsw = (RelativeLayout) findViewById(R.id.rl_userChange_psw);

		ivHead = (ImageView) findViewById(R.id.iv_user_updateIcon);
		tvNick = (TextView) findViewById(R.id.tv_nick);
		tvSex = (TextView) findViewById(R.id.tv_sex);
		tvAdress = (TextView) findViewById(R.id.tv_adress);
		tvPer = (TextView) findViewById(R.id.tv_persign);
		tvBorn = (TextView) findViewById(R.id.tv_birthday);
		tvStar = (TextView) findViewById(R.id.tv_star);
		tvSchool = (TextView) findViewById(R.id.tv_school);
		tvJob = (TextView) findViewById(R.id.tv_job);
		tvState = (TextView) findViewById(R.id.tv_state);

		initUI();

		rlChangehead.setOnClickListener(this);
		rlNickName.setOnClickListener(this);
		rlSex.setOnClickListener(this);
		rlPer.setOnClickListener(this);
		rlAdress.setOnClickListener(this);
		rlBorn.setOnClickListener(this);
		rlStar.setOnClickListener(this);
		rlSchool.setOnClickListener(this);
		rlJob.setOnClickListener(this);
		rlState.setOnClickListener(this);
		rlAlertPsw.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_userChangeHead:
			String[] titles = { "拍照", "从相册选择", "取消" };
			// 更换头像点击事件
			menuWindow = new SelectPicPop(this, titles, ChangeHeadOnClick);
			menuWindow.showAtLocation(findViewById(R.id.mainLayout),
					Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
			break;

		case R.id.rl_userChange_nick:
			setNick();
			break;
		case R.id.rl_userChange_sex:
			String[] sexs = { "男", "女", "取消" };
			// 更换头像点击事件
			menuWindow = new SelectPicPop(this, sexs, SelectSexOnClick);
			menuWindow.showAtLocation(findViewById(R.id.mainLayout),
					Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
			break;
		case R.id.rl_userChange_adress:
			break;
		case R.id.rl_userChange_intro:
			Toast.makeText(UserDataActivity.this, "个人简介", Toast.LENGTH_SHORT)
					.show();
			startActivityForResult(new Intent(UserDataActivity.this,
					UserPerSignActivity.class), REQUESTCODE_PERSIGN);
			break;
		case R.id.rl_userChange_birthday:
			startActivityForResult(new Intent(UserDataActivity.this,
					DatePickerActivity.class), REQUESTCODE_BIRTHDAY_OR_STAR);
			break;
		case R.id.rl_userChange_star:
			startActivityForResult(new Intent(UserDataActivity.this,
					DatePickerActivity.class), REQUESTCODE_BIRTHDAY_OR_STAR);
			break;
		case R.id.rl_userChange_school:
			break;
		case R.id.rl_userChange_work:
			break;
		case R.id.rl_userChange_state:
			break;
		case R.id.rl_userChange_psw:
			Intent intent = new Intent(UserDataActivity.this,
					UserRegisterActivity.class);
			intent.putExtra("Title", CHANGE_PSW);
			startActivity(intent);
			break;
		default:
			break;
		}
		IsAlert = true;
	}

	/**
	 * 保存用户数据
	 */
	VolleyUtils mUtils;
	ArrayMap<String, String> map;

	private void SaveUserInfo() {
		mUtils = new VolleyUtilsImpl();
		map = new ArrayMap<String, String>();
		map.put("countid", ApplicationController.getInstance().getUser()
				.getInt("countid", 0)
				+ "");
		map_up(nickname);
		map_up(sex);
		map_up(borndate);
		map_up(starid + "");
		map_up(persign);
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what == hpCantant.LABLE_UALERT) {

					Toast.makeText(UserDataActivity.this, "修改成功", 1).show();
					SaveUserPreInfo();
					UserDataActivity.this.finish();
				}
				if (msg.what == 999) {
					icon = (String) msg.obj;
					map_up(icon);
					CommonUtils.getData(mHandler, map,
							hpCantant.UALERTUSERINFO_URL,
							hpCantant.LABLE_UALERT);
				}
			}

		};
		if (urlpath != null) {
			uit = new UploadImageToken(UserDataActivity.this, urlpath,
					mHandler, "4");
		} else {
			CommonUtils.getData(mHandler, map, hpCantant.UALERTUSERINFO_URL,
					hpCantant.LABLE_UALERT);
		}

	}

	private void map_up(String text) {
		if (text != null) {
			map.put(text, text);
		}
	}

	/**
	 * 保存数据库的字段
	 */
	private void SaveUserPreInfo() {
		SharedPreferences.Editor mEditor = ApplicationController.getInstance()
				.getUser().edit();
		if (icon != null) {
			mEditor.putString("icon", icon);
		}
		if (nickname != null) {
			mEditor.putString("nickname", nickname);
		}
		if (sex != null) {
			mEditor.putInt("sex", sexid);
		}
		if (borndate != null) {
			mEditor.putString("borndate", borndate);
			mEditor.putString("starid", starid + "");
		}
		if (persign != null) {
			mEditor.putString("persign", persign);
		}
		// mEditor.putString("industry",mUData.getIndustry());
		// mEditor.putString("job", mUData.getJob());
		// mEditor.putString("school",mUData.getSchool());

		// mEditor.putString("btypeid", mUData.getBtypeid());

		// mEditor.putInt("state", mUData.getState());
		Log.i(getPackageName(), mEditor.toString());
		mEditor.commit();
	};

	/*
	 * 弹出提示框
	 */
	private void AlertDialog() {
		new AlertDialog.Builder(UserDataActivity.this).setTitle("系统提示")
		// 设置对话框标题
				.setMessage("是否放弃编辑?")
				// 设置显示的内容

				.setPositiveButton("放弃", new DialogInterface.OnClickListener() {// 添加确定按钮
							@Override
							public void onClick(DialogInterface dialog,
									int which) {// 确定按钮的响应事件
								finish();
							}
						})//
				.setNegativeButton("取消", null)//
				.show();// 在按键响应事件中显示此对话框
	}

	/**
	 * 设置昵称
	 */
	private void setNick() {
		dialog = new CustomDialog(UserDataActivity.this);
		final EditText editText = (EditText) ((CustomDialog) dialog)
				.getEditText();// 方法在CustomDialog中实现
		editText.setHint(tvNick.getText().toString());

		((CustomDialog) dialog).setOnPositiveListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// dosomething youself
				tvNick.setText(editText.getText().toString().trim());
				nickname = tvNick.getText().toString();
				dialog.dismiss();
				// Toast.makeText(getApplicationContext(), name,
				// Toast.LENGTH_SHORT).show();
			}
		});
		((CustomDialog) dialog).setOnNegativeListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
		WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
		params.width = LayoutParams.MATCH_PARENT;
		params.height = LayoutParams.WRAP_CONTENT;
		dialog.getWindow().setAttributes(params);
	}

	private int sexid;
	/**
	 * 选择性别弹窗
	 */
	private OnClickListener SelectSexOnClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			menuWindow.dismiss();
			switch (v.getId()) {
			// 男
			case R.id.takePhotoBtn:
				sexid = 1;
				tvSex.setText("男");
				break;
			// 女
			case R.id.pickPhotoBtn:
				sexid = 2;
				tvSex.setText("女");
				break;
			default:
				sexid = 0;
				tvSex.setText("保密");
				break;
			}
			sex = tvSex.getText().toString();
		}
	};

	// 修改头像弹窗
	private OnClickListener ChangeHeadOnClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			menuWindow.dismiss();
			switch (v.getId()) {
			// 拍照
			case R.id.takePhotoBtn:
				Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				// 下面这句指定调用相机拍照后的照片存储的路径
				takeIntent
						.putExtra(MediaStore.EXTRA_OUTPUT, Uri
								.fromFile(new File(Environment
										.getExternalStorageDirectory(),
										IMAGE_FILE_NAME)));
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
	private int starid;
	private UploadImageToken uit;

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
		case REQUESTCODE_PICK:// 直接从相册获取
			try {
				startPhotoZoom(data.getData());
			} catch (NullPointerException e) {
				e.printStackTrace();// 用户点击取消操作
			}
			break;
		case REQUESTCODE_TAKE:// 调用相机拍照
			File temp = new File(Environment.getExternalStorageDirectory()
					+ "/" + IMAGE_FILE_NAME);
			startPhotoZoom(Uri.fromFile(temp));
			break;
		case REQUESTCODE_CUTTING:// 取得裁剪后的图片
			if (data != null) {
				setPicToView(data);
			}
			break;
		}

		switch (resultCode) {
		case REQUESTCODE_PERSIGN:// 获取个人简介
			tvPer.setText(data.getStringExtra("persign"));
			persign = tvPer.getText().toString();
			break;
		case REQUESTCODE_BIRTHDAY_OR_STAR:// 获取出生日及星座
			Bundle b = data.getExtras();
			tvBorn.setText(b.getString("birthday"));
			borndate = tvBorn.getText().toString();
			((TextView) findViewById(R.id.tv_star))
					.setText(b.getString("star"));
			starid = b.getInt("starid");
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, REQUESTCODE_CUTTING);
	}

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 */
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			// 取得SDCard图片路径做显示
			Bitmap photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(null,
					CommonUtils.toRoundBitmap(photo));
			urlpath = FileUtil.saveFile(UserDataActivity.this, IMAGE_FILE_NAME,
					photo);
			ivHead.setImageDrawable(drawable);
		}
	}

}
