package com.epro.mall.mvp.model.bean


data class GetShopHomeViewBean(val code: Int, val message: String, val result: Result) {
    data class Result(val goodsHotsList: ArrayList<HotsGoods>,
                      val homeRecommendList: ArrayList<RecommendGoods>,
                      val shopId: String,
                      val shopLogo: String,
                      val shopName: String) {


    }


    data class HotsGoods(val goodsId: String,
                         val goodsName: String,
                         val goodsTypeId: String,
                         val goodsTypeName: String,
                         val id: String,
                         val isShow: Int,
                         val onlineSalesPrice: String,
                         val activityOnlinePrice: String?,
                         val shopGoodsDesc: String,
                         val shopGoodsImage: String,
                         val shopGoodsTitle: String,
                         val shopId: String,
                         val sortOrder: Int,
                         val status: Int)

    data class RecommendGoods(val goodsCompressPriUrl: String,
                              val goodsCompressPriUrl1: String,
                              val goodsCompressPriUrl2: String,
                              val goodsCompressPriUrl3: String,
                              val goodsCompressPriUrl4: String,
                              val goodsId: String,
                              val goodsName: String,
                              val goodsPicUrl: String,
                              val goodsPicUrl1: String,
                              val goodsPicUrl2: String,
                              val goodsPicUrl3: String,
                              val goodsPicUrl4: String,
                              val goodsTypeId: String,
                              val goodsTypeName: String,
                              val id: String,
                              val isShow: Int,
                              val onlineSalesPrice: String,
                              val activityOnlinePrice: String?,
                              val products: ArrayList<ProductsBean>,
                              val shopGoodsDesc: String,
                              val shopGoodsTitle: String,
                              val shopId: String,
                              val sortOrder: Int,
                              val status: Int) {

        data class ProductsBean(val compressPicUrl: String,
                                val productPicUrl: String)
    }
}