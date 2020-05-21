package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.GetGoodsByBarcodeBean
import com.epro.mall.mvp.model.bean.ScanBuyCartGoods
import com.epro.mall.mvp.model.bean.ShopInfo
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface ScanBuyCartContract {

    interface View : IBaseView {
        fun onGetGoodsByBarcodeSuccess(cartGoods:ScanBuyCartGoods)
        fun onGetShopInfoSuccess(shopInfo: ShopInfo)
    }


    interface Presenter : IPresenter<View> {
        fun getGoodsByBarcode(shopId: String, productBarCode:String,type: String)
        fun getShopInfo(shopId:String, type: String)
    }
}