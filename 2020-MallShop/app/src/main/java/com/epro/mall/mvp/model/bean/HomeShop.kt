package com.epro.mall.mvp.model.bean

data class HomeShop(val apphomeRecommendVOList: ArrayList<RecommendGoods>,
                    val goodsTypeList: ArrayList<GoodsType>,
                    val id: String,
                    val distance:Int,
                    val latitude: String,
                    val longitude: String,
                    val shopId: String,
                    val saleRange: Int,
                    val shopLogo: String,
                    val shopName: String) {

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
                              val isShow: Int,
                              val onlineSalesPrice: String,
                              val activityOnlinePrice: String?,
                              val products: ArrayList<ProductsBean>,
                              val shopGoodsTitle: String,
                              val shopId: String,
                              val sortOrder: Int,
                              val status: Int) {

        data class ProductsBean(val compressPicUrl: String,
                                val productPicUrl: String)
    }

    data class GoodsType(val goodsTypeId: String,
                         val goodsTypeName: String,
                         val isShow: Int,
                         val shopId: String,
                         val sortOrder: String)
}