package com.epro.mall.mvp.model.bean

import com.mike.baselib.interface_.Judgable

data class SpecValue(val id:String, val specificationsValue:String, val parentId:String, override var judgeValue: Boolean=false):Judgable {
    override fun judge(): Boolean {
       return judgeValue //是否被选中
    }
    var isLongLock=false //从初始化开始就不能点击
    var isLock=false//锁住,不能点
}