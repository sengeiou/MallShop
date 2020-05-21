package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.GetVfBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface FindPasswordContract {

    interface View : IBaseView {
        fun onFindPasswordSuccess()

        fun onGetVfCodeSuccess(result: GetVfBean.Result)

    }


    interface Presenter : IPresenter<View> {
        fun findPassword(account:String,password:String,rePassword:String,code:String,authType:String,type: String)

        fun getVfCode(account:String,getType:Int,type:String)
    }
}