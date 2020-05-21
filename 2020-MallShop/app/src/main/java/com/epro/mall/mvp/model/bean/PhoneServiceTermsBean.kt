package com.epro.mall.mvp.model.bean

data class PhoneServiceTermsBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<PhoneServiceTermsBean.Result> {
    data class Result(val id: String?)
    data class Send(val id: String)
}
