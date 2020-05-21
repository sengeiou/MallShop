package com.epro.mall.mvp.presenter

import com.mike.baselib.base.ListPresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.mall.mvp.model.bean.SearchShop
import com.epro.mall.mvp.contract.SearchShopListContract
import com.epro.mall.mvp.model.SearchShopListModel
import com.mike.baselib.net.exception.ErrorStatus

class SearchShopListPresenter : ListPresenter<SearchShop, SearchShopListContract.View>(), SearchShopListContract.Presenter {

    private val model by lazy { SearchShopListModel() }

     fun searchShopList( keyword:String, longitude:String,  latitude:String, page:Int,type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.searchShopList(keyword,longitude,latitude,page)
                .subscribe({ bean ->
                    mRootView?.getListDataSuccess(bean.result.rows, type)
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
