package com.epro.mall.mvp.model.bean

import com.mike.baselib.interface_.Judgable

data class NearlyShop(val address: String,
                      val distance: Int,
                      val goodsTypeList: ArrayList<GoodsType>,
                      val id: String,
                      val isShow: String,
                      val latitude: String,
                      val longitude: String,
                      val shopId: String,
                      val shopLogo: String,
                      val shopName: String,
                      val shopDiscount: String,override var judgeValue: Boolean=false):Judgable {
    override fun judge(): Boolean {
       return judgeValue
    }

    data class GoodsType(val createTime: String,
                         val goodsTypeId: String,
                         val goodsTypeName: String,
                         val sortOrder: Int,
                         val typeName: String,
                         val id: Int,
                         val shopId: String,
                         val isShow: Int)
}