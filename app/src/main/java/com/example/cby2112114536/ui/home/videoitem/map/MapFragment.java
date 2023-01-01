package com.example.cby2112114536.ui.home.videoitem.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.cby2112114536.R;
import com.example.cby2112114536.databinding.FragmentMapBinding;

/**
 * @author breeze
 */
public class MapFragment extends Fragment {

    private FragmentMapBinding binding;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private static final int REQUEST_CODE = 0x4536;
    private LocationClient mLocationClient;
    private PoiSearch mPoiSearch;
    private LatLng latLng;
    private boolean isSearch;

    public class MyPoiOverlay extends PoiOverlay {

        /**
         * 构造函数
         *
         * @param baiduMap 该 PoiOverlay 引用的 BaiduMap 对象
         */
        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int i) {
            if (getPoiResult().getAllPoi() != null) {
                PoiInfo poiInfo = getPoiResult().getAllPoi().get(i);
                if (poiInfo != null) {
                    Toast.makeText(getContext(),
                                    getString(R.string.map_mark_name) + ": " + poiInfo.name + "\n"
//                                    + getString(R.string.map_mark_distance) + ": " + poiInfo.distance + "\n"
//                                    + getString(R.string.map_mark_address) + ": " + poiInfo.address + "\n"
                                    + getString(R.string.map_mark_phone) + ": " + poiInfo.phoneNum,
                                    Toast.LENGTH_LONG)
                            .show();
                }
            }
            return super.onPoiClick(i);
        }
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mMapView == null){
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            mBaiduMap.setMyLocationData(locData);
            if (!isSearch) {
                mPoiSearch.searchNearby(new PoiNearbySearchOption()
                        .location(latLng)
                        .radius(10000)
                        //支持多个关键字并集检索，不同关键字间以$符号分隔，最多支持10个关键字检索。如:”银行$酒店”
                        .keyword("电影"));
                isSearch = true;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        isSearch = false;
        mMapView = binding.bmapView;
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(18.0f);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

        if (root.getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }
        mBaiduMap.setMyLocationEnabled(true);
        LocationClient.setAgreePrivacy(true);
        //定位初始化
        try {
            mLocationClient = new LocationClient(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        // 打开gps
        option.setOpenGps(true);
        // 设置坐标类型
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);

        //设置locationClientOption
        mLocationClient.setLocOption(option);

        //注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
        //开启地图定位图层
        mLocationClient.start();
//        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_mark);
        MyLocationConfiguration mLocationConfiguration = new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.COMPASS, true, null);
        mBaiduMap.setMyLocationConfiguration(mLocationConfiguration);

        mPoiSearch = PoiSearch.newInstance();

        OnGetPoiSearchResultListener listener = new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    mBaiduMap.clear();

                    //创建PoiOverlay对象
                    MyPoiOverlay poiOverlay = new MyPoiOverlay(mBaiduMap);

                    //设置Poi检索数据
                    poiOverlay.setData(poiResult);

                    //将poiOverlay添加至地图并缩放至合适级别
                    poiOverlay.addToMap();
                    poiOverlay.zoomToSpan();
                    mBaiduMap.setOnMarkerClickListener(poiOverlay);
                }
            }
            @Override
            public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

            }
            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
            //废弃
            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }
        };
        mPoiSearch.setOnGetPoiSearchResultListener(listener);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocationClient.restart();
                isSearch = false;
            } else {
                Toast.makeText(getContext(), getString(R.string.video_item_map_nearby_text),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}