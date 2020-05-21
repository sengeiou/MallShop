package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.AdBanner
import com.epro.mall.mvp.model.bean.HomeShop
import com.mike.baselib.base.IPresenter
import com.mike.baselib.base.ListView

interface HomeContract {

    interface View : ListView<HomeShop> {
        fun onGetCartGoodsCountSuccess(count: Int)
        fun onGetBannerListSuccess(banners: ArrayList<AdBanner>)
    }


    interface Presenter : IPresenter<View> {
        fun getCartGoodsCount(type: String)
    }
}