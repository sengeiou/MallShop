package com.epro.mall.mvp.model.bean

/*
| 字段                      | 类型    | 必须 | 说明                         |
| ------------------------- | ------- | ---- | ---------------------------- |
"discount": "0.80",
"discountPrice": "2.24",
| goodsName                 | string  | Y    | 商品名                       |
| productId                 | long    | Y    | 产品id                       |
| salePrice                 | decimal | Y    | 产品价格                     |
| productCount              | integer | Y    | 产品数量                     |
| listPicUrl                | string  | Y    | 产品图片链接                 |
| goodsSpecifitionNameValue | integer | Y    | 产品规则(白色,250ml)逗号隔开 |
*/
data class OrderProduct (val goodsName:String,val productId:String,val salePrice:String,val productCount:String,
                         val listPicUrl:String,val goodsSpecifitionNameValue:String,val discountPrice:String,val discount:Float){
}