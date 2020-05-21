package com.epro.mall.mvp.model.bean

data class FindPasswordBean(override val code:Int, override val message: String, override val result:Result):BaseBean<FindPasswordBean.Result>{
    data class Result(val code: Int,val message: String,val result:Any)

/*    | 字段            | 类型   | 必须 | 说明              |
    | --------------- | :----- | ---- | ----------------- |
    | account         | string | Y    | 手机号码/邮箱账号 |
    | passWord        | string | Y    | 密码              |
    | passwordConfirm | string | Y    | 确认密码          |
    | code            | string | Y    | 验证码            |
    | authTye         | string | Y    | ep:邮箱, mp:手机  |*/
    data class Send(val account:String,val password:String,val passwordConfirm:String,val code:String,val authType:String)
}