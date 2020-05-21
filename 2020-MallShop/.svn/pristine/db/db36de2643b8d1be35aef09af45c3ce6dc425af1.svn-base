package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.GetShopHomeViewBean
import com.epro.mall.mvp.model.bean.ShopInfo
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface ShopDetailContract {

    interface View : IBaseView {
        fun onGetShopInfoSuccess(shopInfo: ShopInfo)
        fun onFollowShopSuccess()
        fun onGetShopHomeViewSuccess(result: GetShopHomeViewBean.Result)

    }


    interface Presenter : IPresenter<View> {
        fun getShopInfo(shopId:String, type: String)
        fun followShop(shopId: String,type: String)
    }
}