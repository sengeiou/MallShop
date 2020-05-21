package com.epro.mall.ui.activity;

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.amap.api.location.AMapLocation
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.*
import com.epro.mall.R
import com.epro.mall.mvp.contract.FindShopContract
import com.epro.mall.mvp.model.bean.Location
import com.epro.mall.mvp.model.bean.ShopLocation
import com.epro.mall.mvp.presenter.FindShopPresenter
import com.epro.mall.ui.fragment.SelectMapBottomPopup
import com.epro.mall.ui.fragment.ShopMapInfoBottomPopup
import com.epro.mall.utils.LocationManager
import com.epro.mall.utils.MallMapUtils
import com.google.gson.reflect.TypeToken
import com.mike.baselib.activity.BaseTitleBarCustomActivity
import com.mike.baselib.fragment.BaseBottomPopup
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.toast
import kotlinx.android.synthetic.main.activity_find_shop.*

/**
 * 发现页面
 */
class FindShopActivity : BaseTitleBarCustomActivity<FindShopContract.View, FindShopPresenter>(), FindShopContract.View, View.OnClickListener, AMap.OnMarkerClickListener, LocationManager.LocationListener {
    override fun onLocationSuccess(location: AMapLocation) {
        for(m in aMap?.mapScreenMarkers!!){
            if(m.title=="my_location"){
                m.remove()
            }
        }
        val markerOption = MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .title("my_location")
                .position(LatLng(location.latitude,location.longitude))
                .draggable(true)
        aMap?.addMarker(markerOption)
        aMap?.moveCamera(CameraUpdateFactory.newLatLng(LatLng(location.latitude,location.longitude)))
    }

    override fun onLocationError() {
        toast(getString(R.string.location_failed))
    }

    companion object {
        const val TAG = "FindShop"
        const val LOCATION_LIST = "location_list"
        const val RC_LOCATION = 100
        fun launchWithShopLocationList(context: Context, shopLocationList: ArrayList<ShopLocation>) {
            context.startActivity(Intent(context, FindShopActivity::class.java).putExtra(LOCATION_LIST, AppBusManager.toJson(shopLocationList)))
        }
    }

    override fun getPresenter(): FindShopPresenter {
        return FindShopPresenter()
    }

    override fun onFindShopSuccess() {
    }


    override fun layoutContentId(): Int {
        return R.layout.activity_find_shop
    }

    override fun initData() {
        zoomToSpanWithCenter()
    }

    val pointList = ArrayList<LatLng>()
    var centerMarker: Marker? = null
    var centerPoint: LatLng? = null
    var shopLocationList = ArrayList<ShopLocation>()
    var centerShopLocation: ShopLocation? = null
    var locationManager:LocationManager= LocationManager.getInstance()
    override fun initView() {
        super.initView()
        getTitleView().text = getString(R.string.geolocation)
        val type = object : TypeToken<ArrayList<ShopLocation>>() {}.type
        val json = intent.getStringExtra(LOCATION_LIST)
        shopLocationList = AppBusManager.fromJson(json, type)!!
        centerShopLocation = shopLocationList[0]
        latlng = LatLng(shopLocationList[0].latitude.toDouble(), shopLocationList[0].longitude.toDouble())
        centerPoint = latlng
        pointList.add(latlng!!)
        for (i in shopLocationList.indices) {
            if (i > 0) {
                pointList.add(LatLng(shopLocationList[i].latitude.toDouble(), shopLocationList[i].longitude.toDouble()))
            }
        }
        /**
         * 设置离线地图存储目录，在下载离线地图或初始化地图设置; 使用过程中可自行设置, 若自行设置了离线地图存储的路径，
         * 则需要在离线地图下载和使用地图页面都进行路径设置
         * Demo中为了其他界面可以使用下载的离线地图，使用默认位置存储，屏蔽了自定义设置
         */

        // MapsInitializer.sdcardDir =OffLineMapUtils.getSdCacheDir(this);
        aMap = mapView!!.map
        aMap!!.setOnMarkerClickListener(this)
        aMap!!.uiSettings.isZoomControlsEnabled = false
        aMap!!.setInfoWindowAdapter(InfoPopupWindow(this))
        addMarkersToMap()// 往地图上添加marker


        tvShopName.text = centerShopLocation!!.shopName
        tvAddress.text = centerShopLocation!!.address

        locationManager.initLocation(this)
    }

