package com.epro.mall.mvp.model.bean
/*
send
| 字段       | 类型   | 必须 | 说明   |
| ---------- | :----- | ---- | ------ |
| providerId | string | Y    | openID |
*/
class UnbindAccountBean(val code:Int , val message:String , val result:Any) {
    data class Send(val providerId:String)
}