package com.epro.mall.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.amap.api.maps.model.LatLng
import com.epro.mall.R
import com.epro.mall.listener.RefreshHomePageFinishEvent
import com.epro.mall.mvp.contract.HomeShopListContract
import com.epro.mall.mvp.model.bean.AdBanner
import com.epro.mall.mvp.model.bean.HomeShop
import com.epro.mall.mvp.presenter.HomeShopListPresenter
import com.epro.mall.ui.activity.ShopDetailActivity
import com.epro.mall.ui.adapter.HomeShopListAdapter
import com.epro.mall.utils.LocationManager
import com.epro.mall.utils.MallConst
import com.mike.baselib.fragment.BaseTitleBarListFragment
import com.mike.baselib.listener.RefreshEvent
import com.mike.baselib.utils.DisplayManager
import com.mike.baselib.utils.ext_loadGif
import com.mike.baselib.view.recyclerview.MultipleType
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 首页中店铺列表页面
 */
@Deprecated("废弃")
class HomeShopListFragment : BaseTitleBarListFragment<HomeShop, HomeShopListContract.View
        , HomeShopListPresenter, HomeShopListAdapter>(), HomeShopListContract.View {

    override fun onGetBannerListSuccess(banners: ArrayList<AdBanner>) {
        if(banners.isNotEmpty()){
            logTools.toJson(banners[0])
            listDataAdapter?.bannerList!!.clear()
            listDataAdapter?.bannerList!!.addAll(banners)
            listDataAdapter?.notifyItemChanged(0)
        }
    }

    companion object {
        const val TAG = "HomeShopList"
        fun newInstance(str: String): HomeShopListFragment {
            val args = Bundle()
            args.putString(TAG, str)
            val fragment = HomeShopListFragment()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(): HomeShopListFragment {
            return newInstance("")
        }
    }

    override fun lazyLoad() {
        //super.lazyLoad()
    }

    override fun getListAdapter(list: ArrayList<HomeShop>): HomeShopListAdapter {
        list.add(HomeShop(ArrayList(), ArrayList(),"",0,"","","",0,"",""))
        return HomeShopListAdapter(activity!!, list, ArrayList(), object : MultipleType<HomeShop> {
            override fun getLayoutId(item: HomeShop, position: Int): Int {
                return if (position == 0) {
                    R.layout.item_homeshoplist_header
                } else {
                    R.layout.item_homeshoplist2
                }
            }
        })
    }

     fun refresh(){
         isRefresh=true
         page=1
         getListData()
         mPresenter.getBannerList(0,!isRefresh)
    }

    override fun getListData() {
        val latLng = arguments?.getParcelable<LatLng>("LatLng")
        logTools.d(latLng)
        if (latLng != null&&latLng.longitude!=0.toDouble()&&latLng.latitude!=0.toDouble()) {
            mPresenter.getHomeShopList(latLng.latitude.toString(), latLng.longitude.toString(),!isRefresh)
        }else{
            mPresenter.getHomeShopList("", "",!isRefresh)
        }
    }

    override fun dismissLoading(errorMsg: String, errorCode: Int, type: String) {
        if (type == MallConst.GET_HOME_SHOP_LIST) {
            super.dismissLoading(errorMsg, errorCode, type)
        } else {
            getMultipleStatusView().showContent()
        }
        EventBus.getDefault().post(RefreshHomePageFinishEvent())
    }

    override fun initData() {
       // mPresenter.getBannerList(0)
    }

    override fun getPresenter(): HomeShopListPresenter {
        return HomeShopListPresenter()
    }

    @SuppressLint("RestrictedApi")
    override fun initView() {
        super.initView()
        getTitleBar().visibility = View.GONE
        (getRefreshView().refreshHeader!! as ClassicsHeader)
                .setPrimaryColorId(R.color.transparent)
                //.setProgressResource(R.mipmap.gif_refresh_white)
                .setAccentColor(resources.getColor(R.color.white))
        val progressView = (getRefreshView().refreshHeader as ClassicsHeader).findViewById<ImageView>(com.scwang.smartrefresh.layout.R.id.srl_classics_progress)
        progressView.ext_loadGif(R.mipmap.gif_refresh_white)
        getRvListView().setBackgroundResource(R.color.transparent)
        getRefreshView().setEnableRefresh(false)
        val padding = DisplayManager.dip2px(6F)!!
        //  getRvListView().setPadding(padding,padding,padding,0)
        getRefreshView().setEnableLoadMore(false)
        listDataAdapter?.onItemClickListener = object : HomeShopListAdapter.OnItemClickListener {
            override fun onClick(item: HomeShop) {
                ShopDetailActivity.launchWithShopId(activity!!, item.shopId.toString())
            }
        }

    }

    override fun initListener() {
    }

    private var isRefresh = false
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun onRefreshEvent(event: RefreshEvent) {
//        if (event.tag == HomeFragment::class.java.simpleName) {
//            isRefresh = true
//            mPresenter.getBannerList(0)
//        }
//    }

    override fun showLoading(type: String) {
        if (!isRefresh) {
            super.showLoading(type)
        }
    }
}


