package com.mike.baselib.view.recyclerview.adapter

/**
 *
 * Description: Adapter条目的长按事件
 */
interface OnItemLongClickListener<M> {

    fun onItemLongClick(m: M, position: Int): Boolean

}
