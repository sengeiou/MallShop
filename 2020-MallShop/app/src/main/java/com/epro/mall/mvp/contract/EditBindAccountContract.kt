package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.CheckBindAccountBean
import com.epro.mall.mvp.model.bean.GetUserVfBean
import com.epro.mall.mvp.model.bean.GetVfBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface EditBindAccountContract {

    interface View : IBaseView {
        fun onEditBindAccountSuccess()
        fun onGetVfCodeSuccess(result: GetVfBean.Result)
        fun onCheckSuccess(result: CheckBindAccountBean.Result)
        fun onGetUserCodeSuccess(result: GetUserVfBean.Result,type: String)
    }


    interface Presenter : IPresenter<View> {
        fun getEditBindAccount(type: String,account:String,code:String,authType:String)
        fun getVfCode(account:String,getType:Int,type:String)
        fun checkBindAccount(type: String,account:String,code:String,authType:String)
        fun getUserVfCode(authType:String,account:String,getType:Int,type:String)
    }
}