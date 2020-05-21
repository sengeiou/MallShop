package com.epro.mall.mvp.model.bean

data class ScanBarPurchaseBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<ScanBarPurchaseBean.Result> {
    data class Result(val id: String?)
    data class Send(val id: String)
}
