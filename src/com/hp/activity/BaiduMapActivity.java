package com.hp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapLongClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.hp.R;
import com.hp.utils.MyOrientationListener;
import com.hp.utils.MyOrientationListener.OnOrientationListener;
import com.hp.widget.TopBar;
import com.hp.widget.TopBar.topBarClickListener;

public class BaiduMapActivity extends Activity {
	private MapView mMapView;
	private BaiduMap mBaiduMap;

	private Context context;

	// 定位相关
	private LocationClient mLocationClient;
	private MyLocationListener mLocationListener;
	private boolean isFirstIn = true;
	private double mLatitude;
	private double mLongtitude;
	private String mAdress;
	// 自定义定位图标
	private BitmapDescriptor mIconLocation;
	private MyOrientationListener myOrientationListener;
	private float mCurrentX;
	private LocationMode mLocationMode;

	// 覆盖物相关
	private BitmapDescriptor mMarker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
		// 注意该方法要再setContentView方法之前实现
		getWindow().setFlags(0x08000000, 0x08000000);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_baidumap);
		this.context = this;
		initView();
		// 初始化定位
		initLocation();
		initMarker();
		/**
		 * 点击地图监听
		 */
		// mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
		// @Override
		// public boolean onMarkerClick(Marker marker) {
		// Bundle extraInfo = marker.getExtraInfo();
		// BaiduFGWInfo info = (BaiduFGWInfo) extraInfo
		// .getSerializable("info");
		//
		// InfoWindow infoWindow;
		// TextView tv = new TextView(context);
		// tv.setBackgroundResource(R.drawable.location_tips);
		// tv.setPadding(30, 20, 30, 50);
		// tv.setText(info.getName());
		// tv.setTextColor(Color.parseColor("#ffffff"));
		//
		// final LatLng latLng = marker.getPosition();
		// Point p = mBaiduMap.getProjection().toScreenLocation(latLng);
		// p.y -= 47;
		// LatLng ll = mBaiduMap.getProjection().fromScreenLocation(p);
		//
		// infoWindow = new InfoWindow(mMarker, ll, 0,
		// new OnInfoWindowClickListener() {
		//
		// @Override
		// public void onInfoWindowClick() {
		// mBaiduMap.hideInfoWindow();
		// }
		// });
		//
		// mBaiduMap.showInfoWindow(infoWindow);
		// return true;
		// }
		// });
		mBaiduMap.setOnMapClickListener(new OnMapClickListener() {
			@Override
			public boolean onMapPoiClick(MapPoi arg0) {
				return false;
			}

			@Override
			public void onMapClick(LatLng latLng) {
				mBaiduMap.hideInfoWindow();

				mLatitude = latLng.latitude;
				mLongtitude = latLng.longitude;
				// 先清除图层
				mBaiduMap.clear();
				// 定义Maker坐标点
				LatLng point = new LatLng(mLatitude, mLongtitude);
				// 构建MarkerOption，用于在地图上添加Marker
				MarkerOptions options = new MarkerOptions().position(point)
						.icon(mMarker);
				// 在地图上添加Marker，并显示
				mBaiduMap.addOverlay(options);
				BDLocation bLocation=new BDLocation();
				mAdress=bLocation.getAddrStr();
				// 获取点击的坐标地址
				Toast.makeText(BaiduMapActivity.this,
						mAdress, 0).show();
//				// 实例化一个地理编码查询对象
//				GeoCoder geoCoder = GeoCoder.newInstance();
//				// 设置反地理编码位置坐标
//				ReverseGeoCodeOption op = new ReverseGeoCodeOption();
//				op.location(latLng);
//				// 发起反地理编码请求(经纬度->地址信息)
//				geoCoder.reverseGeoCode(op);
//				geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
//
//					@Override
//					public void onGetReverseGeoCodeResult(
//							ReverseGeoCodeResult arg0) {
//						mAdress=arg0.getAddress();
//						// 获取点击的坐标地址
//						Toast.makeText(BaiduMapActivity.this,
//								arg0.getAddress(), 0).show();
//					}
//
//					@Override
//					public void onGetGeoCodeResult(GeoCodeResult arg0) {
//					}
//				});
			}
		});
		mBaiduMap.setOnMapLongClickListener(new OnMapLongClickListener() {

			@Override
			public void onMapLongClick(LatLng arg0) {
				
			}
		});
	}

	private void initMarker() {
		mMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_adress);
	}

	private void initLocation() {

		mLocationMode = LocationMode.NORMAL;
		mLocationClient = new LocationClient(this);
		mLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mLocationListener);

		LocationClientOption option = new LocationClientOption();
		option.setCoorType("bd09ll");
		option.setIsNeedAddress(true);
		option.setOpenGps(true);
		option.setScanSpan(1000);
		mLocationClient.setLocOption(option);
		// 初始化图标
		mIconLocation = BitmapDescriptorFactory
				.fromResource(R.drawable.icon_adress);
		myOrientationListener = new MyOrientationListener(context);

		myOrientationListener
				.setOnOrientationListener(new OnOrientationListener() {
					@Override
					public void onOrientationChanged(float x) {
						mCurrentX = x;
					}
				});

	}

	private void initView() {
		TopBar tBar = (TopBar) findViewById(R.id.topbar_map);
		tBar.setTitleText("");
		tBar.setRighttImageResource(R.drawable.icon_right_tj);
		tBar.setOnTopBarClickListener(new topBarClickListener() {
			@Override
			public void rightClick() {
				// DOTO
				Intent intent=getIntent();
				Bundle bundle=new Bundle();
				bundle.putString("lat", mLatitude+"");
				bundle.putString("lng", mLongtitude+"");
				bundle.putString("adress", mAdress);
				intent.putExtras(bundle);
				setResult(RESULT_OK, intent);
				BaiduMapActivity.this.finish();
			}
			@Override
			public void leftClick() {
				finish();
			}
		});
		mMapView = (MapView) findViewById(R.id.id_bmapView);
		mBaiduMap = mMapView.getMap();
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
		mBaiduMap.setMapStatus(msu);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	protected void onStart() {
		super.onStart();
		// 开启定位
		mBaiduMap.setMyLocationEnabled(true);
		if (!mLocationClient.isStarted())
			mLocationClient.start();
		// 开启方向传感器
		myOrientationListener.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();

		// 停止定位
		mBaiduMap.setMyLocationEnabled(false);
		mLocationClient.stop();
		// 停止方向传感器
		myOrientationListener.stop();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
	}

	private class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {

			MyLocationData data = new MyLocationData.Builder()//
					.direction(mCurrentX)//
					.accuracy(location.getRadius())//
					.latitude(location.getLatitude())//
					.longitude(location.getLongitude())//
					.build();
			mBaiduMap.setMyLocationData(data);
			// 设置自定义图标
			MyLocationConfiguration config = new MyLocationConfiguration(
					mLocationMode, true, mIconLocation);
			mBaiduMap.setMyLocationConfigeration(config);

			// 更新经纬度
			mLatitude = location.getLatitude();
			mLongtitude = location.getLongitude();

			if (isFirstIn) {
				LatLng latLng = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
				mBaiduMap.animateMapStatus(msu);
				isFirstIn = false;
				mAdress=location.getAddrStr();
				Toast.makeText(context, location.getAddrStr(),
						Toast.LENGTH_SHORT).show();
			}

		}
	}

}
