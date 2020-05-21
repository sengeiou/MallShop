package com.epro.mall.mvp.model.bean


data class OrderPayBean(override val code: Int,
                        override val message: String,
                        override val result: Result) : BaseBean<OrderPayBean.Result> {
    data class Send(val orderSn: String) {}
    data class Result(val notifyStr: String, val orderSn: String)
}