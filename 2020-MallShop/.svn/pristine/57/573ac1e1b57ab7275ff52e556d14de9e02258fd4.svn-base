package com.epro.mall.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.mall.mvp.contract.EditAddressContract
import com.epro.mall.mvp.model.EditAddressModel
import com.mike.baselib.net.exception.ErrorStatus

class EditAddressPresenter : BasePresenter<EditAddressContract.View>(), EditAddressContract.Presenter {


    private val model by lazy { EditAddressModel() }

    override fun editAddress(type: String,id:String,receive:String,province:String, city:String, area:String,
                             location:String,address:String, mobile:String,isDefult:Int,longitude:String,latitude:String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.editAddress(id,receive,province,city,area,location,address,mobile,isDefult,longitude,latitude)
                .subscribe({ bean ->
                    mRootView?.onEditAddressSuccess(bean)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    override fun addNewAddress(type: String,receive:String,province:String, city:String, area:String,
                               location:String,address:String, mobile:String,isDefult:Int,longitude:String,latitude:String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.addNewAddress(receive,province,city,area,location,address,mobile,isDefult,longitude,latitude)
                .subscribe({ bean ->
                    mRootView?.onAddNewAddressSuccess(bean)
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