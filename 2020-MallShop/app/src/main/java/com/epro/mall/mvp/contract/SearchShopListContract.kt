package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.SearchShop
import com.mike.baselib.base.IPresenter
import com.mike.baselib.base.ListView

interface SearchShopListContract {

    interface View : ListView<SearchShop> {

    }


    interface Presenter : IPresenter<View> {
    }
}