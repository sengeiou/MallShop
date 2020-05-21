package com.epro.mall.mvp.model.bean

data class ScanBuyCartGoods(val onlineSalesPrice: String,
                  var retailPrice: String,
                  val productBarCode: String,
                  val productId: String,
                  val goodsId: String,
                  val productNumber: Int,
                  val goodsName: String,
                  val specificationsValueNames: String,val offlineActivityInfo:String,val onlineActivityInfo:String ){
    var buyNum=1
}
