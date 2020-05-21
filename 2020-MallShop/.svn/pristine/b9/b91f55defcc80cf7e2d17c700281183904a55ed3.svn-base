package com.epro.mall.mvp.presenter

import com.epro.mall.mvp.contract.AboutContract
import com.epro.mall.mvp.model.AboutModel
import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.net.exception.ExceptionHandle

class AboutPresenter : BasePresenter<AboutContract.View>(), AboutContract.Presenter {

    private val model by lazy { AboutModel() }
    override fun aboutApp(type: String,authType:String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.aboutApp(authType)
                .subscribe({
                   bean -> mRootView?.onAboutSuccess(bean.result)
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