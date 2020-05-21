package com.epro.mall.mvp.model.bean

/*| 字段     | 类型   | 必须 | 说明             |
| -------- | :----- | ---- | ---------------- |
| account  | string | Y    | 手机/邮箱        |
| code     | string | Y    | 验证码           |
| authType | string | Y    | EP:邮箱, MP:手机 |*/
data class EditBindAccountBean(override val code: Int,
                               override val message: String,
                               override val result: Result):BaseBean<EditBindAccountBean.Result>{
    data class Result(val code: Int,val message: String,val result:Any)
    data class Send(val account:String,val code: String,val authType:String)
}