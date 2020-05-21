package com.epro.mall.mvp.model.bean

/*
请求结果
| 字段         | 类型   | 必须 | 说明                             |
| ------------ | :----- | ---- | -------------------------------- |
| providerId   | string | Y    | openID                           |
| providerType |        |      | 第三方类型FB:facebook TW:Twitter |
| name         |        |      | 昵称                             |
*/
data class AccountAssociationBean (override val code: Int, override val message: String, override val result: Result):BaseBean<AccountAssociationBean.Result>{
    data class Result(val code: Int,val message:String,val result:Any)
    data class Send(val providerId:String,val providerType:String,val name:String)
}