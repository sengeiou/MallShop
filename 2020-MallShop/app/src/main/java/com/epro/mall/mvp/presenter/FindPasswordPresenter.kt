package com.epro.mall.mvp.presenter

import android.content.Context
import com.epro.mall.R
import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.mall.mvp.contract.FindPasswordContract
import com.epro.mall.mvp.model.FindPasswordModel
import com.epro.mall.utils.MallUtils
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.utils.ValidateUtils

class FindPasswordPresenter : BasePresenter<FindPasswordContract.View>(), FindPasswordContract.Presenter {
    private val model by lazy { FindPasswordModel() }
    override fun findPassword(account:String,password:String,rePassword:String,code:String,authType:String,type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.findPassword(account, password,rePassword,code,authType)
                .subscribe({ bean ->
                    mRootView?.onFindPasswordSuccess()
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    override fun getVfCode(account: String, getType: Int, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getVfCode(account, getType)
                .subscribe({ bean ->
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

    fun checkParams(mContext: Context,mobile:String,code:String):Boolean{
        if(!ValidateUtils.validatePhoneNo(mobile)){
            MallUtils.showToast(mContext.getString(R.string.phone_format_error),mContext)
            return false
        }

        if(!ValidateUtils.validateVfcode(code)){
            MallUtils.showToast(mContext.getString(R.string.vfcode_format_error),mContext)
            return false
        }
        return true
    }
    fun checkPhone(mContext: Context,mobile:String):Boolean{
        if(!ValidateUtils.validatePhoneNo(mobile)){
            MallUtils.showToast(mContext.getString(R.string.phone_format_error),mContext)
            return false
        }
        return true
    }
    fun checkParams2(mContext: Context,email:String,code:String):Boolean{
        if(!ValidateUtils.validateEmail(email)){
            MallUtils.showToast(mContext.getString(R.string.email_format_error),mContext)
            return false
        }

        if(!ValidateUtils.validateVfcode(code)){
            MallUtils.showToast(mContext.getString(R.string.vfcode_format_error),mContext)
            return false
        }
        return true
    }
    fun checkEmail(mContext: Context,email:String):Boolean{
        if(!ValidateUtils.validateEmail(email)){
            MallUtils.showToast(mContext.getString(R.string.email_format_error),mContext)
            return false
        }
        return true
    }

    fun checkParams3(mContext: Context,password:String,repassword:String):Boolean{
        if(!ValidateUtils.validatePassword(password)){
            MallUtils.showToast(mContext.getString(R.string.password_format_error),mContext)
            return false
        }
        if(!ValidateUtils.validatePassword(repassword)){
            MallUtils.showToast(mContext.getString(R.string.password_format_error),mContext)
            return false
        }
        if(!(password.equals(repassword))){
            MallUtils.showToast(mContext.getString(R.string.two_password_not_same),mContext)
            return false
        }
        return true
    }

}