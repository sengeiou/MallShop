package com.epro.mall.mvp.presenter

import com.epro.mall.mvp.contract.ScanBuyOrderInfoContract
import com.epro.mall.mvp.model.ScanBuyOrderInfoModel
import com.epro.mall.mvp.model.bean.CreateScanBuyOrderBean
import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.net.exception.ExceptionHandle

class ScanBuyOrderInfoPresenter : BasePresenter<ScanBuyOrderInfoContract.View>(), ScanBuyOrderInfoContract.Presenter {

    private val model by lazy { ScanBuyOrderInfoModel() }

    fun createScanBuyOrder(send: CreateScanBuyOrderBean.Send, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.createScanBuyOrder(send)
                .subscribe({ bean ->
                    mRootView?.onCreateScanBuyOrderSuccess(bean.result)
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