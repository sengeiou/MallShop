package com.epro.mall.mvp.model.bean


/*| 字段              | 类型    | 必须 | 说明                                                         |
| ----------------- | ------- | ---- | ------------------------------------------------------------ |
| orderSn           | string  | Y    | 订单编号                                                     |
| orderStatus       | integer | Y    | 订单状态(1.未支付 2.用户已取消3.待配送 4.待自提 5.配送中  6.交易完成  7.店家已取消) |
| createTime        | string  | Y    | 订单创建时间                                                 |
| productCount      | integer | Y    | 产品数量                                                     |
| orderTotalAmount  | string  | Y    | 订单总价啊                                                   |
| discount          | string  | Y    | 整单折扣                                                     |
| orderActualAmount | string  | Y    | 订单折后总价                                                 |
| payType           | integer | Y    | 支付类型                                                     |
| shopId            | string  | Y    | 店铺id                                                       |
| shopName          | string  | Y    | 店铺名称                                                     |
| shopAddress       | string  | Y    | 店铺地址                                                     |
| shopMobile        | string  | Y    | 店铺电话                                                     |
|                   |         |      |                                                              |
| products          | list    | Y    | 订单商品列表                                                 |*/
data class ScanCodeOrderListOneBean(val orderSn:String,val orderStatus:String,val createTime:String,val productCount:String,val orderTotalAmount:String,val discount:String,val orderActualAmount:String,
                               val payType:String,val shopId:String,val shopName:String,val shopAddress:String,val shopMobile:String,val products:ArrayList<ScanCodeProductsBean>){

    data class ScanCodeProductsBean(val productId:String, val productCount:String, val goodsName:String, val goodsSpecifitionNameValue:String, val salePrice:String)

}