package com.epro.mall.mvp.model.bean

//
//| 字段     | 类型    | 必须 | 说明         |
//| -------- | ------- | ---- | ------------ |
//| shopId   | integer |      | 店铺ID       |
//| shopName | string  |      | 店铺名称     |
//| logoUrl  | string  |      | 店铺图片路径 |
//| shopDesc | string  |      | 店铺描述信息 |
data class IMShop(val shopId: String, val shopName:String="", val logoUrl:String="", val shopDesc:String=""  )