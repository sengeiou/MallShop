package com.epro.mall.mvp.model.bean

/*| 字段              | 类型    | 必须 | 说明                                                         |
| ----------------- | ------- | ---- | ------------------------------------------------------------ |
| orderSn           | string  | Y    | 订单编号                                                     |
| orderStatus       | integer | Y    | 订单状态(1待支付,3待配送,4待自提 5配送中 6交易完成 2和7交易取消) |
| shopId            | string  | Y    | 商铺id                                                       |
| shopName          | string  | Y    | 店铺名称                                                     |
| productCount      | integer | Y    | 产品总数量                                                   |
| orderActualAmount | decimal | Y    | 实际支付总金额                                               |
| deliveryType      | integer | Y    | 取货方式(0自提,1配送)                                        |
| products          | list    | Y    | 该订单下的产品列表                                           |*/

data class OrderListBean(val code: Int, val message: String, val result: Result) {
    data class Result(val total:String, val rows: List<Order>)
    data class Send(val orderStatus:String,val page:PageList)
    data class PageList(val no:Int,val size: Int=15)
}