package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.CancelOrderBean
import com.epro.mall.mvp.model.bean.Order
import com.epro.mall.mvp.model.bean.OrderListBean
import com.mike.baselib.base.IPresenter
import com.mike.baselib.base.ListView

interface OrderListContract {

    interface View : ListView<Order> {
        fun onOrderListSuccess(result:OrderListBean.Result)
        fun cancelOrderSuccess(result: String)
        fun deleteOrderSuccess(result: String)
        fun onOrderAgainSuccess(productIds:ArrayList<String>)
    }


    interface Presenter : IPresenter<View> {
        fun getOrderList(type: String,orderStatus:String,page:Int)
        fun cancelOrder(type:String,orderId:String,reason:String)
        fun deleteOrder(type:String,orderId:String)
    }
}