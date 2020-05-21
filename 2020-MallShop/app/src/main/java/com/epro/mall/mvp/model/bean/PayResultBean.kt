package com.epro.mall.mvp.model.bean

data class PayResultBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<PayResultBean.Result> {
    data class Result(val id: String)
    data class Send(val id: String)
}
