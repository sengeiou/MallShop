package com.epro.mall.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.mall.mvp.contract.ScanBarPurchaseContract
import com.epro.mall.mvp.model.ScanBarPurchaseModel
import com.mike.baselib.net.exception.ErrorStatus

class ScanBarPurchasePresenter : BasePresenter<ScanBarPurchaseContract.View>(), ScanBarPurchaseContract.Presenter {

    private val model by lazy { ScanBarPurchaseModel() }

    override fun ScanBarPurchase(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.ScanBarPurchase()
                .subscribe({ bean ->
                    mRootView?.onScanBarPurchaseSuccess()
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    mRootView?.showError(ExceptionHandle.handleException(throwable), ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

}