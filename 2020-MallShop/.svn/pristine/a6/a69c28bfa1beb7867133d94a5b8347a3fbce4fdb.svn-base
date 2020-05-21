package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.NearlyShop
import com.mike.baselib.base.IPresenter
import com.mike.baselib.base.ListView

interface NearlyShopListContract {

    interface View : ListView<NearlyShop> {

    }


    interface Presenter : IPresenter<View> {
        fun getNearlyShopList(latitude: String, longitude:String,query:String?=null, cityId:String?=null, type: String)
    }
}