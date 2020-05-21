package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.GetUserVfBean
import com.epro.mall.mvp.model.bean.GetVfBean
import com.epro.mall.mvp.model.bean.ModifyPasswordBean
import com.epro.mall.mvp.model.bean.MyInfoBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface ModifyPasswordContract {

    interface View:IBaseView{
        fun modifyPasswordSuccess(data: ModifyPasswordBean)
        fun onMyInfoSuccess(result: MyInfoBean.Result)
        fun onGetVfCodeSuccess(result: GetUserVfBean.Result)
    }


    interface Presenter:IPresenter<View>{
        fun modifyPassword(type:String,account:String, code:String,password:String)
        fun MyInfo(type: String)
        fun getVfCode(authType:String,account:String,getType:Int,type:String)
    }
}