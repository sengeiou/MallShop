package com.epro.mall.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.mall.mvp.contract.ShopInfoContract
import com.epro.mall.mvp.model.ShopInfoModel
import com.mike.baselib.net.exception.ErrorStatus

class ShopInfoPresenter : BasePresenter<ShopInfoContract.View>(), ShopInfoContract.Presenter {

    private val model by lazy { ShopInfoModel() }

    override fun getShopInfo(shopId: String, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getShopInfo(shopId)
                .subscribe({ bean ->
                    mRootView?.onGetShopInfoSuccess(bean.result)
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