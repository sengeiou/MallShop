package com.epro.mall.mvp.model.bean


data class GetShopGoodsListBean(override val code: Int, override val message: String, override val result:Result):BaseBean<GetShopGoodsListBean.Result> {
    data class Result(val total: Int, val size: Int, val current: Int, val pages: Int, val records: ArrayList<Goods>)
    data class Send(val shopId:String)
}