package com.epro.mall.mvp.model.bean

import com.mike.baselib.interface_.Judgable


data class ShopCart(val shopId: String,
                    var products: ArrayList<CartGoods>, var shopName: String,val isValid: Int=1,override var judgeValue: Boolean=false):Judgable{
    override fun judge(): Boolean {
       return judgeValue
    }
    var shopAddress:String=""
    var send:CreateOrderBean.Send?=null
}