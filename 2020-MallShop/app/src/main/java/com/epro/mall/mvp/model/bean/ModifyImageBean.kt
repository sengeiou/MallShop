package com.epro.mall.mvp.model.bean

data class ModifyImageBean(val code:Int,val message:String,val result:Any){
    data class Send(val avatar: String)
}