package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.OrderDetailBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface OrderDetailContract {

    interface View : IBaseView {
        fun onOrderDetailSuccess(result:OrderDetailBean.Result)
        fun cancelOrderSuccess()
        fun deleteOrderSuccess()
        fun onOrderAgainSuccess(productIds:ArrayList<String>)
    }


    interface Presenter : IPresenter<View> {
        fun getOrderDetail(type: String,orderId:String)
        fun cancelOrder(type:String,orderId:String,reason:String)
        fun deleteOrder(type:String,orderId:String)
    }
}