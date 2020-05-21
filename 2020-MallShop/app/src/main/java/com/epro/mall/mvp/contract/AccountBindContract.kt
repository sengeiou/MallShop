package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.GetUserVfBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface AccountBindContract {

    interface View : IBaseView {
        fun onAccountBindSuccess()
        fun onGetVfCodeSuccess(result: GetUserVfBean.Result)
    }


    interface Presenter : IPresenter<View> {
        fun AccountBind(type: String,account:String,code:String,authType:String,password:String,bindType:Int)
        fun getVfCode(authType:String,account:String,getType:Int,type:String)
    }
}