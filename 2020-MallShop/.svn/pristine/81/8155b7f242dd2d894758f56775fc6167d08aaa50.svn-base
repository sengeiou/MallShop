package com.epro.mall.mvp.model.bean

/*| 字段         | 类型    | 必须 | 说明                             |
| ------------ | ------- | ---- | -------------------------------- |
| Id           | Integer | Y    | 主键ID                           |
| puserId      | String  | Y    | 用户ID                           |
| providerId   | String  | Y    | openID                           |
| providerType | String  | Y    | 第三方类型FB:facebook TW:Twitter |
| name         | String  | Y    | 昵称                             |*/

data class AssociationAccountListBean( val code: Int,val message: String, val result: List<Result>){
    data class Result(val id:String, val puserId:String, val providerId:String, val providerType:String, val name:String)
}