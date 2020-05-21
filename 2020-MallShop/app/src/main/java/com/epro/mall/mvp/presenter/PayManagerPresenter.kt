package com.epro.mall.mvp.presenter

import android.app.Activity
import android.support.v4.app.Fragment
import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.mall.mvp.contract.PayManagerContract
import com.epro.mall.mvp.model.PayManagerModel
import com.mike.baselib.net.exception.ErrorStatus
import java.io.FileReader

class PayManagerPresenter : BasePresenter<PayManagerContract.View>(), PayManagerContract.Presenter {

    private val model by lazy { PayManagerModel() }

    override fun aliPay(payInfo: String, type: String) {
        checkViewAttached()
        //mRootView?.showLoading(type)
        var activity:Activity?=null
        if(mRootView is Activity){
            activity=mRootView as Activity
        }else if(mRootView is Fragment){
            activity=(mRootView as Fragment).activity
        }
        val disposable = model.aliPay(payInfo, activity!!)
                .subscribe({ bean ->
                    mRootView?.onAliPaySuccess()
                    //  mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

    fun checkOrderPay(orderSn: String, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.checkOrderPay(orderSn)
                .subscribe({ bean ->
                    mRootView?.onCheckOrderPaySuccess(bean.checkResult!!.payStatus)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS, type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

    fun orderPay(orderSn: String, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.orderPay(orderSn)
                .subscribe({ bean ->
                    mRootView?.onOrderPaySuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS, type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

}