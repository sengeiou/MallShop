package com.epro.mall.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.epro.mall.mvp.contract.MineContract
import com.epro.mall.mvp.model.MineModel
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.net.exception.ExceptionHandle

class MinePresenter:BasePresenter<MineContract.View>(),MineContract.Presenter {
    override fun loginOut(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.loginOut()
                .subscribe({ bean ->
                    mRootView?.loginOutSuccess()
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    override fun MyInfo(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.MyInfo()
                .subscribe({ bean ->
                    mRootView?.onMyInfoSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }
    private val model by lazy { MineModel() }

}