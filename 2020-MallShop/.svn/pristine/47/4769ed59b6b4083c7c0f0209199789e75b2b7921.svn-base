package com.epro.mall.mvp.model.bean

import com.mike.baselib.interface_.Judgable

data class CartGoods(val goodsId: String,
                     val goodsName: String,
                     val productId: String,
                     var salePrice: String,
                     var productCount: Int,
                     val listPicUrl: String,
                     val goodsSpecifitionNameValue: String = "",
                     val shoppingMallName:String?="",
                     val isValid: Int=1,
                     var onlineActivityInfo:String="",
                     override var judgeValue: Boolean = false) : Judgable {
    override fun judge(): Boolean {
        return judgeValue
    }
}