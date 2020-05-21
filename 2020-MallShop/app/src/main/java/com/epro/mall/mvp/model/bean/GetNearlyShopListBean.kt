package com.epro.mall.mvp.model.bean


data class GetNearlyShopListBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<GetNearlyShopListBean.Result> {
    data class Result(val total: Int, val size: Int, val current: Int, val pages: Int, val records: List<NearlyShop>)

//    | 字段      | 类型    | 必须 | 说明     |
//    | --------- | ------- | ---- | -------- |
//    | latitude  | string  | Y    | 纬度     |
//    | longitude | string  | Y    | 经度     |
//    | cityName    | Integer | N    | 城市ID   |
//    | query     | String  | N    | 搜索条件 |
    data class Send(val latitude: String,val longitude:String,val query:String?=null,val city:String?=null)
}
