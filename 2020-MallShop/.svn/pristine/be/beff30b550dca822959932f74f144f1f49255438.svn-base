package com.epro.mall.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.mall.mvp.contract.ModifyPasswordContract
import com.epro.mall.mvp.model.ModifyPasswordModel
import com.mike.baselib.net.exception.ErrorStatus

class ModifyPasswordPresenter: BasePresenter<ModifyPasswordContract.View>(),ModifyPasswordContract.Presenter {

    private val model by lazy { ModifyPasswordModel() }

    override fun modifyPassword(type:String,account:String, code:String,password:String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable =model.modifyPassword(type,account,code,password)
                .subscribe({
                    bean->
                    mRootView?.modifyPasswordSuccess(bean)
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

    override fun getVfCode(authType:String,account: String, getType: Int, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getVfCode(authType,account,getType)
                .subscribe({
                    bean->
                    mRootView?.onGetVfCodeSuccess(bean.result)
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