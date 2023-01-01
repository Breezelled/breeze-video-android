package com.example.cby2112114536.common;

import androidx.multidex.MultiDexApplication;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

import cn.bmob.v3.Bmob;

/**
 * @author breeze
 */
public class MyApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "a934eab42c86c5b3a6fa53e45c3c57c1");
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.setAgreePrivacy(getApplicationContext(),true);
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }
}
