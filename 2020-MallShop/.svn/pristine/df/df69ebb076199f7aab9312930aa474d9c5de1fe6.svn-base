package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.MyInfoBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface MineContract {

    interface View:IBaseView{
        fun onMyInfoSuccess(result: MyInfoBean.Result)
        fun loginOutSuccess()
    }


    interface Presenter:IPresenter<View>{
        fun MyInfo(type: String)
        fun loginOut(type: String)
    }
}