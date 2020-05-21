package com.mike.baselib.base

/**
 * Created by xuhao on 2017/11/30.
 * desc: 契约类
 */
interface ListView<D> :IBaseView{
    fun getListDataSuccess(list: List<D>,type:String)
}