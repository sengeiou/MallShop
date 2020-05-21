package com.epro.mall.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.mall.mvp.contract.FindShopContract
import com.epro.mall.mvp.model.FindShopModel
import com.mike.baselib.net.exception.ErrorStatus

class FindShopPresenter : BasePresenter<FindShopContract.View>(), FindShopContract.Presenter {

    private val model by lazy { FindShopModel() }

    override fun getFindShop(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getFindShop()
                .subscribe({ bean ->
                    mRootView?.onFindShopSuccess()
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