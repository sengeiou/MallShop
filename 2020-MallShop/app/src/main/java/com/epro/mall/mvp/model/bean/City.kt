package com.epro.mall.mvp.model.bean

import com.epro.mall.mvp.model.cn.CN

data class City(val id: String,
                     val pid: String,
                     val city_code: String,
                     val city_name: String):CN {
    override fun chinese(): String {
       return city_name
    }
}
