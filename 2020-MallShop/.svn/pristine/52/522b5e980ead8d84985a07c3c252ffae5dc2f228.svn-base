package com.epro.mall.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.mall.mvp.contract.PhoneServiceTermsContract
import com.epro.mall.mvp.model.PhoneServiceTermsModel
import com.mike.baselib.net.exception.ErrorStatus

class PhoneServiceTermsPresenter : BasePresenter<PhoneServiceTermsContract.View>(), PhoneServiceTermsContract.Presenter {

    private val model by lazy { PhoneServiceTermsModel() }

    override fun PhoneServiceTerms(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.PhoneServiceTerms()
                .subscribe({ bean ->
                    mRootView?.onPhoneServiceTermsSuccess()
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    mRootView?.showError(ExceptionHandle.handleException(throwable), ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

}