package com.epro.mall.mvp.model.bean

import com.mike.baselib.interface_.Judgable

data class ShopGoodsCategory(val goodsList: ArrayList<Goods>,
                             val goodsTypeId: String,
                             val goodsTypeName: String, override var judgeValue: Boolean=false) :Judgable{
    override fun judge(): Boolean {
        return judgeValue
    }

    data class Goods(val goodsCompressPriUrl: String,
                             val goodsCompressPriUrl1: String,
                             val goodsCompressPriUrl2: String,
                             val goodsCompressPriUrl3: String,
                             val goodsCompressPriUrl4: String,
                             val goodsID: String,
                             val goodsName: String,
                             val shoppingMallName: String?,
                             val goodsPicUrl: String,
                             val goodsPicUrl1: String,
                             val goodsPicUrl2: String,
                             val goodsPicUrl3: String,
                             val goodsPicUrl4: String)
}
