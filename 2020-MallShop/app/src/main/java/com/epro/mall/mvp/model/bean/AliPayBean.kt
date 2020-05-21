package com.epro.mall.mvp.model.bean


/*
返回值
| 字段    | 类型   | 必须 | 说明     |
| ------- | ------ | ---- | -------- |
| appInfo | String | Y    | 产品介绍 |
| version | List   | Y    | 版本信息 |
*/
data class AliPayBean(override val code: Int, override val message: String, override val result: Any?):BaseBean<Any?> {
}