package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.GetVfBean
import com.epro.mall.mvp.model.bean.RegisterBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter
import com.epro.mall.mvp.model.bean.User

interface RegisterContract {

    interface View:IBaseView{
        /**
         * 验证码发送成功
         */
        fun onGetVfCodeSuccess(result: GetVfBean.Result)

        /**
         * 注册成功
         */
        fun registerSuccess(result:RegisterBean.Result)

    }


    interface Presenter:IPresenter<View>{
        /**
         * 获取手机验证码
         */
        fun getVfCode(account:String,getType:Int,type:String)

        /**
         * 注册
         */
        fun register( account:String,password:String,code:String,authType:String,type:String)


    }
}