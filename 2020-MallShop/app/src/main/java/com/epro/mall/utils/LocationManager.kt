package com.epro.mall.utils

import android.Manifest
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.text.TextUtils
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.epro.mall.R
import com.epro.mall.ui.fragment.OpenLocationServiceBottomPopup
import com.mike.baselib.fragment.BaseBottomPopup
import com.mike.baselib.utils.AppUtils
import com.mike.baselib.utils.LogTools
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

class LocationManager private constructor() {

    companion object {
       // private var mInstance: LocationManager? = null
        const val RC_LOCATION = 100

      //  @Synchronized
//        fun getInstance(): LocationManager {
//            if (mInstance == null) {
//                mInstance = LocationManager()
//            }
//            return mInstance!!
//        }

        fun getInstance(): LocationManager {
            return LocationManager()
        }
    }


    private var locationClient: AMapLocationClient? = null
    private var locationOption: AMapLocationClientOption? = null
    var locationListener: LocationListener? = null
    private var activity: Activity? = null
    var isShowOpenGpsUI = false

    /**
     * 初始化定位
     */
    fun initLocation(activity: Activity, isShowOpenGpsUI: Boolean = false) {
        this.activity = activity
        /**
         * 定位监听
         */
        val aMapLocationListener: AMapLocationListener = AMapLocationListener { location ->
            if (null != location) {
                //定位成功
                LogTools("LocationManager").d("do here1")
                LogTools("LocationManager").d(locationListener)
                if (TextUtils.isEmpty(location.city)) {
                    locationListener?.onLocationError()
                } else {
                    locationListener?.onLocationSuccess(location)
                }
            } else {
                locationListener?.onLocationError()
            }
        }
        //初始化client
        locationClient = AMapLocationClient(activity.applicationContext)
        locationOption = getDefaultOption()
        //设置定位参数
        locationClient?.setLocationOption(locationOption)
        // 设置定位监听
        locationClient?.setLocationListener(aMapLocationListener)
        this.isShowOpenGpsUI = isShowOpenGpsUI
    }

    /**
     * 默认的定位参数
     */
    private fun getDefaultOption(): AMapLocationClientOption {
        val mOption = AMapLocationClientOption()
        mOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.isGpsFirst = false//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.httpTimeOut = 30000//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.interval = 2000//可选，设置定位间隔。默认为2秒
        mOption.isNeedAddress = true//可选，设置是否返回逆地理地址信息。默认是true
        mOption.isOnceLocation = true//可选，设置是否单次定位。默认是false
        mOption.isOnceLocationLatest = false//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP)//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.isSensorEnable = false//可选，设置是否使用传感器。默认是false
        mOption.isWifiScan = true //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.isLocationCacheEnable = true //可选，设置是否使用缓存定位，默认为true
        mOption.geoLanguage = AMapLocationClientOption.GeoLanguage.DEFAULT//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption
    }

    fun startLocation() {
        if (!AppUtils.checkGPSIsOpen(activity!!) && isShowOpenGpsUI) {
            BaseBottomPopup.Builder(OpenLocationServiceBottomPopup()).create().show((activity as FragmentActivity).supportFragmentManager, "open_gps")
        } else {
            // 设置定位参数
            if (locationClient == null) {
                initLocation(activity!!, isShowOpenGpsUI)
            }
            locationClient?.setLocationOption(locationOption)
            // 启动定位
            locationClient?.startLocation()
        }
    }

    /**
     * 停止定位
     *
     */
    fun stopLocation() {
        // 停止定位
        locationClient?.stopLocation()
    }

    /**
     * 销毁定位
     */
    fun destroyLocation() {
        /**
         * 如果AMapLocationClient是在当前Activity实例化的，
         * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
         */
        stopLocation()
        locationClient?.onDestroy()
        locationClient = null
        locationOption = null
        isShowOpenGpsUI = false
    }


    interface LocationListener {
        fun onLocationSuccess(location: AMapLocation)
        fun onLocationError()
    }


    @AfterPermissionGranted(RC_LOCATION)
    fun requestLocation() {
        if (EasyPermissions.hasPermissions(activity!!, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE)) {
            // Already have permission, do the thing
            LogTools.debug("do here1")
            startLocation()
            LogTools.debug("do here2")
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(activity!!, activity!!.getString(R.string.pls_location_parms),
                    RC_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE)
        }
        if (Build.VERSION.SDK_INT > 28 && activity?.getApplicationContext()!!.getApplicationInfo().targetSdkVersion > 28) {

        }
    }
}