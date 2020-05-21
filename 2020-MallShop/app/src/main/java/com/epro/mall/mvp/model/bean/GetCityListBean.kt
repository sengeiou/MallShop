package com.epro.mall.mvp.model.bean

data class GetCityListBean(val code: Int, val message: String, val result: ArrayList<City>) {
    data class Send(val parentId: String)
}