package com.epro.mall.mvp.model.bean

import com.mike.baselib.interface_.Judgable

data class Address(val address: String,
                   val area: String,
                   val city: String,
                   val createTime: String,
                   val id: String,
                   val isDefult: Int,
                   val latitude: String,
                   val location: String,
                   val longitude: String,
                   val mobile: String,
                   val province: String,
                   val receive: String,
                   val userId: String, override var judgeValue: Boolean = false) : Judgable {
    override fun judge(): Boolean {
        return judgeValue
    }

    var isEnabled=true
    var distance=0
}