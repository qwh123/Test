package com.hp.activity.userOrder;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.hp.R;
import com.hp.bean.UGetQRCode;
import com.hp.bean.UGetQRCode.Data;
import com.hp.dao.UGetQRCodeDao;
import com.hp.utils.CommonUtils;
import com.hp.utils.hpCantant;
import com.hp.widget.TopBar;
import com.hp.widget.TopBar.topBarClickListener;
import com.zxing.encoding.EncodingHandler;

public class QRGenActivity extends Activity {
	private ImageView qrImgImageView;
	private String orderid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qrgen);
		orderid = getIntent().getExtras().getString("orderid");
		Log.i("qwgen", orderid);
		TopBar tBar = (TopBar) findViewById(R.id.topbar_qrgen);
		tBar.setTitleText("验证二维码");
		tBar.setRightIsVisable(false);
		tBar.setOnTopBarClickListener(new topBarClickListener() {
			@Override
			public void rightClick() {
			}

			@Override
			public void leftClick() {
				finish();
			}
		});
		getData();
	}

	private UGetQRCodeDao mCodeDao;
	private UGetQRCode.Data mData;
	private Handler mHandler = new Handler() {
		private JSONObject js;

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle bundle = msg.getData();
			if (msg.what == hpCantant.LABLE_UGETQRCODE) {
				mCodeDao = new UGetQRCodeDao();
				try {
					js = new JSONObject(bundle.getString(hpCantant.GETDATA));
					if (js.getString("code").equals(hpCantant.SUCCESS_CODE)) {
						mData = mCodeDao.getUGetQRCode(bundle
								.getString(hpCantant.GETDATA));
						initView(mData);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	};

	private void getData() {
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("orderid", orderid);// 用户账号id
		CommonUtils.getData(mHandler, map, hpCantant.UGETQRCODE_URL,
				hpCantant.LABLE_UGETQRCODE);
	}

	private void initView(Data mData2) {
		qrImgImageView = (ImageView) findViewById(R.id.iv_qr_image);
		((TextView) findViewById(R.id.tv_qrgen_date)).setText("有效期："
				+ mData2.getBegintime() + "~" + mData2.getEndtime());
		((TextView) findViewById(R.id.tv_qrgen_string)).setText("券码："
				+ mData2.getCode());

		try {
			if (!mData2.getCode().equals("")) {
				// 根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（600*600）
				Bitmap qrCodeBitmap = EncodingHandler.createQRCode(
						mData2.getCode(), 600);

				// ------------------添加logo部分------------------//
				Bitmap logoBmp = BitmapFactory.decodeResource(getResources(),
						R.drawable.logo_homeparty);

				// 二维码和logo合并
				Bitmap bitmap = Bitmap.createBitmap(qrCodeBitmap.getWidth(),
						qrCodeBitmap.getHeight(), qrCodeBitmap.getConfig());
				Canvas canvas = new Canvas(bitmap);
				// 二维码
				canvas.drawBitmap(qrCodeBitmap, 0, 0, null);
				// logo绘制在二维码中央
				canvas.drawBitmap(logoBmp, qrCodeBitmap.getWidth() / 2
						- logoBmp.getWidth() / 2, qrCodeBitmap.getHeight() / 2
						- logoBmp.getHeight() / 2, null);
				// ------------------添加logo部分------------------//

				qrImgImageView.setImageBitmap(bitmap);
			} else {
				Toast.makeText(QRGenActivity.this, "Text can not be empty",
						Toast.LENGTH_SHORT).show();
			}

		} catch (WriterException e) {
			e.printStackTrace();
		}
	}
}
