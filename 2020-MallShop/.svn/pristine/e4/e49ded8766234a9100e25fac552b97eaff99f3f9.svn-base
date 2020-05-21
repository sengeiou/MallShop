package com.epro.mall.mvp.model.bean

/*| 字段     | 类型    | 必须 | 说明                             |
| -------- | :------ | ---- | -------------------------------- |
| account  | string  | Y    | 手机/邮箱                        |
| code     | string  | Y    | 验证码                           |
| authType | string  | Y    | ep:邮箱, mp:手机                 |
| password | String  | Y    | 密码                             |
| type     | Integer | Y    | 绑定入口 1:第三方账户 2:普通用户 |*/

data class AccountBindBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<AccountBindBean.Result> {
    data class Result(val result: Any)
    data class Send(val account: String ,val code:String,val authType:String,val password:String,val type:Int)
}
