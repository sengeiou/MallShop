package com.epro.mall.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.mall.mvp.contract.OrderDetailContract
import com.epro.mall.mvp.model.OrderDetailModel
import com.epro.mall.mvp.model.bean.OrderDetailBean
import com.mike.baselib.net.exception.ErrorStatus

class OrderDetailPresenter : BasePresenter<OrderDetailContract.View>(), OrderDetailContract.Presenter {

    private val model by lazy { OrderDetailModel() }

    override fun getOrderDetail(type: String,orderId:String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getOrderDetail(orderId)
                .subscribe({ bean ->
                    mRootView?.onOrderDetailSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    override fun cancelOrder(type: String,orderId:String,reason:String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.cancelOrder(orderId,reason)
                .subscribe({bean->
                    mRootView?.cancelOrderSuccess()
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
                    mRootView?.deleteOrderSuccess()
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                },{
                    throwable ->
                    mRootView?.showError(ExceptionHandle.handleException(throwable),ExceptionHandle.errorCode,type)
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