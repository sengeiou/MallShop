package com.epro.mall.mvp.presenter

import com.epro.mall.mvp.contract.ScanCodeOrderListDetailContract
import com.epro.mall.mvp.model.ScanCodeOrderListDetailModel
import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.net.exception.ExceptionHandle

class ScanCodeOrderListDetailPresenter : BasePresenter<ScanCodeOrderListDetailContract.View>(), ScanCodeOrderListDetailContract.Presenter {

    private val model by lazy { ScanCodeOrderListDetailModel() }

    override fun ScanCodeOrderListDetail(type: String,orderSn:String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.ScanCodeOrderListDetail(orderSn)
                .subscribe({ bean ->
                    mRootView?.onScanCodeOrderListDetailSuccess(bean.result)
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