package com.epro.mall.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.mall.mvp.contract.UpdateAppContract
import com.epro.mall.mvp.model.UpdateAppModel
import com.mike.baselib.net.exception.ErrorStatus

class UpdateAppPresenter : BasePresenter<UpdateAppContract.View>(), UpdateAppContract.Presenter {

    private val model by lazy { UpdateAppModel() }

    override fun UpdateApp(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.UpdateApp()
                .subscribe({ bean ->
                    mRootView?.onUpdateAppSuccess()
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    mRootView?.showError(ExceptionHandle.handleException(throwable), ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

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