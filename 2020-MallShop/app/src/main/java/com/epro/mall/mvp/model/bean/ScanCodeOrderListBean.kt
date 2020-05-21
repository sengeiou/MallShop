package com.epro.mall.mvp.model.bean


/*| 字段   | 类型   | 必须 | 说明     |
| ------ | ------ | ---- | -------- |
| shopId | string | Y    | 店铺id   |
| page   | object | Y    | 分页信息 |*/

/*
| 字段              | 类型    | 必须 | 说明                                                         |
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
| products          | list    | Y    | 订单商品列表                                                 |
*/

data class ScanCodeOrderListBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<ScanCodeOrderListBean.Result> {
    data class Result(val total: String,val rows:List<ScanCodeOrderListOneBean>)
  /*  data class ScanCodeOrderListOneBean(val orderSn:String,val orderStatus:String,val createTime:String,val productCount:String,val orderTotalAmount:String,val discount:String,val orderActualAmount:String,
                                        val payType:String,val shopId:String,val shopName:String,val shopAddress:String,val shopMobile:String,val products:List<ScanCodeProductsBean>)
    data class ScanCodeProductsBean(val productId:String,val productCount:String,val goodsName:String,val goodsSpecifitionNameValue:String,val salePrice:String)*/
    data class Send(val shopId: String,val page:PageList)
    data class PageList(val no:Int,val size :Int =15)
}

/*
products
| 字段                      | 类型    | 必须 | 说明     |
| ------------------------- | ------- | ---- | -------- |
| productId                 | string  | Y    | 商品id   |
| productCount              | integer | Y    | 商品数量 |
| goodsName                 | string  | Y    | 商品名称 |
| goodsSpecifitionNameValue | string  | Y    | 商品规则 |
| salePrice                 | string  | Y    | 售价     |*/
