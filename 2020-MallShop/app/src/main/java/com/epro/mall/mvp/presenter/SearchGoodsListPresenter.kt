package com.epro.mall.mvp.presenter

import com.mike.baselib.base.ListPresenter
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.mall.mvp.model.bean.SearchGoods
import com.epro.mall.mvp.contract.SearchGoodsListContract
import com.epro.mall.mvp.model.SearchGoodsListModel

class SearchGoodsListPresenter : ListPresenter<SearchGoods, SearchGoodsListContract.View>(), SearchGoodsListContract.Presenter {

    private val model by lazy { SearchGoodsListModel() }

    override fun searchGoodsList(keyword:String,page:Int,shopId:String?,type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.searchGoodsList(keyword,page,shopId)
                .subscribe({ bean ->
                    mRootView?.getListDataSuccess(bean.result.rows, type)
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
