package com.epro.mall.mvp.model.bean


data class GetCommonListBean<T>(override val code: Int, override val message: String, override val result:Result<T>):BaseBean<GetCommonListBean.Result<T>> {
    data class Result<T>(val total: Int, val size: Int, val current: Int, val pages: Int, val records: ArrayList<T>)
}