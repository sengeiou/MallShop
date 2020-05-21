package com.epro.mall.mvp.model.bean


data class OrderAgainBean(override val code: Int,
                          override val message: String,
                          override val result: ArrayList<String>) : BaseBean<ArrayList<String>> {
    data class Send(val orderSn: String) 
}