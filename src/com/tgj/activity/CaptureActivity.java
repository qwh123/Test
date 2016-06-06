package com.tgj.activity;

import java.io.IOException;
import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.util.ArrayMap;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.hp.R;
import com.hp.utils.CommonUtils;
import com.hp.utils.TGJCanst;
import com.hp.utils.hpCantant;
import com.hp.widget.TopBar;
import com.hp.widget.TopBar.topBarClickListener;
import com.zxing.camera.CameraManager;
import com.zxing.decoding.CaptureActivityHandler;
import com.zxing.decoding.InactivityTimer;
import com.zxing.view.ViewfinderView;

/**
 * Initial the camera
 * 
 * @author Ryan.Tang
 */
public class CaptureActivity extends Activity implements Callback {

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;

	private Handler mHandler = new Handler() {
		private JSONObject js;

		public void handleMessage(android.os.Message msg) {
			if (msg.what == TGJCanst.LABLE_BCodeValidate) {
				Bundle bundle = msg.getData();
				try {
					if (pgbCa != null) {
						btnCancel.setVisibility(View.VISIBLE);
						pgbCa.setVisibility(View.GONE);
					}
					js = new JSONObject(bundle.getString(hpCantant.GETDATA));
					Intent resultIntent = getIntent();
					Bundle bundle1 = new Bundle();
					if (js.getInt("code") == 100) {
						bundle1.putString("result", resultString);
					} else if (js.getInt("code") == 101) {
						bundle1.putString("result", "券码错误");
					} else if (js.getInt("code") == 102) {
						bundle1.putString("result", "该券码已使用");
					}
					bundle1.putString("result", "验券失败");
					resultIntent.putExtras(bundle1);
					CaptureActivity.this.setResult(RESULT_OK, resultIntent);
					CaptureActivity.this.finish();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
	};
	private String resultString = null;
	private TopBar tBar = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capture);
		// ViewUtil.addTopView(getApplicationContext(), this,
		// R.string.scan_card);
		CameraManager.init(getApplication());
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		tBar = (TopBar) findViewById(R.id.topbar_capture);
		tBar.setTitleText("扫码");
		tBar.setRighttImageResource(R.drawable.icon_right_tj);
		tBar.setOnTopBarClickListener(new topBarClickListener() {
			@Override
			public void rightClick() {
				showQM();
			}

			@Override
			public void leftClick() {
				finish();
			}
		});

		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;

	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	/**
	 * Handler scan result
	 * 
	 * @param result
	 * @param barcode
	 */
	public void handleDecode(Result result, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		resultString = result.getText();
		// FIXME
		if (resultString.equals("")) {
			Toast.makeText(CaptureActivity.this, "Scan failed!",
					Toast.LENGTH_SHORT).show();
		} else {
			// System.out.println("Result:"+resultString);
			intentResult(resultString);
		}
		CaptureActivity.this.finish();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};
	EditText edtKey = null;
	Button btnCancel = null;
	private LinearLayout ll = null;
	private ProgressBar pgbCa = null;

	/**
	 * 弹出输入券码
	 */
	private void showQM() {
		ll = (LinearLayout) findViewById(R.id.ll_capture);
		tBar.setVisibility(View.GONE);
		ll.setVisibility(View.VISIBLE);
		pgbCa = (ProgressBar) findViewById(R.id.pgb_capture);
		btnCancel = (Button) findViewById(R.id.btn_capture_back);
		edtKey = (EditText) findViewById(R.id.edt_capture_input);
		edtKey.addTextChangedListener(new EditChangedListener());
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (btnCancel.getText().toString().trim().equals("取消")) {
					tBar.setVisibility(View.VISIBLE);
					ll.setVisibility(View.GONE);
				} else {
					btnCancel.setVisibility(View.GONE);
					pgbCa.setVisibility(View.VISIBLE);
					intentResult(edtKey.getText().toString());
				}
				onFocusChange(false);
			}
		});
	}

	/**
	 * 返回结果
	 * 
	 * @param result
	 */
	private void intentResult(String result) {
		ArrayMap<String, String> map = new ArrayMap<String, String>();
		map.put("code", result);
		CommonUtils.getTGJData(mHandler, map, TGJCanst.BCodeValidate_URL,
				TGJCanst.LABLE_BCodeValidate);
	}

	class EditChangedListener implements TextWatcher {
		private String inputString = null;

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			Log.d(getPackageResourcePath(), " onTextChanged--------------->");
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			Log.d(getPackageName(), " beforeTextChanged--------------->");
		}

		@Override
		public void afterTextChanged(Editable arg0) {
			Log.d(getPackageName(), "afterTextChanged--------------->");
			inputString = edtKey.getText().toString().trim();
			if (inputString.isEmpty()) {
				btnCancel.setText("取消");
			} else {
				btnCancel.setText("验券");
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
				InputMethodManager imm = (InputMethodManager) edtKey
						.getContext().getSystemService(INPUT_METHOD_SERVICE);
				if (isFocus) {
					// 显示输入法
					imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
				} else {
					// 隐藏输入法
					imm.hideSoftInputFromWindow(edtKey.getWindowToken(), 0);
				}
			}
		}, 100);
	}

}