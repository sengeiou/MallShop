package com.epro.mall.mvp.model.bean

data class DeleteOrderBean(val code:Int,val message:String,val result:String){
    data class Send(val orderSn:String)
}