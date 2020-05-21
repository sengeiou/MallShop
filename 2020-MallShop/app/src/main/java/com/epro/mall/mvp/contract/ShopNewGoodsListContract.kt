package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.GetShopNewGoodsListBean
import com.epro.mall.mvp.model.bean.Goods
import com.mike.baselib.base.IPresenter
import com.mike.baselib.base.ListView

interface ShopNewGoodsListContract {

    interface View : ListView<GetShopNewGoodsListBean.NewGoods> {
    }




    interface Presenter : IPresenter<View> {

    }
}