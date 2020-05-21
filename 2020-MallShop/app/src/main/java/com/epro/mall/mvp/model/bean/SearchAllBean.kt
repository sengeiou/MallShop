package com.epro.mall.mvp.model.bean

data class SearchAllBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<SearchAllBean.Result> {
    data class Result(val id: String?)
    data class Send(val searchStr: String)
}
