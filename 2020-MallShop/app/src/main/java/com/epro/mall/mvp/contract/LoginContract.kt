package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.GetVfBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter
import com.epro.mall.mvp.model.bean.LoginBean
import com.epro.mall.mvp.model.bean.MyInfoBean

/**
 * Created by xuhao on 2017/11/30.
 * desc: 契约类
 */
interface LoginContract {

    interface View:IBaseView{
        /**
         * 登录成功
         */
        fun loginSuccess(result: LoginBean.Result,type:String)

        fun onGetVfCodeSuccess(result: GetVfBean.Result)

        fun onMyInfoSuccess(result: MyInfoBean.Result,type: String)
    }


    interface Presenter:IPresenter<View>{
        /**
         * 登录
         */
        fun login(account:String,password:String,loginType:String,type:String)

        fun getVfCode(account:String,getType:Int,type:String)

        fun MyInfo(type: String,loginType: String)
    }
}