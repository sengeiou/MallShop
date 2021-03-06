package com.epro.mall.ui.activity;

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.amap.api.location.AMapLocation
import com.amap.api.maps.AMap
import com.amap.api.maps.AMap.OnCameraChangeListener
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.UiSettings
import com.amap.api.maps.model.*
import com.amap.api.maps.model.animation.Animation
import com.amap.api.maps.model.animation.TranslateAnimation
import com.amap.api.services.core.AMapException
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.core.PoiItem
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
import com.amap.api.services.route.SearchCity
import com.epro.mall.R
import com.epro.mall.listener.ClosePopGps
import com.epro.mall.mvp.contract.GeolocationSelectContract
import com.epro.mall.mvp.model.bean.City
import com.epro.mall.mvp.model.bean.GeolocationBean
import com.epro.mall.mvp.presenter.GeolocationSelectPresenter
import com.epro.mall.ui.adapter.GeolocationListAdapter
import com.epro.mall.ui.adapter.SearchGeolocationListAdapter
import com.epro.mall.ui.fragment.OpenLocationServiceBottomPopup
import com.epro.mall.utils.LocationManager
import com.epro.mall.utils.MallMapUtils
import com.mike.baselib.activity.BaseTitleBarCustomActivity
import com.mike.baselib.utils.AppUtils
import com.mike.baselib.utils.ext_getFromJsonWithClassKey
import com.mike.baselib.utils.ext_putJsonExtra
import com.mike.baselib.utils.toast
import kotlinx.android.synthetic.main.activity_geolocation_select.*
import org.greenrobot.eventbus.EventBus


class GeolocationSelectActivity : BaseTitleBarCustomActivity<GeolocationSelectContract.View, GeolocationSelectPresenter>(), GeolocationSelectContract.View, LocationManager.LocationListener, AMap.OnMarkerClickListener, View.OnClickListener, PoiSearch.OnPoiSearchListener {
    override fun onClick(p0: View?) {
        when (p0) {
            ivLocation -> {
                //重置地理位置
                // MallBusManager.setLocationInfo(null)
                locationManager.requestLocation()
            }

            tvCancel -> {
                keyword = ""
                etSearch.setText("")
                AppUtils.closeKeyboard(this@GeolocationSelectActivity)
            }
            llLeft->{
                SelectCityActivity.launchForResult(this, FOR_CITY)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Activity.RESULT_OK == resultCode) {
            when (requestCode) {
                FOR_CITY -> {
                    val city = data!!.ext_getFromJsonWithClassKey(City::class.java)
                    tvLocation.text = city!!.city_name
                    cityCode = city.city_code
                    doSearchQuery()
                }
            }
        }
        if (requestCode == OpenLocationServiceBottomPopup.FOR_OPEN_RESULT) {
            if (AppUtils.checkGPSIsOpen(this)) {
                toast("开启成功")
                locationManager.requestLocation()
            }
            val msg = ClosePopGps()
            EventBus.getDefault().postSticky(msg)
        }
    }

    companion object {
        const val TAG = "GeolocationSelect"
        const val FOR_CITY=11
        fun launchForReuslt(activity: Activity, requestCode: Int) {
            activity.startActivityForResult(Intent(activity, GeolocationSelectActivity::class.java), requestCode)
        }
    }

    override fun getPresenter(): GeolocationSelectPresenter {
        return GeolocationSelectPresenter()
    }

    override fun onGeolocationSelectSuccess() {
    }


    override fun layoutContentId(): Int {
        return R.layout.activity_geolocation_select
    }

    override fun initData() {

    }

    var aMap: AMap? = null
    var mUiSettings: UiSettings? = null
    var marker: Marker? = null
    var latlng: LatLng= LatLng(0.toDouble(),0.toDouble())
    var cityCode:String=""
    var location:AMapLocation?=null
    var locationManager:LocationManager= LocationManager.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapView!!.onCreate(savedInstanceState) // 此方法必须重写
    }

