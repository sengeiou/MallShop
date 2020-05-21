package com.epro.mall.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.mall.mvp.contract.SearchContract
import com.epro.mall.mvp.model.SearchModel
import com.mike.baselib.net.exception.ErrorStatus

class SearchPresenter : BasePresenter<SearchContract.View>(), SearchContract.Presenter {

    private val model by lazy { SearchModel() }

    override fun getSearchSuggests(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getSearchSuggests()
                .subscribe({ bean ->
                    mRootView?.onGetSearchSuggestsSuccess()
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }
    fun searchAssociate(keyword:String,type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.searchAssociate(keyword)
                .subscribe({ bean ->
                    mRootView?.onSearchAssociateSuccess(bean.result)
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