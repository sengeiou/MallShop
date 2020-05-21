package com.epro.mall.mvp.model.bean

import com.mike.baselib.interface_.Judgable


data class GoodsCategory(val id :String,
                         val name:String, override var judgeValue:Boolean=false):Judgable{
    override fun judge(): Boolean {
       return judgeValue
    }

}