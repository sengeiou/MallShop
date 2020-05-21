package com.epro.mall.mvp.model.bean

/*| 字段              | 类型    | 必须 | 说明                                                         |
| ----------------- | ------- | ---- | ------------------------------------------------------------ |
| orderSn           | string  | Y    | 订单编号                                                     |
| orderStatus       | integer | Y    | 订单状态(1待支付,3待配送,4待自提 5配送中 6交易完成 2和7交易取消) |
| shopId            | string  | Y    | 商铺id                                                       |
| shopName          | string  | Y    | 店铺名称                                                     |
| productCount      | integer | Y    | 产品总数量                                                   |
| orderActualAmount | decimal | Y    | 实际支付总金额                                               |
| orderTotalAmount                      订单总金额折扣后价格
| deliveryType      | integer | Y    | 取货方式(0自提,1配送)                                        |
| products          | list    | Y    | 该订单下的产品列表                                           |*/

/*| 字段                      | 类型    | 必须 | 说明                         |
| ------------------------- | ------- | ---- | ---------------------------- |
| goodsName                 | string  | Y    | 商品名                       |
| productId                 | long    | Y    | 产品id                       |
| salePrice                 | decimal | Y    | 产品价格                     |
| productCount              | integer | Y    | 产品数量                     |
| listPicUrl                | string  | Y    | 产品图片链接                 |
| goodsSpecifitionNameValue | integer | Y    | 产品规则(白色,250ml)逗号隔开 |*/
data class Order(val orderTotalAmount:String,val deliveryCode:String,val orderSn: String,val orderStatus:String,val shopId:String,val shopName:String,val productCount:String,val orderActualAmount:String,val deliveryType:String,val products:ArrayList<OrderProduct>,val payType:Int){
}
/*data class Order(val orderId: Long,val orderSn:String,val orderStatus:Integer,val shopId:String,val shopName:String,
val shopLogoUrl:String,val productsNumber:Integer,val orderPrice:String,val reciveType:Integer,val products:List<OrderProduct>)*/
