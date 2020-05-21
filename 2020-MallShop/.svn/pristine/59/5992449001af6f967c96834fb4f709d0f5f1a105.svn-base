package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.OrderPayBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface PayManagerContract {

    interface View : IBaseView {
        fun onAliPaySuccess()
        fun onCheckOrderPaySuccess(payStatus:Int)
        fun onOrderPaySuccess(result: OrderPayBean.Result)
    }


    interface Presenter : IPresenter<View> {
        fun aliPay(payInfo: String, type: String)
    }
}