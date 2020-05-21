package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.GetShopNewGoodsListBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface PayResultContract {

    interface View : IBaseView {
        fun onGetShopNewGoodsListSuccess(goodsList: ArrayList<GetShopNewGoodsListBean.NewGoods>)
    }


    interface Presenter : IPresenter<View> {

    }
}