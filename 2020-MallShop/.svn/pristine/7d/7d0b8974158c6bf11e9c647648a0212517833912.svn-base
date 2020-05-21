package com.epro.mall.mvp.presenter

import com.epro.mall.mvp.contract.AccountAssociationContract
import com.epro.mall.mvp.model.AccountAssociationModel
import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.net.exception.ExceptionHandle

class AccountAssociationPresenter :BasePresenter<AccountAssociationContract.View>(),AccountAssociationContract.Presenter{

    private val model by lazy { AccountAssociationModel() }

    override fun associationAccount(type: String,providerId:String,providerType:String,name:String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.associationAccount(providerId,providerType,name)
                .subscribe({
                    bean-> mRootView?.onAssociationSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    override fun unBindAccount(type: String,id:String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.unBindAccount(id)
                .subscribe({
                    bean-> mRootView?.unBindAssociation(bean)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    override fun associationAccountList(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.associationAccountList()
                .subscribe({
                    bean-> mRootView?.associationAccountListSuccess(bean.result)
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