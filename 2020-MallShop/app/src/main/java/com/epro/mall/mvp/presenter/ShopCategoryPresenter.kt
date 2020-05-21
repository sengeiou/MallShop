package com.epro.mall.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.mall.mvp.contract.ShopCategoryContract
import com.epro.mall.mvp.model.ShopCategoryModel
import com.mike.baselib.net.exception.ErrorStatus

class ShopCategoryPresenter : BasePresenter<ShopCategoryContract.View>(), ShopCategoryContract.Presenter {

    private val model by lazy { ShopCategoryModel() }

   fun getShopGoodsCategoryList(shopId: String, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getShopGoodsCategoryList(shopId)
                .subscribe({ bean ->
                    mRootView?.onGetShopGoodsCategoryListSuccess(bean.result)
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