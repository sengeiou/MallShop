package com.epro.mall.mvp.model.bean


data class GetShopInfoBean(override val code: Int,
                           override val message: String,
                           override val result: ShopInfo) : BaseBean<ShopInfo> {
}