package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.ScanCodeOrderListOneBean
import com.mike.baselib.base.IPresenter
import com.mike.baselib.base.ListView

interface ScanCodeOrderListContract {

    interface View : ListView<ScanCodeOrderListOneBean> {

    }


    interface Presenter : IPresenter<View> {
        fun ScanCodeOrderList(type: String,shopId:String,page:Int)
    }
}