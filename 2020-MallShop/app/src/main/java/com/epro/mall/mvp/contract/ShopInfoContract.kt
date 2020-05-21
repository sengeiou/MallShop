package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.ShopInfo
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface ShopInfoContract {

    interface View : IBaseView {
        fun onGetShopInfoSuccess(shopInfo: ShopInfo)

    }

    interface Presenter : IPresenter<View> {
        fun getShopInfo(shopId: String, type: String)
    }
}