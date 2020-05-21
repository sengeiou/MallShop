package com.epro.mall.mvp.presenter

import com.mike.baselib.base.ListPresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.mall.mvp.model.bean.Order
import com.epro.mall.mvp.contract.OrderListContract
import com.epro.mall.mvp.model.OrderListModel
import com.mike.baselib.net.exception.ErrorStatus

class OrderListPresenter : ListPresenter<Order, OrderListContract.View>(), OrderListContract.Presenter {
    override fun cancelOrder(type: String,orderId:String,reason:String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.cancelOrder(orderId,reason)
                .subscribe({bean->
                    mRootView?.cancelOrderSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                },{
                    throwable ->
                    mRootView?.showError(ExceptionHandle.handleException(throwable),ExceptionHandle.errorCode,type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    override fun deleteOrder(type: String, orderId: String) {
        checkViewAttached()
        mRootView?.showLoading(type)

        val disposable = model.deleteOrder(orderId)
                .subscribe({
                    bean->
                    mRootView?.deleteOrderSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                },{
                    throwable ->
                    mRootView?.showError(ExceptionHandle.handleException(throwable),ExceptionHandle.errorCode,type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    private val model by lazy { OrderListModel() }

    override fun getOrderList(type: String,orderStatus:String,page:Int) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getOrderList(orderStatus,page)
                .subscribe({ bean ->
                    mRootView?.getListDataSuccess(bean.result.rows, type)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    mRootView?.showError(ExceptionHandle.handleException(throwable), ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    fun orderAgain(orderSn: String,type: String){
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.orderAgain(orderSn)
                .subscribe({ bean ->
                    mRootView?.onOrderAgainSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }
}
