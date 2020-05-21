package com.epro.mall.mvp.model.bean

data class SearchShop(val apphomeRecommendVOList: ArrayList<RecommendGoods>,
                     val distance: Int,
                     val goodsTypeList: List<GoodsTypeListBean>,
                     val id: String,
                     val latitude: String,
                     val longitude: String,
                     val saleRange: String,
                     val shopId: String,
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
                                          val isShow: String,
                                          val onlineSalesPrice: String,
                                          val activityOnlinePrice: String?,
                                          val shopGoodsTitle: String,
                                          val shopId: String,
                                          val sortOrder: Int,
                                          val status: String)

    data class GoodsTypeListBean(val goodsTypeId: String,
                                 val goodsTypeName: String,
                                 val isShow: Int,
                                 val shopId: String,
                                 val sortOrder: String)
}