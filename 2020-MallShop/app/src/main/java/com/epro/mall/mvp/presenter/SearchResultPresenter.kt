package com.epro.mall.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.mall.mvp.contract.SearchResultContract
import com.epro.mall.mvp.model.SearchResultModel
import com.mike.baselib.net.exception.ErrorStatus

class SearchResultPresenter : BasePresenter<SearchResultContract.View>(), SearchResultContract.Presenter {
    override fun searchInShop(shopId: String, keyword: String, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.searchInShop(shopId,keyword)
                .subscribe({ bean ->
                    mRootView?.onSearchInShopSuccess()
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    private val model by lazy { SearchResultModel() }

    override fun searchAll(keyword:String,type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.searchAll(keyword)
                .subscribe({ bean ->
                    mRootView?.onSearchAllSuccess()
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