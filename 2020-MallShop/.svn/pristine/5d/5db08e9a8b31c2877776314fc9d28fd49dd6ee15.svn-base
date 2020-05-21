package com.epro.mall.mvp.model.bean

import com.mike.baselib.interface_.Judgable

data class Spec(val id:String, val name:String, var specificationsValueList:ArrayList<SpecValue>, override var judgeValue: Boolean=false) :Judgable{
    override fun judge(): Boolean {
        return judgeValue
    }
}