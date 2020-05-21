package com.epro.mall.mvp.model.bean

data class UpdateAppBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<UpdateAppBean.Result> {
    data class Result(val id: String?)
    data class Send(val id: String)
}
