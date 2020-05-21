package com.epro.mall.mvp.presenter

import com.epro.mall.mvp.contract.ScanCodeOrderListContract
import com.epro.mall.mvp.model.ScanCodeOrderListModel
import com.epro.mall.mvp.model.bean.ScanCodeOrderListBean
import com.epro.mall.mvp.model.bean.ScanCodeOrderListOneBean
import com.mike.baselib.base.ListPresenter
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.net.exception.ExceptionHandle

class ScanCodeOrderListPresenter : ListPresenter<ScanCodeOrderListOneBean,ScanCodeOrderListContract.View>(), ScanCodeOrderListContract.Presenter {

    private val model by lazy { ScanCodeOrderListModel() }

    override fun ScanCodeOrderList(type: String,shopId:String,page:Int) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.ScanCodeOrderList(shopId,page)
                .subscribe({ bean ->
                    mRootView?.getListDataSuccess(bean.result.rows,type)
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