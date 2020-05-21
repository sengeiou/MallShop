package com.epro.comp.im.mvp.model.bean

import com.mike.baselib.interface_.Judgable

data class Picture(val imageUrl: String, val content:String,override var judgeValue: Boolean = false) : Judgable {
    override fun judge(): Boolean {
        return judgeValue
    }
}