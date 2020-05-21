package com.epro.mall.mvp.model.bean


data class GetGoodsDetailBean(override val code: Int,
                              override val message: String,
                              override val result: Goods):BaseBean<Goods>{
}