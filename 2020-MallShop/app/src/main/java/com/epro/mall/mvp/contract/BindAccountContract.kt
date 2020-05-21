package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.GetVfBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface BindAccountContract {

    interface View : IBaseView {
        fun onBindAccountSuccess()
        fun onGetVfCodeSuccess(result: GetVfBean.Result)
    }


    interface Presenter : IPresenter<View> {
        fun getBindAccount(account: String, password: String, code: String, type: String)
        fun getVfCode(account: String, getType: Int, type: String)
    }
}