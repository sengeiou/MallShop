package com.epro.mall.mvp.model.bean

data class RegisterBean(override val code:Int, override val message: String, override val result:Result):BaseBean<RegisterBean.Result>{
    data class Result(val code: Int,val message: String,val result:Any)

/*    | 字段     | 类型   | 必须 | 说明                       |
    | -------- | :----- | ---- | -------------------------- |
    | account  | string | Y    | 手机号码/邮箱账号          |
    | password | string | Y    | 密码(8-16位数字和字母组合) |
    | code     | string | Y    | 验证码(6位数字)            |
    | authType | string | Y    | EP:邮箱, MP:手机           |*/
    data class Send(val account:String,val password:String,val code:String,val authType:String)
}