package com.epro.comp.im.mvp.presenter

import android.text.TextUtils
import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.comp.im.mvp.contract.LoginContract
import com.epro.comp.im.mvp.model.LoginModel
import com.epro.comp.im.utils.IMConst
import com.mike.baselib.net.exception.ErrorStatus

class LoginPresenter : BasePresenter<LoginContract.View>(), LoginContract.Presenter {

    private val model by lazy { LoginModel() }

    fun login(username: String, password: String, type: String = IMConst.LOGIN) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.login(username, password)
                .subscribe({ bean ->
                    mRootView?.loginSuccess(bean.result!!)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS, type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

    fun checkParam(mobile: String, password: String?): Boolean {
        if (TextUtils.isEmpty(mobile)) {
            toast("账号不能为空")
            return false
        }
        if (TextUtils.isEmpty(password)) {
            toast("密码不能为空")
            return false
        }
        return true
    }
}