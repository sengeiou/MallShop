package com.epro.mall.mvp.model.bean

//| 字段     | 类型   | 必须 | 说明     |
//| -------- | ------ | :--- | -------- |
//| email    | string | Y    | 邮箱账号 |
//| mobile   | string | Y    | 手机号码 |
//| userName | string | Y    | 响应数据 |
//| icon     | String | Y    | 头像     |
data class User(val email :String,
                val mobile:Boolean,
                val userName:String,
                var icon:String)