package com.epro.mall.mvp.presenter

import android.annotation.SuppressLint
import android.content.Context
import com.epro.mall.R
import com.epro.mall.mvp.contract.LoginContract
import com.epro.mall.mvp.model.LoginModel
import com.epro.mall.utils.MallUtils
import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.net.exception.ExceptionHandle
import com.mike.baselib.utils.ValidateUtils

class LoginPresenter:BasePresenter<LoginContract.View>(),LoginContract.Presenter {
    override fun getVfCode(account: String, getType: Int, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getVfCode(account,getType)
                .subscribe({
                    bean->
                    mRootView?.onGetVfCodeSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    private val model by lazy { LoginModel() }

    override fun login(account:String,password:String,loginType:String,type:String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.login(account,password,loginType)
                .subscribe({
                    bean->
                    mRootView?.loginSuccess(bean.result,type)
                   // mRootView?.loginSuccess(bean.result!!,type)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    fun checkParams(mContext:Context, mobile:String, password: String):Boolean{
        if(!ValidateUtils.validatePhoneNo(mobile)){
            MallUtils.showToast(mContext.getString(R.string.phone_format_error),mContext)
            return false
        }

        if(!ValidateUtils.validatePassword(password)){
            MallUtils.showToast(mContext.getString(R.string.phone_format_error),mContext)
            return false
        }
       /* if (!isChecked){
            MallUtils.showToast(mContext.getString(R.string.pls_checked_server_select),mContext)
            return false
        }*/
        return true
    }

    fun checkParams2(mContext: Context,mobile:String,code:String):Boolean{
        if(!ValidateUtils.validatePhoneNo(mobile)){
            MallUtils.showToast(mContext.getString(R.string.phone_format_error),mContext)
            return false
        }

        if(!ValidateUtils.validateVfcode(code)){
            MallUtils.showToast(mContext.getString(R.string.vfcode_format_error),mContext)
            return false
        }
       /* if (!isChecked){
            MallUtils.showToast(mContext.getString(R.string.pls_checked_server_select),mContext)
            return false
        }*/
        return true
    }

    fun checkParams3(mContext: Context,email:String,password: String):Boolean{
        if(!ValidateUtils.validateEmail(email)){
            MallUtils.showToast(mContext.getString(R.string.email_format_error),mContext)
            return false
        }

        if(!ValidateUtils.validatePassword(password)){
            MallUtils.showToast(mContext.getString(R.string.phone_format_error),mContext)
            return false
        }
      /*  if (!isChecked){
            MallUtils.showToast(mContext.getString(R.string.pls_checked_server_select),mContext)
            return false
        }*/
        return true
    }

    fun checkPhone(mContext: Context,mobile:String):Boolean{
        if(!ValidateUtils.validatePhoneNo(mobile)){
            MallUtils.showToast(mContext.getString(R.string.phone_format_error),mContext)
            return false
        }
        return true
    }

    override fun MyInfo(type: String,loginType: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.MyInfo()
                .subscribe({ bean ->
                    mRootView?.onMyInfoSuccess(bean.result,loginType)
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