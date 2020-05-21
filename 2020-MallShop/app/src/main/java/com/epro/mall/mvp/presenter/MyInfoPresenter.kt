package com.epro.mall.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.mall.mvp.contract.MyInfoContract
import com.epro.mall.mvp.model.MyInfoModel
import com.mike.baselib.net.exception.ErrorStatus
import java.io.File

class MyInfoPresenter : BasePresenter<MyInfoContract.View>(), MyInfoContract.Presenter {

    private val model by lazy { MyInfoModel() }

    override fun MyInfo(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.MyInfo()
                .subscribe({ bean ->
                    mRootView?.onMyInfoSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    override fun updateImage(type:String, image: File, isCreateThumb:Int) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.updateImage(image,isCreateThumb)
                .subscribe({ bean ->
                    mRootView?.onUpdateImageSucess(bean)
                    mRootView?.dismissLoading(type, ErrorStatus.SUCCESS,ErrorStatus.SUCCESS_MSG)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    override fun modifyImage(type:String, image: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.modifyImage(image)
                .subscribe({ bean ->
                    mRootView?.onModifyImageSuccess()
                    mRootView?.dismissLoading(type, ErrorStatus.SUCCESS,ErrorStatus.SUCCESS_MSG)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

}