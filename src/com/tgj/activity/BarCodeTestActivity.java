package com.tgj.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.hp.R;
import com.zxing.encoding.EncodingHandler;

public class BarCodeTestActivity extends Activity {
	/** Called when the activity is first created. */
	private TextView resultTextView;
	private EditText qrStrEditText;
	private ImageView qrImgImageView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capture_test);

		final Resources r = getResources();

		resultTextView = (TextView) this.findViewById(R.id.tv_scan_result);
		qrStrEditText = (EditText) this.findViewById(R.id.et_qr_string);
		qrImgImageView = (ImageView) this.findViewById(R.id.iv_qr_image);

		Button scanBarCodeButton = (Button) this
				.findViewById(R.id.btn_scan_barcode);
		scanBarCodeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 打开扫描界面扫描条形码或二维码
				Intent openCameraIntent = new Intent(BarCodeTestActivity.this,
						CaptureActivity.class);
				startActivityForResult(openCameraIntent, 0);
			}
		});

		Button generateQRCodeButton = (Button) this
				.findViewById(R.id.btn_add_qrcode);
		generateQRCodeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					String contentString = qrStrEditText.getText().toString();
					if (!contentString.equals("")) {
						// 根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（600*600）
						Bitmap qrCodeBitmap = EncodingHandler.createQRCode(
								contentString, 600);

						// ------------------添加logo部分------------------//
						Bitmap logoBmp = BitmapFactory.decodeResource(r,
								R.drawable.ic_launcher);

						// 二维码和logo合并
						Bitmap bitmap = Bitmap.createBitmap(
								qrCodeBitmap.getWidth(),
								qrCodeBitmap.getHeight(),
								qrCodeBitmap.getConfig());
						Canvas canvas = new Canvas(bitmap);
						// 二维码
						canvas.drawBitmap(qrCodeBitmap, 0, 0, null);
						// logo绘制在二维码中央
						canvas.drawBitmap(
								logoBmp,
								qrCodeBitmap.getWidth() / 2
										- logoBmp.getWidth() / 2,
								qrCodeBitmap.getHeight() / 2
										- logoBmp.getHeight() / 2, null);
						// ------------------添加logo部分------------------//

						qrImgImageView.setImageBitmap(bitmap);
					} else {
						Toast.makeText(BarCodeTestActivity.this,
								"Text can not be empty", Toast.LENGTH_SHORT)
								.show();
					}

				} catch (WriterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 处理扫描结果（在界面上显示）
		if (resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result");
			resultTextView.setText(scanResult);
		}
	}
}