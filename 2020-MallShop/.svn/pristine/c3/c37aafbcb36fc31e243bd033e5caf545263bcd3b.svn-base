package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.GetShopHomeViewBean
import com.epro.mall.mvp.model.bean.Goods
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface ShopHomeContract {

    interface View : IBaseView {
        fun onGetShopGoodsListHotSuccess(goodsList: ArrayList<Goods>)
        fun onGetShopGoodsListRecommendSuccess(goodsList: ArrayList<Goods>)
    }


    interface Presenter : IPresenter<View> {
        fun getShopGoodsListHot(shopId: String, type: String)
        fun getShopGoodsListRecommend(shopId: String, type: String)
    }
}