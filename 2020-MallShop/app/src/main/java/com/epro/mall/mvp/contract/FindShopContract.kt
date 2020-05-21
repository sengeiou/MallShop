package com.epro.mall.mvp.contract

import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface FindShopContract {

    interface View : IBaseView {
        fun onFindShopSuccess()

    }


    interface Presenter : IPresenter<View> {
        fun getFindShop(type: String)
    }
}