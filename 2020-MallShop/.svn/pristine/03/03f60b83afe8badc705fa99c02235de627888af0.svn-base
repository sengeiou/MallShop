package com.epro.mall.mvp.presenter

import com.mike.baselib.base.ListPresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.mall.mvp.model.bean.AddressListItem
import com.epro.mall.mvp.contract.AddressListContract
import com.epro.mall.mvp.model.AddressListModel
import com.epro.mall.mvp.model.bean.Address
import com.epro.mall.mvp.model.bean.AddressListBean
import com.mike.baselib.net.exception.ErrorStatus

class AddressListPresenter : ListPresenter<Address, AddressListContract.View>(), AddressListContract.Presenter {


    private val model by lazy { AddressListModel() }

    override fun getAddressList(type: String,parentId:String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getAddressList(parentId)
                .subscribe({ bean ->
                    mRootView?.getListDataSuccess(bean.result, type)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    override fun deleteAddress(type: String, id: String) {
        checkViewAttached()
        mRootView?.showLoading(type)

        val disposable = model.deleteAddress(id)
                .subscribe({
                    bean->
                    mRootView?.onAddressDeleteSuccess(bean)
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