    override fun initListener() {
        clearMap.setOnClickListener(this)
        resetMap.setOnClickListener(this)
        ivGohere.setOnClickListener(this)
        ivLocation.setOnClickListener(this)
        locationManager.locationListener = this
    }


    val markers = ArrayList<MarkerOptions>()
    private var aMap: AMap? = null
    private var latlng: LatLng? = LatLng(0.toDouble(), 0.toDouble())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapView!!.onCreate(savedInstanceState) // 此方法必须重写
    }


    /**
     * 方法必须重写
     */
    override fun onResume() {
        super.onResume()
        mapView!!.onResume()
    }

    /**
     * 方法必须重写
     */
    override fun onPause() {
        super.onPause()
        mapView!!.onPause()
    }

    /**
     * 方法必须重写
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView!!.onSaveInstanceState(outState)
    }

    /**
     * 方法必须重写
     */
    override fun onDestroy() {
        super.onDestroy()
        mapView!!.onDestroy()
        locationManager.destroyLocation()
    }

    /**
     * 在地图上添加marker
     */
    private fun addMarkersToMap() {
        for (i in pointList.indices) {
            val markerOption = MarkerOptions().icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    .title(shopLocationList[i].shopName)
                    .position(pointList[i])
                    .draggable(true)
            markers.add(markerOption)
        }
        val markerList = aMap!!.addMarkers(markers, false)
        centerMarker = markerList[0]
    }

    /**
     * 对marker标注点点击响应事件
     */
    override fun onMarkerClick(marker: Marker): Boolean {
        if (aMap != null) {
            MallMapUtils.jumpPoint(marker,aMap!!.projection)
        }
        // showBottomPopup()
        marker.showInfoWindow()
        return true
    }


    override fun onClick(v: View) {
        when (v.id) {
            /**
             * 清空地图上所有已经标注的marker
             */
            R.id.clearMap -> if (aMap != null) {
                aMap!!.clear()
            }
            /**
             * 重新标注所有的marker
             */
            R.id.resetMap -> if (aMap != null) {
                aMap!!.clear()
                addMarkersToMap()
            }

            R.id.ivGohere -> {
                showSelectMapBottomPopup()
            }
            R.id.ivLocation -> {
                locationManager.requestLocation()
            }
            else -> {
            }
        }
    }

    private fun showSelectMapBottomPopup() {
        val location=Location(centerShopLocation!!.address,centerShopLocation!!.latitude,centerShopLocation!!.longitude)
        BaseBottomPopup.Builder(SelectMapBottomPopup.newInstance(location)).create().show(supportFragmentManager, "select_map")
    }

    /**
     * 缩放移动地图，保证所有自定义marker在可视范围中，且地图中心点不变。
     */
    fun zoomToSpanWithCenter() {
        if (pointList.size > 0) {
            if (aMap == null)
                return
            centerMarker?.setVisible(true)
            centerMarker?.showInfoWindow()
            val bounds = MallMapUtils.getLatLngBounds(centerPoint, pointList)
            aMap?.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50))
        }
    }


    /**
     * 缩放移动地图，保证所有自定义marker在可视范围中。
     */
    fun zoomToSpan() {
        if (pointList.size > 0) {
            if (aMap == null)
                return
            centerMarker?.setVisible(false)
            val bounds = MallMapUtils.getLatLngBounds(pointList)
            aMap?.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50))
        }
    }



    private fun showBottomPopup() {
        BaseBottomPopup.Builder(ShopMapInfoBottomPopup())
                .setBackgroundTransparent(true).create().show(supportFragmentManager, "shop_map_info")

    }

    inner class InfoPopupWindow(context: Context) : AMap.InfoWindowAdapter {
        var context: Context? = null

        init {
            this.context = context
        }

        override fun getInfoContents(p0: Marker?): View? {
            return null
        }

        override fun getInfoWindow(p0: Marker?): View {
            val view = LayoutInflater.from(context).inflate(R.layout.popup_shop_mapinfo, null)
            val tvShopName = view.findViewById<TextView>(R.id.tvShopName)
            tvShopName.text = p0?.title.toString()
            return view
        }
    }
}


