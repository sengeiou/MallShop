package com.epro.mall.mvp.presenter

import android.content.Context
import android.text.TextUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.epro.mall.R
import com.mike.baselib.base.BasePresenter
import com.epro.mall.mvp.contract.RegisterContract
import com.epro.mall.mvp.model.RegisterModel
import com.epro.mall.utils.MallUtils
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.net.exception.ExceptionHandle
import com.mike.baselib.utils.ValidateUtils
import org.jetbrains.anko.windowManager

class RegisterPresenter:BasePresenter<RegisterContract.View>(),RegisterContract.Presenter {
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


    private val model by lazy { RegisterModel() }

    override fun register(account: String,password: String,code: String,authType:String,type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.register(account,password,code,authType)
                .subscribe({
                    bean->
                    mRootView?.registerSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    fun checkParams(mobile:String,code:String):Boolean{
        if(!ValidateUtils.validatePhoneNo(mobile)){
            toast(R.string.phone_format_error)
            return false
        }

        if(!ValidateUtils.validateVfcode(code)){
            toast(R.string.vfcode_format_error)
            return false
        }
        return true
    }

    fun checkParams2(email:String,code:String):Boolean{
        if(!ValidateUtils.validateEmail(email)){
            toast(R.string.email_format_error)
            return false
        }

        if(!ValidateUtils.validateVfcode(code)){
            toast(R.string.vfcode_format_error)
            return false
        }
        return true
    }

    fun checkPhone(mContext: Context,mobile:String):Boolean{
        if(!ValidateUtils.validatePhoneNo(mobile)){
            MallUtils.showToast(mContext.getString(R.string.account_error),mContext)
            return false
        }
        return true
    }
    fun checkEmail(mContext: Context,email:String):Boolean{
        if(!ValidateUtils.validateEmail(email)){
            MallUtils.showToast(mContext.getString(R.string.account_error),mContext)
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
            MallUtils.showToast(mContext.getString(R.string.repassword_format_error),mContext)
            return false
        }
        if(!(password.equals(repassword))){
            MallUtils.showToast(mContext.getString(R.string.two_password_not_same),mContext)
            return false
        }
        return true
    }

    fun checkParams4(mContext: Context,password:String):Boolean{
        if(!ValidateUtils.validatePassword(password)){
            MallUtils.showToast(mContext.getString(R.string.password_format_error),mContext)
            return false
        }
        return true
    }
}