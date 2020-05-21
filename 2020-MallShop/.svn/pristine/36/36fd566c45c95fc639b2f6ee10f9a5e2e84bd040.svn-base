package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.ShopCart
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface CartContract {

    interface View : IBaseView {
        fun onGetCartGoodsListSuccess(shopCarts:ArrayList<ShopCart>)
        fun onModifyCartGoodsSuccess()
        fun onDeleteCartGoodsSuccess()
    }


    interface Presenter : IPresenter<View> {
        fun getCartGoodsList(type: String)
        fun modifyCartGoods(productId:String,number:Int,type: String)
        fun deleteCartGoods(productId:String,type: String)
    }
}