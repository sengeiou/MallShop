package com.epro.mall.mvp.model.bean

import com.mike.baselib.interface_.Judgable
/*
| 字段       | 类型   | 必须 | 说明         |
| ---------- | ------ | ---- | ------------ |
| shopId         | String | Y    | 主键ID       |
| receiver   | String | Y    | 收货人       |
| email      | String | Y    | 邮箱账号     |
| phone      | String | Y    | 手机号码     |
| province   | string | Y    | 省           |
| city       | string | Y    | 市           |
| title       | string | Y    | 区           |
| address    | String | Y    | 地址         |
| is_default | String | Y    | 是否默认地址 |*/

data class AddressListItem(val id: String,val receiver:String,val email:String,val phone:String ,val province:String,val city:String,val area:String,val address:String,val is_default:String, override var judgeValue: Boolean=false):Judgable{
    override fun judge(): Boolean {
       return judgeValue
    }

}