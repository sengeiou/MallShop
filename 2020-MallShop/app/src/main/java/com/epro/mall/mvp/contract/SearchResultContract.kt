package com.epro.mall.mvp.contract

import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface SearchResultContract {

    interface View : IBaseView {
        fun onSearchAllSuccess()
        fun onSearchInShopSuccess()

    }


    interface Presenter : IPresenter<View> {
        fun searchAll(keyword:String,type: String)
        fun searchInShop(shopId:String,keyword: String, type: String)
    }
}