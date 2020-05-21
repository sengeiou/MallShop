package com.epro.mall.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.mall.mvp.contract.EditBindAccountContract
import com.epro.mall.mvp.model.EditBindAccountModel
import com.mike.baselib.net.exception.ErrorStatus

class EditBindAccountPresenter : BasePresenter<EditBindAccountContract.View>(), EditBindAccountContract.Presenter {



    private val model by lazy { EditBindAccountModel() }

    override fun getEditBindAccount(type: String,account:String,code:String,authType:String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getEditBindAccount(account,code,authType)
                .subscribe({ bean ->
                    mRootView?.onEditBindAccountSuccess()
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    override fun getVfCode(account: String, getType: Int, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getVfCode(account,getType)
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

    //解绑或者修改密码
    override fun getUserVfCode(authType:String,account: String, getType: Int, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getUserVfCode(authType,account,getType)
                .subscribe({
                    bean->
                    mRootView?.onGetUserCodeSuccess(bean.result,type)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    override fun checkBindAccount(type: String, account: String, code: String, authType: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.checkBindAccount(account,code,authType)
                .subscribe({ bean ->
                    mRootView?.onCheckSuccess(bean.result)
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