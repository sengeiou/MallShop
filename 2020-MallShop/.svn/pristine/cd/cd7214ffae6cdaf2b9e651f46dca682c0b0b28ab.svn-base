package com.epro.mall.mvp.model.bean


data class GetUserVfBean(override val code:Int, override val message: String, override val result:Result):BaseBean<GetUserVfBean.Result>{
    data class Result(val id:String)

    //    | 字段    | 类型   | 必须 | 说明                                            |
//    | ------- | :----- | ---- | ----------------------------------------------- |
//    | account | string | Y    | 手机号码/邮箱账号                               |
//    | type    | int    | Y    | 验证码类型(1-登录,2-注册,3-找回密码,4-修改密码) |
    data class Send(val authType:String,val account:String,val type:Int)
}