package com.epro.mall.mvp.model.bean

data class CheckOrderPayBean(override val code: Int, override val message: String, override var result: String?, var checkResult:Result?) : BaseBean<Any> {
    data class Result(val payStatus:Int,val message:String) //支付状态
    data class Send(val orderSn:String)
}