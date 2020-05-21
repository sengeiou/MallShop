package com.epro.mall.mvp.contract

import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface SearchContract {

    interface View : IBaseView {
        fun onGetSearchSuggestsSuccess()
        fun onSearchAssociateSuccess(list:ArrayList<String>)

    }

    interface Presenter : IPresenter<View> {
        fun getSearchSuggests(type: String)
    }
}