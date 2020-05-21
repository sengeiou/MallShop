package com.epro.mall.mvp.model.bean


data class ScanCodeOrderListDetailBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<ScanCodeOrderListDetailBean.Result> {
    data class Result(val orderSn:String,val orderStatus:String,val createTime:String,val productCount:String,val orderTotalAmount:String,val discount:String,val orderActualAmount:String,
                      val payType:String,val shopId:String,val shopName:String,val shopAddress:String,val shopMobile:String,val products:ArrayList<ScanCodeProductsBean>)
    data class ScanCodeProductsBean(val discount: Float,val discountPrice:String, val productId:String,val listPicUrl:String, val productCount:String, val goodsName:String, val goodsSpecifitionNameValue:String, val salePrice:String)
    data class Send(val orderSn: String)
}
