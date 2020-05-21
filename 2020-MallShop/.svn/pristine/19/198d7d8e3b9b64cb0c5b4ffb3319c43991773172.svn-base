package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.ScanCodeOrderListDetailBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface ScanCodeOrderListDetailContract {

    interface View : IBaseView{
        fun onScanCodeOrderListDetailSuccess(result: ScanCodeOrderListDetailBean.Result)
    }


    interface Presenter : IPresenter<View> {
        fun ScanCodeOrderListDetail(type: String,orderSn:String)
    }
}