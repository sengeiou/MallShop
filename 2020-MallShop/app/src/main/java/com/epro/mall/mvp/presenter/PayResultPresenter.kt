package com.epro.mall.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.mall.mvp.contract.PayResultContract
import com.epro.mall.mvp.model.PayResultModel
import com.epro.mall.utils.MallConst
import com.mike.baselib.net.exception.ErrorStatus

class PayResultPresenter : BasePresenter<PayResultContract.View>(), PayResultContract.Presenter {

    private val model by lazy { PayResultModel() }

    fun getShopNewGoodsList(shopId:String,type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getShopNewGoodsList(shopId)
                .subscribe({ bean ->
                    mRootView?.onGetShopNewGoodsListSuccess(bean.result)
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