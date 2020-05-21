package com.epro.mall.mvp.model.bean

data class GetCartGoodsListBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<GetCartGoodsListBean.Result> {
    data class Result(val total: Int, val size: Int, val current: Int, val pages: Int, val records: ArrayList<ShopCart>)
    data class Send(val id: String)
}
