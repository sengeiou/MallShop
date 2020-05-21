package com.epro.mall.mvp.model.bean

/*
请求
| 字段     | 类型   | 必须 | 说明              |
| -------- | :----- | ---- | ----------------- |
| account  | string | Y    | 手机号码/邮箱账号 |
| password | string | Y    | 密码              |
| code     | string | Y    | 验证码            |
*/
data class UpdatePswBean(val code:Int ,val message:String ,val result : Any){
    data class Send(val account:String,val password:String,val code: String)
}