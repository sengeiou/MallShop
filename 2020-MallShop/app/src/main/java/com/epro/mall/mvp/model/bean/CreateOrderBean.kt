package com.epro.mall.mvp.model.bean


data class CreateOrderBean(override val code: Int,
                           override val message: String,
                           override val result: Result) : BaseBean<CreateOrderBean.Result> {

    //    | 字段              | 类型       | 必须  | 说明                               |
//    | ----------------- | ---------- | ----- | ---------------------------------- |
//    | shopId            | string     | Y     | 店铺Id                             |
//    | deliveryType      | integer    | Y     | 送货方式(0.自提 1.配送)            |
//    | addressId         | string     | Y     | 送货地址id(当为配送时,必传)        |
//    | logisticsFee      | string     | Y     | 运输费用(没有费用时传0,不能传null) |
//    | productCount      | integer    | Y     | 订单商品总数量                     |
//    | orderActualAmount | string     | Y     | 订单总金额(包含运费)               |
//    | payType           | integer    | Y     | 支付类型                           |
//    | pickUpTime        | string     | N     | 自提时间                           |
//    | ~~pickUpAdress~~  | ~~string~~ | ~~N~~ | ~~自提地址~~(删除,不用传)          |
//    | products          | list       | Y     | 商品列表                           |                    |
    data class Send(val shopId: String, var deliveryType: Int, var addressId: String = "", val logisticsFee: String = "0"
                    , val productCount: Int, val orderActualAmount: String, var payType: Int, var pickUpTime: String, val products: ArrayList<CartGoods>) {
    }

    data class Result(val notifyStr: String, val orderSn: String)
}