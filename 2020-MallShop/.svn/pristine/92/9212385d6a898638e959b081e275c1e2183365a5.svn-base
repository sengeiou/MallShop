package com.epro.mall.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.mall.mvp.contract.ShopHomeContract
import com.epro.mall.mvp.model.ShopHomeModel
import com.mike.baselib.net.exception.ErrorStatus

class ShopHomePresenter : BasePresenter<ShopHomeContract.View>(), ShopHomeContract.Presenter {
    override fun getShopGoodsListRecommend(shopId: String, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getShopGoodsListRecommend(shopId)
                .subscribe({ bean ->
                    mRootView?.onGetShopGoodsListRecommendSuccess(bean.result.records)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    private val model by lazy { ShopHomeModel() }

    override fun getShopGoodsListHot(shopId: String, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getShopGoodsListHot(shopId)
                .subscribe({ bean ->
                    mRootView?.onGetShopGoodsListHotSuccess(bean.result.records)
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