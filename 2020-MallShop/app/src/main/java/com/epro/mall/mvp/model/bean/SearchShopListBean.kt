package com.epro.mall.mvp.model.bean


data class SearchShopListBean(val code: Int, val message: String, val result: Result) {
    data class Result(val total:String, val rows: List<SearchShop>)
//    | 字段      | 类型   | 必须 | 说明       |
//    | --------- | ------ | ---- | ---------- |
//    | keyword   | string | Y    | 搜索关键词 |
//    | longitude | string | Y    | 定位经度   |
//    | latitude  | string | Y    | 定位维度   |
//    | page      | object | Y    | 分页信息   |
    data class Send(val keyword:String,val longitude:String, val latitude:String,val page:PageList )
    data class PageList(val no:Int,val size :Int =15)
}