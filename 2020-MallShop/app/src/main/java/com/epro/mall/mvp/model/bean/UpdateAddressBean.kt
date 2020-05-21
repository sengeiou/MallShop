package com.epro.mall.mvp.model.bean

data class UpdateAddressBean(val code:Int,val message:String,val result:Any) {
    data class Send(val id:String,val receive:String,val province:String,
                    val city:String,val area:String,val location:String,
                    val address:String,val mobile:String,val isDefult:Int,val longitude:String,val latitude:String)
}