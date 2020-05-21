package com.epro.mall.mvp.presenter

import android.app.Activity
import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.mall.mvp.contract.OrderInfoContract
import com.epro.mall.mvp.model.OrderInfoModel
import com.epro.mall.mvp.model.bean.CartGoods
import com.epro.mall.mvp.model.bean.CreateOrderBean
import com.mike.baselib.net.exception.ErrorStatus

class OrderInfoPresenter : BasePresenter<OrderInfoContract.View>(), OrderInfoContract.Presenter {


    private val model by lazy { OrderInfoModel() }

    fun createOrder(send: CreateOrderBean.Send, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.createOrder(send)
                .subscribe({ bean ->
                    mRootView?.onCreateOrderSuccess(bean.result)
                     mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

    override fun getShopInfo(shopId: String, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getShopInfo(shopId)
                .subscribe({ bean ->
                    mRootView?.onGetShopInfoSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS, type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

    override fun getAddressList(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getAddressList("0")
                .subscribe({ bean ->
                    mRootView?.onGetAddressListSuccess(bean.result)
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