    var mAdapter: GeolocationListAdapter? = null
    var searchAdapter: SearchGeolocationListAdapter? = null
    override fun initView() {
        super.initView()
        getTitleView().text = "确认收货地址"
        aMap = mapView.map
        mUiSettings = aMap!!.uiSettings
        aMap!!.setOnMarkerClickListener(this)
        mUiSettings!!.isZoomControlsEnabled = false
        locationManager.initLocation(this,true)
        locationManager.requestLocation()
        initMapView()
        rvMapView.layoutManager = LinearLayoutManager(this)
        mAdapter = GeolocationListAdapter(this, ArrayList())
        rvMapView.adapter = mAdapter
        rvSearchView.layoutManager = LinearLayoutManager(this)
        searchAdapter = SearchGeolocationListAdapter(this, ArrayList())
        rvSearchView.adapter = searchAdapter

        mAdapter?.onItemClickListener = object : GeolocationListAdapter.OnItemClickListener {
            override fun onClick(item: GeolocationBean) {
                // MallBusManager.setLocationInfo(item)
                setResult(Activity.RESULT_OK, Intent().ext_putJsonExtra(item))
                finish()
            }
        }
        searchAdapter?.onItemClickListener = object : SearchGeolocationListAdapter.OnItemClickListener {
            override fun onClick(item: GeolocationBean) {
                setResult(Activity.RESULT_OK, Intent().ext_putJsonExtra(item))
                finish()
            }
        }

        llLeft.isEnabled=true
    }

    private fun initMapView() {

        aMap!!.setOnMapLoadedListener {
            addMarkerInScreenCenter()
        }

        // 设置可视范围变化时的回调的接口方法
        aMap!!.setOnCameraChangeListener(object : OnCameraChangeListener {
            override fun onCameraChange(position: CameraPosition) {}
            override fun onCameraChangeFinish(postion: CameraPosition) { //屏幕中心的Marker跳动
                latlng= LatLng(postion.target.latitude,postion.target.longitude)
                startJumpAnimation()
            }
        })
    }

    private fun startJumpAnimation() {
        if (screenMarker != null) { //根据屏幕距离计算需要移动的目标点
            val latLng = screenMarker!!.position
            logTools.t("YB").d("startJumpAnimation location :" + latLng!!.latitude + " longitude: " + latLng!!.longitude)
            val point = aMap!!.projection.toScreenLocation(latLng)
            point.y -= dip2px(this, 125f)
            val target = aMap!!.projection
                    .fromScreenLocation(point)
            //使用TranslateAnimation,填写一个需要移动的目标点
            val animation: Animation = TranslateAnimation(target)
            animation.setInterpolator { input ->
                // 模拟重加速度的interpolator
                if (input <= 0.5) {
                    (0.5f - 2 * (0.5 - input) * (0.5 - input)).toFloat()
                } else {
                    (0.5f - Math.sqrt((input - 0.5f) * (1.5f - input).toDouble())).toFloat()
                }
            }
            //整个移动所需要的时间
            animation.setDuration(600)
            //设置动画
            screenMarker!!.setAnimation(animation)
            //开始动画
            screenMarker!!.startAnimation()
            //开始搜索
            doNearSearchQuery()
        } else {
            Log.e("amap", "screenMarker is null")
        }
    }

