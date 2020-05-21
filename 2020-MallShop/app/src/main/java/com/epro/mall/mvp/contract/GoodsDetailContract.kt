package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.Goods
import com.epro.mall.mvp.model.bean.ShopInfo
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface GoodsDetailContract {

    interface View : IBaseView {
        fun onGetGoodsDetailSuccess(goods:Goods)
        fun onAddToCartSuccess()
        fun onGetShopInfoSuccess(shopInfo: ShopInfo)
    }


    interface Presenter : IPresenter<View> {
        fun getGoodsDetail(goodsId:String,type: String)
        fun addToCart(productId:String,number:Int,type: String)
        fun getShopInfo(shopId: String, type: String)
    }
}