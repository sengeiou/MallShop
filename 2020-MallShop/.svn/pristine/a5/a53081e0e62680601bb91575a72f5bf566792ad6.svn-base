package com.epro.comp.im.mvp.contract

import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter
import com.epro.comp.im.mvp.model.bean.User

interface RegisterContract {

    interface View:IBaseView{
        /**
         * 验证码发送成功
         */
        fun getVerifyCodeSuccess(msg: String)

        /**
         * 注册成功
         */
        fun registerSuccess(user: User)

    }


    interface Presenter:IPresenter<View>{
        /**
         * 获取手机验证码
         */
        fun getPhoneVerifyCode(dial:String,mobile:String,type:String)

        /**
         * 注册
         */
        fun register( loginname:String,name:String,details:String,type:String)

    }
}