package com.epro.mall.mvp.model.bean

data class GetShopNewGoodsListBean(override val code: Int, override val message: String, override val result: ArrayList<NewGoods>) : BaseBean<ArrayList<GetShopNewGoodsListBean.NewGoods>> {
    data class NewGoods(val goodsCompressPriUrl: String,
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
                        val products: ArrayList<Product>?,
                        val shopGoodsDesc: String,
                        val shopGoodsTitle: String,
                        val shopId: String,
                        val sortOrder: String,
                        val status: Int) {
    }

    data class Product(val compressPicUrl: String,
                       val productPicUrl: String)
}