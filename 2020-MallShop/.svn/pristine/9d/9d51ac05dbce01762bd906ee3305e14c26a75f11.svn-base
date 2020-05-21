package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.SearchGoods
import com.mike.baselib.base.IPresenter
import com.mike.baselib.base.ListView

interface SearchGoodsListContract {

    interface View : ListView<SearchGoods> {

    }


    interface Presenter : IPresenter<View> {
        fun searchGoodsList(keyword:String,page:Int,shopId:String?=null,type: String)
    }
}