    //dip和px转换
    private fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    var screenMarker: Marker? = null
    private fun addMarkerInScreenCenter() {
        val latLng = aMap!!.cameraPosition.target
        val screenPosition = aMap!!.projection.toScreenLocation(latLng)
        logTools.t("YB").d("addMarkerInScreenCenter location :" + latLng!!.latitude + " longitude: " + latLng!!.longitude)
        screenMarker = aMap!!.addMarker(MarkerOptions()
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.group_1)))
        //设置Marker在屏幕上,不跟随地图移动
        screenMarker!!.setPositionByPixels(screenPosition.x, screenPosition.y)
    }

    override fun initListener() {
        ivLocation.setOnClickListener(this)
        logTools.t("YB").d("initListener()")
        locationManager.locationListener = this
        tvCancel.setOnClickListener(this)
        etSearch.addTextChangedListener(textWatcher)
        llLeft.setOnClickListener(this)
    }

    override fun onLocationSuccess(location: AMapLocation) {
        logTools.t("YB").d("onLocationSuccess location :" + location.latitude + " longitude: " + location.longitude)
        latlng = LatLng(location.latitude, location.longitude)
//        if (MallBusManager.getLocationInfo()!= null){
//            logTools.t("YB").d("onLocationSuccess location 111 :" + MallBusManager.getLocationInfo()!!.latitude + " longitude: " +  MallBusManager.getLocationInfo()!!.longitude)
//            aMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(MallBusManager.getLocationInfo()!!.latitude, MallBusManager.getLocationInfo()!!.longitude), 18f))
//        }else{
//            logTools.t("YB").d("onLocationSuccess location 222 :" + location.latitude + " longitude: " + location.longitude)
//            aMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 18f))
//        }
        aMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 18f))
        tvLocation.text = location.city
        this.location=location
        doNearSearchQuery()
    }

    override fun onLocationError() {
        logTools.t("YB").d("onLocationError: ")
        toast(getString(R.string.location_failed))
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        if (aMap != null) {
            MallMapUtils.jumpPoint(marker, aMap!!.projection)
        }
        marker.showInfoWindow()
        return true
    }

    /**
     * 开始进行poi搜索
     */
    private var currentPage: Int = 0
    private var poiSearch: PoiSearch? = null // POI搜索
    private var query: PoiSearch.Query? = null // Poi查询条件类
    private var keyword = ""
    fun doSearchQuery() {
        val mType = "汽车服务|汽车销售|汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施"
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        logTools.t("YB").d("doSearchQuery() cityCode: "+cityCode)
        query = PoiSearch.Query(keyword, mType, cityCode)
        currentPage = 1
        query!!.pageSize = 30
        query!!.pageNum = currentPage
        poiSearch = PoiSearch(this, query)
        poiSearch!!.setOnPoiSearchListener(this)
        if(TextUtils.isEmpty(keyword)){  //附近1000米搜索
            poiSearch!!.bound = PoiSearch.SearchBound(LatLonPoint(latlng!!.latitude, latlng!!.longitude), 1000, true)
        }
        poiSearch!!.searchPOIAsyn()
    }

    fun doNearSearchQuery(){
        if(location==null){
            toast("定位失败")
            return
        }
        keyword=""
        cityCode=location!!.cityCode
        doSearchQuery()
    }

    private var poiResult: PoiResult? = null // poi返回的结果
    override fun onPoiSearched(result: PoiResult, rCode: Int) {
        logTools.t("YB").d("onPoiSearched result.pois :${result.pois} rCode :$rCode")
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result.query != null) { // 搜索poi的结果
                if (result.query == query) { // 是否是同一条
                    poiResult = result
                    // 取得搜索到的poiitems有多少页
                    val poiItems: List<PoiItem> = poiResult!!.pois // 取得第一页的poiitem数据，页数从数字0开始
                    val tem = ArrayList<GeolocationBean>()
                    if (poiItems.isNotEmpty()) {
                        for (i in poiItems) {
                            logTools.t("YB").d("poiItems.isNotEmpty()"+i)
                            //logTools.t("yb").d("poiItems : title " + i.title + " cityName: " + i.cityName + " businessArea: " + i.businessArea + " province: " + i.provinceName + " adName: " + i.adName + " distance: " + i.distance + " snippet: " + i.snippet + " latitude: " + i.latLonPoint.latitude + " longitude: " + i.latLonPoint.longitude)
                            val bean = GeolocationBean(i.title, i.snippet, i.latLonPoint.latitude.toString(),
                                    i.latLonPoint.longitude.toString(), i.distance, i.adName, i.cityName, i.provinceName)
                            tem.add(bean)
                        }
                        if (TextUtils.isEmpty(keyword)) {
                            rvMapView.visibility = View.VISIBLE
                            mAdapter!!.mData.clear()
                            mAdapter!!.setData(tem)
                            rvMapView.scrollToPosition(0)
                        } else {
                            if(location==null){
                                toast("定位失败")
                                return
                            }
                            rvSearchView.visibility = View.VISIBLE
                            searchAdapter!!.keyword = keyword
                            searchAdapter!!.latitude=location!!.latitude
                            searchAdapter!!.longtitude=location!!.longitude
                            searchAdapter!!.mData.clear()
                            searchAdapter!!.setData(tem)
                        }
                    } else {
                        if (TextUtils.isEmpty(keyword)) {
                            rvMapView.visibility = View.GONE
                        } else {
                            rvSearchView.visibility = View.GONE
                        }
                    }
                }
            }
        }else{
            logTools.t("YB").d("result :$result rCode :$rCode")
        }
    }

    override fun onPoiItemSearched(p0: PoiItem?, p1: Int) {

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

    val textWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            if (!TextUtils.isEmpty(p0.toString())) {
                rlPoiSearch.visibility = View.VISIBLE
                tvCancel.visibility = View.VISIBLE
                keyword = p0.toString()
                logTools.t("YB").d("afterTextChanged () doSearchQuery ")
                doSearchQuery()
            } else {
                rlPoiSearch.visibility = View.GONE
                tvCancel.visibility = View.GONE
                doNearSearchQuery()
            }
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

    }


}


