package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.*
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface OrderInfoContract {

    interface View : IBaseView {
        fun onCreateOrderSuccess(result:CreateOrderBean.Result)
        fun onGetShopInfoSuccess(shopInfo: ShopInfo)
        fun onGetAddressListSuccess(addressList:ArrayList<Address>)

    }


    interface Presenter : IPresenter<View> {
        fun getAddressList(type: String)
        fun getShopInfo(shopId:String, type: String)
    }
}