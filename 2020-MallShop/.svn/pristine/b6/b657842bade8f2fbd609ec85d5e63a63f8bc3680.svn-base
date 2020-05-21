package com.epro.mall.mvp.model.bean

data class AddToCartBean(override val code: Int, override val message: String, override val result: Any?) : BaseBean<Any?> {
    data class Send(val products:ArrayList<Product>)
    data class Product(val productId:String,val productCount:Int)
}
