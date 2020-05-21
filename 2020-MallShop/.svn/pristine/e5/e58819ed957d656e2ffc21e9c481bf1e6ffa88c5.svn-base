package com.epro.mall.mvp.model.bean

data class BindAccountBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<BindAccountBean.Result> {
    data class Result(val id: String?)
    //    | 字段     | 类型   | 必须 | 说明              |
//    | -------- | :----- | ---- | ----------------- |
//    | account  | string | Y    | 手机号码/邮箱账号 |
//    | passWord | string | Y    | 密码              |
//    | code     | string | Y    | 验证码            |
    data class Send(val account:String,val password:String,val code:String)
}
