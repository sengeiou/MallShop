package com.epro.mall.mvp.model.bean


data class CreateScanBuyOrderBean(override val code: Int, override val message: String, override val result: Result):BaseBean<CreateScanBuyOrderBean.Result> {
    data class Result(val notifyStr: String, val orderSn: String)

//    | 字段          | 类型    | 必须 | 说明                                                |
//    | ------------- | ------- | ---- | --------------------------------------------------- |
//    | shopId        | string  | Y    | 店铺id                                              |
//    | productCount  | integer | Y    | 商品总数量                                          |
//    | totalAmount   | string  | Y    | 商品折前总金额                                      |
//    | totalDiscount | string  | Y    | 商品总折扣                                          |
//    | discountPrice | string  | Y    | 商品总折后价                                        |
//    | payType       | integer | Y    | 付款方式2.支付宝 3.paypal 4.微信 5.信用卡 6.银行卡) |
//    | products      | list    | Y    | 产品列表                                            |
    data class Send(val shopId:String,val productCount:Int,val totalAmount:String,val totalDiscount:Float,val discountPrice:String,var payType:Int,val products:ArrayList<Product>)


//    | 字段         | 类型    | 必须 | 说明     |
//    | ------------ | ------- | ---- | -------- |
//    | productId    | string  | Y    | 商品id   |
//    | productCount | integer | Y    | 商品数量 |
//    | salePrice    | string  | Y    | 售价     |
    data class Product(val productId:String,val productCount:Int,val salePrice:String)
}