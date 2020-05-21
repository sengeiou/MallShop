package com.epro.mall.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.mall.mvp.contract.MemberCentreContract
import com.epro.mall.mvp.model.MemberCentreModel
import com.mike.baselib.net.exception.ErrorStatus

class MemberCentrePresenter : BasePresenter<MemberCentreContract.View>(), MemberCentreContract.Presenter {

    private val model by lazy { MemberCentreModel() }

    override fun MemberCentre(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.MemberCentre()
                .subscribe({ bean ->
                    mRootView?.onMemberCentreSuccess()
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    mRootView?.showError(ExceptionHandle.handleException(throwable), ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                })
        addSubscription(disposable)
    }

}