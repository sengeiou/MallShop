package com.epro.mall.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.mall.mvp.contract.SelectCityContract
import com.epro.mall.mvp.model.SelectCityModel

class SelectCityPresenter : BasePresenter<SelectCityContract.View>(), SelectCityContract.Presenter {

    private val model by lazy { SelectCityModel() }

    override fun getCities(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getCities()
                .subscribe({ bean ->
                    mRootView?.onGetCitiesSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS, type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }
     fun getCityList(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getCityList()
                .subscribe({ bean ->
                    mRootView?.onGetCitiesSuccess()
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS, type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

}