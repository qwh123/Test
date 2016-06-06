package com.hp.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.util.ArrayMap;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.hp.R;
import com.hp.application.ApplicationController;
import com.hp.utils.UploadImageToken;
import com.hp.utils.hpCantant;
import com.hp.widget.SelectPicPop;
import com.hp.widget.TopBar;
import com.hp.widget.TopBar.topBarClickListener;
import com.photoselector.model.PhotoModel;
import com.photoselector.ui.PhotoPreviewActivity;
import com.photoselector.ui.PhotoSelectorActivity;
import com.photoselector.util.CommonUtils;
import com.scene.selectimg.Constants;
import com.scene.selectimg.GdAdapter;
import com.scene.selectimg.Util;
import com.scene.selectimg.img.ImageLoaderConfig;

public class PublishDTActivity extends Activity implements OnItemClickListener {
	private GridView gd;
	private GdAdapter adapter;
	private final int SELECT_IMAGE_CODE = 1001;
	// 选择图片
	private String str_choosed_img = "";
	private List<PhotoModel> selected;
	private final int MAX_PHOTOS = 100;
	private SelectPicPop menuWindow;
	private String title;
	private StringBuffer images = null;
	private int num = 0;// 记录图片上传次数
	private String contents;
	private EditText edtPublish;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ImageLoaderConfig.initImageLoader(this, Constants.BASE_IMAGE_CACHE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_publish_denymic);
		Bundle bundle = getIntent().getExtras();
		title = bundle.getString("title");
		initView();
	}

	private Handler mHandler = new Handler() {
		private JSONObject js;

		public void handleMessage(android.os.Message msg) {
			if (msg.what == hpCantant.LABLE_UDYNAMICCREATE) {
				Bundle bundle = msg.getData();
				try {
					js = new JSONObject(bundle.getString(hpCantant.GETDATA));
					if (js.getString("code").equals(hpCantant.SUCCESS_CODE)) {
						Toast.makeText(PublishDTActivity.this, "发布成功",
								Toast.LENGTH_SHORT).show();
						Intent intent = getIntent();
						// 数据是使用Intent返回
						// 把返回数据存入Intent
						intent.putExtra("result", "ok");
						// 设置返回数据
						PublishDTActivity.this.setResult(RESULT_OK, intent);
						PublishDTActivity.this.finish();
					} else {
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else if (msg.what == 999) {

				if (images != null) {
					// images = images + ";" + (String) msg.obj;
					images.append(";"+(String) msg.obj);
				} else {
					images = new StringBuffer((String) msg.obj);
				}
				num++;
				if (num == selected.size() - 1) {
					ArrayMap<String, String> map = new ArrayMap<String, String>();
					map.put("lat", ApplicationController.getInstance().getLat());
					map.put("lng", ApplicationController.getInstance().getLng());
					map.put("contents", contents);
					map.put("classid", "1");
					map.put("images", images.toString());
					map.put("countid", ApplicationController.getInstance()
							.getUser().getInt("countid", 0)
							+ "");
					com.hp.utils.CommonUtils.getData(mHandler, map,
							hpCantant.UDYNAMICCREATE_URL,
							hpCantant.LABLE_UDYNAMICCREATE);
				}
			}

		};
	};

	private void initView() {
		edtPublish = (EditText) findViewById(R.id.edt_denymic_content);
		TopBar tBar = (TopBar) findViewById(R.id.topbar_pub_dynamic);
		tBar.setTitleText(title);
		tBar.setRighttImageResource(R.drawable.icon_right_publish);
		tBar.setOnTopBarClickListener(new topBarClickListener() {
			private UploadImageToken upload;

			@Override
			public void rightClick() {
				contents = edtPublish.getText().toString();
				if (selected.size() > 1) {
					for (int i = 0; i < selected.size() - 1; i++) {
						upload = new UploadImageToken(PublishDTActivity.this,
								selected.get(i).getOriginalPath(), mHandler,
								"3");
					}
				} else {
					ArrayMap<String, String> map = new ArrayMap<String, String>();
					map.put("lat", ApplicationController.getInstance().getLat());
					map.put("lng", ApplicationController.getInstance().getLng());
					map.put("contents", contents);
					map.put("countid", ApplicationController.getInstance()
							.getUser().getInt("countid", 0)
							+ "");
					com.hp.utils.CommonUtils.getData(mHandler, map,
							hpCantant.UDYNAMICCREATE_URL,
							hpCantant.LABLE_UDYNAMICCREATE);
				}
			}

			@Override
			public void leftClick() {
				finish();
			}
		});

		gd = (GridView) findViewById(R.id.gv_publish_denymic);
		selected = new ArrayList<PhotoModel>();
		PhotoModel photoModel = new PhotoModel();
		photoModel.setOriginalPath("default");
		selected.add(photoModel);
		adapter = new GdAdapter(PublishDTActivity.this, selected);
		gd.setAdapter(adapter);
		gd.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		onFocusChange(false);
		if (position == selected.size() - 1) {// 如果是最后一个
			String[] titles = { "拍照", "从相册选择", "取消" };
			// 更换头像点击事件
			menuWindow = new SelectPicPop(this, titles, ChangeHeadOnClick);
			menuWindow.showAtLocation(findViewById(R.id.mainLayout),
					Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
		} else {
			List<PhotoModel> lists = new ArrayList<PhotoModel>();
			lists.addAll(selected);
			lists.remove(lists.size() - 1);
			Bundle bundle = new Bundle();
			bundle.putSerializable("photos", (Serializable) lists);
			CommonUtils
					.launchActivity(this, PhotoPreviewActivity.class, bundle);
		}
	}

	@SuppressWarnings("unchecked")
	private void enterChoosePhoto() {
		// TODO Auto-generated method stub

		List<PhotoModel> choosed = new ArrayList<PhotoModel>();
		if (selected.size() > 0) {
			choosed.addAll(selected);
			choosed.remove(choosed.size() - 1);
		}
		Intent intent = new Intent(PublishDTActivity.this,
				PhotoSelectorActivity.class);
		intent.putExtra(PhotoSelectorActivity.KEY_MAX, MAX_PHOTOS);
		Bundle bundle = new Bundle();
		bundle.putParcelableArrayList("selected",
				(ArrayList<? extends Parcelable>) choosed);
		intent.putExtras(bundle);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivityForResult(intent, SELECT_IMAGE_CODE);
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addCategory(Intent.CATEGORY_HOME);
		startActivity(intent);
	}

	// 修改头像弹窗
	private OnClickListener ChangeHeadOnClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			menuWindow.dismiss();
			switch (v.getId()) {
			// 拍照
			case R.id.takePhotoBtn:
				if (selected.size() > MAX_PHOTOS) {
					Toast.makeText(PublishDTActivity.this,
							"最多上传" + MAX_PHOTOS + "张", Toast.LENGTH_SHORT)
							.show();
				} else {
					Util.selectPicFromCamera(PublishDTActivity.this);
				}
				break;
			// 相册选择图片
			case R.id.pickPhotoBtn:
				enterChoosePhoto();
				break;
			default:
				break;
			}
		}
	};

	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case SELECT_IMAGE_CODE:
				List<PhotoModel> photos = (List<PhotoModel>) data.getExtras()
						.getSerializable("photos");
				selected.clear();
				adapter.notifyDataSetChanged();
				selected.addAll(photos);
				PhotoModel addModel = new PhotoModel();
				addModel.setOriginalPath("default");
				selected.add(addModel);
				adapter.notifyDataSetChanged();
				break;

			case Util.CAMERA_PHOTO:// 拍照上传
				if (Util.cameraFile != null && Util.cameraFile.exists()) {
					str_choosed_img = Util.cameraFile.getAbsolutePath();
					PhotoModel cameraPhotoModel = new PhotoModel();
					cameraPhotoModel.setChecked(true);
					cameraPhotoModel.setOriginalPath(str_choosed_img);
					if (selected.size() > 0) {// 如果原来有图片
						selected.remove(selected.size() - 1);
					}
					selected.add(cameraPhotoModel);
					PhotoModel addModel1 = new PhotoModel();
					addModel1.setChecked(false);
					addModel1.setOriginalPath("default");
					selected.add(addModel1);
					adapter.notifyDataSetChanged();
					MediaScannerConnection.scanFile(PublishDTActivity.this,
							new String[] { str_choosed_img }, null, null);
				} else {
					Util.showToast(PublishDTActivity.this, "获取照片失败，请重试");
				}
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 显示或隐藏输入法
	 */
	public void onFocusChange(boolean hasFocus) {
		final boolean isFocus = hasFocus;
		(new Handler()).postDelayed(new Runnable() {
			public void run() {
				InputMethodManager imm = (InputMethodManager) edtPublish
						.getContext().getSystemService(INPUT_METHOD_SERVICE);
				if (isFocus) {
					// 显示输入法
					imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
				} else {
					// 隐藏输入法
					imm.hideSoftInputFromWindow(edtPublish.getWindowToken(), 0);
				}
			}
		}, 100);
	}

}
