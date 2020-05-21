package com.epro.mall.mvp.presenter

import com.epro.mall.R
import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.mall.mvp.contract.ScanBuyCartContract
import com.epro.mall.mvp.model.ScanBuyCartModel
import com.epro.mall.mvp.model.bean.CreateScanBuyOrderBean
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.utils.AppContext

class ScanBuyCartPresenter : BasePresenter<ScanBuyCartContract.View>(), ScanBuyCartContract.Presenter {

    private val model by lazy { ScanBuyCartModel() }

    override fun getGoodsByBarcode(shopId: String, productBarCode: String, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getGoodsByBarcode(shopId, productBarCode)
                .subscribe({ bean ->
                    mRootView?.onGetGoodsByBarcodeSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS, type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(AppContext.getInstance().getString(R.string.product_barcode_cannot_be_identified), ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

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