package com.epro.mall.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.mall.mvp.contract.GeolocationSelectContract
import com.epro.mall.mvp.model.GeolocationSelectModel
import com.mike.baselib.net.exception.ErrorStatus

class GeolocationSelectPresenter : BasePresenter<GeolocationSelectContract.View>(), GeolocationSelectContract.Presenter {

    private val model by lazy { GeolocationSelectModel() }

    override fun GeolocationSelect(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.GeolocationSelect()
                .subscribe({ bean ->
                    mRootView?.onGeolocationSelectSuccess()
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    mRootView?.showError(ExceptionHandle.handleException(throwable), ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                })
        addSubscription(disposable)
    }

}