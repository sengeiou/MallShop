package com.epro.mall.mvp.model.bean


data class SearchGoodsListBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<SearchGoodsListBean.Result> {
    data class Result(val total:String, val rows: List<SearchGoods>)
    data class Send(val keyword: String,val page:PageList,val shopId:String?=null)
    data class PageList(val no:Int,val size :Int =15)

}
