package com.epro.mall.mvp.presenter

import android.content.Context
import com.epro.mall.R
import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.mall.mvp.contract.AccountBindContract
import com.epro.mall.mvp.model.AccountBindModel
import com.epro.mall.utils.MallUtils
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.utils.ValidateUtils

class AccountBindPresenter : BasePresenter<AccountBindContract.View>(), AccountBindContract.Presenter {

    private val model by lazy { AccountBindModel() }

    override fun AccountBind(type: String,account:String,code:String,authType:String,password:String,bindType:Int) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.AccountBind(account,code,authType,password,bindType)
                .subscribe({ bean ->
                    mRootView?.onAccountBindSuccess()
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    mRootView?.showError(ExceptionHandle.handleException(throwable), ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    override fun getVfCode(authType:String,account: String, getType: Int, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getVfCode(authType,account,getType)
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

    fun checkPhone(mContext: Context, mobile:String):Boolean{
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

    fun checkPassword(mContext: Context,password: String):Boolean{
        if(!ValidateUtils.validatePassword(password)){
            MallUtils.showToast(mContext.getString(R.string.password_format_error),mContext)
            return false
        }
        return true
    }
}