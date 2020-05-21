package com.epro.mall.mvp.model.bean

/*
请求数据
| 字段        | 类型   | 必须 | 说明               |
| ----------- | ------ | ---- | ------------------ |
| authType        | string | Y    | 1:账号 2邮箱 3手机 |
| code        | String | Y    | 验证码             |
| account     | string | Y    | 账号               |
| password    | string | Y    | 新密码             |
*/
data class ModifyPasswordBean(override val code:Int,override val message: String,  override val result:Result):BaseBean<ModifyPasswordBean.Result>{
    data class Result(val code: Int,val message: String,val result:Any)
    data class Send(val authType:String,val account:String,val code: String,val password:String)
}