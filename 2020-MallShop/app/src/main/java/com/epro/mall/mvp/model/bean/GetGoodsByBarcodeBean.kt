package com.epro.mall.mvp.model.bean

data class GetGoodsByBarcodeBean(override val code: Int, override val message: String, override val result: ScanBuyCartGoods) : BaseBean<ScanBuyCartGoods> {
    data class Send(val shopId: String, val productBarCode: String)
}
