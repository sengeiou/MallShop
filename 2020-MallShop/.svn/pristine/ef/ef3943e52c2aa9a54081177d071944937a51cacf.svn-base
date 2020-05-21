package com.epro.mall.mvp.model.bean

import com.mike.baselib.interface_.Judgable

data class Item(val icon:Int, var content:String,override var judgeValue: Boolean=false):Judgable{
    override fun judge(): Boolean {
        return judgeValue
    }

    var content2=""
    fun valueContent2(content: String):Item{
        this.content2=content
        return this
    }

    var background2:Int?=null
    fun valueBackgroud2(background:Int):Item{
        this.background2 = background
        return this
    }
}