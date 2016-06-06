package com.hp.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.hp.utils.volley.VolleyUtils;
import com.hp.utils.volley.VolleyUtilsImpl;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.util.ArrayMap;

public class CommonUtils {
	private static MyLogger Log = MyLogger.yLog();

	/**
	 * 获取数据
	 * 
	 * @param map
	 * @param url
	 * @param lable
	 */
	public static void getData(final Handler mHandler,
			final ArrayMap<String, String> map, final String url, final int lable) {
		new Thread() {
			public void run() {
				VolleyUtils mUtils = new VolleyUtilsImpl();
				mUtils.getRequest2String(hpCantant.URL + url, mHandler, map,
						lable);
			};
		}.start();
	}

	/**
	 * 获取图管家数据
	 * 
	 * @param map
	 * @param url
	 * @param lable
	 */
	public static void getTGJData(final Handler mHandler,
			final ArrayMap<String, String> map, final String url,
			final int lable) {
		new Thread() {
			public void run() {
				VolleyUtils mUtils = new VolleyUtilsImpl();
				mUtils.getRequest2String(TGJCanst.URL + url, mHandler, map,
						lable);
			};
		}.start();
	}

	/**
	 * 获取数据
	 * 
	 * @param url
	 * @param lable
	 */
	public static void getData1(final Handler mHandler, final String url,
			final int lable) {
		new Thread() {
			public void run() {
				VolleyUtils mUtils = new VolleyUtilsImpl();
				mUtils.getRequestString(hpCantant.URL + url, mHandler, lable);
			};
		}.start();
	}

	/**
	 * 检测网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		if (context != null) {
			// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (mConnectivityManager == null) {
				return false;
			} else { // 获取NetworkInfo对象
				NetworkInfo[] mNetworkInfo = mConnectivityManager
						.getAllNetworkInfo();
				for (NetworkInfo networkInfo : mNetworkInfo) {
					// 判断当前网络状态是否为连接状态
					if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * 检测Sdcard是否存在
	 * 
	 * @return
	 */
	public static boolean isExitsSdcard() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;
	}

	public static String getTopActivity(Context context) {
		ActivityManager manager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

		if (runningTaskInfos != null)
			return runningTaskInfos.get(0).topActivity.getClassName();
		else
			return "";
	}

	/**
	 * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNullOrEmpty(Object obj) {
		Log.d("obj--->" + obj);

		if (obj != null) {
			Log.d("--->not null");
		} else {
			return true;
		}

		if (obj.toString() != null) {// 判断对象（不知道对不对）
			Log.d("--->not null");
		} else {
			return true;
		}

		/*
		 * if (!isNullOrEmpty(obj.toString())){//判断对象（不知道对不对）0904|好像有错，以后在好好看一下
		 * Log.d("isNullOrEmpty", "=》》》》》》not null"); }else{ return true; }
		 */

		if (obj instanceof CharSequence) {
			Log.d("--->CharSequence");
			return ((CharSequence) obj).length() == 0;
		}
		if (obj instanceof Collection) {
			Log.d("--->Collection");
			return ((Collection) obj).isEmpty();
		}

		if (obj instanceof Map) {
			Log.d("--->Map");
			return ((Map) obj).isEmpty();
		}

		if (obj instanceof Object[]) {
			Log.d("--->Object[]");
			Object[] object = (Object[]) obj;
			if (object.length == 0) {
				return true;
			}
			boolean empty = true;
			for (int i = 0; i < object.length; i++) {
				if (!isNullOrEmpty(object[i])) {
					empty = false;
					break;
				}
			}
			return empty;
		}

		if (obj instanceof JSONArray) {
			if (((JSONArray) obj).length() == 0) {
				return true;
			}
		}

		if (obj instanceof JSONObject) {
			if (((JSONObject) obj).length() == 0) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 计算地球上任意两点(经纬度)距离
	 * 
	 * @param long1
	 *            第一点经度
	 * @param lat1
	 *            第一点纬度
	 * @param long2
	 *            第二点经度
	 * @param lat2
	 *            第二点纬度
	 * @return 返回距离 单位：米
	 */
	public static double Distance(double long1, double lat1, double long2,
			double lat2) {
		double a, b, R;
		R = 6378137; // 地球半径
		lat1 = lat1 * Math.PI / 180.0;
		lat2 = lat2 * Math.PI / 180.0;
		a = lat1 - lat2;
		b = (long1 - long2) * Math.PI / 180.0;
		double d;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		d = 2
				* R
				* Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
						* Math.cos(lat2) * sb2 * sb2));
		return d;
	}

	/**
	 * 过滤emoji 或者 其他非文字类型的字符
	 * 
	 * @param source
	 * @return
	 */
	public static String filterEmoji(String source) {

		if (!containsEmoji(source)) {
			return source;// 如果不包含，直接返回
		}
		// 到这里铁定包含
		StringBuilder buf = null;

		int len = source.length();

		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);

			if (!isEmojiCharacter(codePoint)) {
				if (buf == null) {
					buf = new StringBuilder(source.length());
				}

				buf.append(codePoint);
			} else {
			}
		}

		if (buf == null) {
			return "";
		} else {
			if (buf.length() == len) {// 这里的意义在于尽可能少的toString，因为会重新生成字符串
				buf = null;
				return source;
			} else {
				return buf.toString();
			}
		}

	}

	private static boolean isEmojiCharacter(char codePoint) {
		return !((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
				|| (codePoint == 0xD)
				|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
				|| ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
	}

	/**
	 * 检测是否有emoji字符
	 * 
	 * @param source
	 * @return 一旦含有就抛出
	 */
	public static boolean containsEmoji(String source) {
		// if (StringUtils.isBlank(source)) { //需要引用commons-lang-2.5.jar
		// return false;
		// }

		int len = source.length();

		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);

			if (isEmojiCharacter(codePoint)) {
				// do nothing，判断到了这里表明，确认有表情字符
				return true;
			}
		}

		return false;
	}

	/**
	 * 转换图片成圆形
	 * 
	 * @param bitmap
	 *            传入Bitmap对象
	 * @return
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;

			left = 0;
			top = 0;
			right = width;
			bottom = width;

			height = width;

			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;

			float clip = (width - height) / 2;

			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;

			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right,
				(int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);

		paint.setAntiAlias(true);// 设置画笔无锯齿

		canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas

		// 以下有两种方法画圆,drawRounRect和drawCircle
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);// 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
		// canvas.drawCircle(roundPx, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
		canvas.drawBitmap(bitmap, src, dst, paint); // 以Mode.SRC_IN模式合并bitmap和已经draw了的Circle

		return output;
	}

}
