package com.mike.baselib.view.recyclerview.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mike.baselib.utils.DisplayManager
import com.mike.baselib.utils.LogTools

import com.mike.baselib.view.recyclerview.MultipleType
import com.mike.baselib.view.recyclerview.ViewHolder

/**
 * Created by xuhao on 2017/11/22.
 * desc: 通用的 Adapter
 */

abstract class CommonAdapter<T>(var mContext: Context, var mData: ArrayList<T>, //条目布局
                                private var mLayoutId: Int,var isRecyclable:Boolean=true) : RecyclerView.Adapter<ViewHolder>() {
    protected var mInflater: LayoutInflater? = null
    private var mTypeSupport: MultipleType<T>? = null
    protected val logTools=LogTools("CommonAdapter_"+this.javaClass.simpleName)

    //使用接口回调点击事件
    private var mItemClickListener: OnItemClickListener<T>? = null

    //使用接口回调点击事件
    private var mItemLongClickListener: OnItemLongClickListener<T>? = null

    init {
        mInflater = LayoutInflater.from(mContext)
    }

    /**
     * 设置新数据
     */
    open fun setData(list: ArrayList<T>) {
        mData.clear()
        mData.addAll(list)
        notifyDataSetChanged()
    }

    open fun setItemData(data: T, position: Int) {
        mData.removeAt(position)
        mData.add(position, data)
        notifyItemChanged(position)
    }

    //需要多布局
    constructor(context: Context, data: ArrayList<T>, typeSupport: MultipleType<T>) : this(context, data, -1) {
        this.mTypeSupport = typeSupport
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (mTypeSupport != null) {
            //需要多布局
            mLayoutId = viewType
        }

        //创建view
        val view = mInflater?.inflate(mLayoutId, parent, false)
        return ViewHolder(view!!)
    }


    override fun getItemViewType(position: Int): Int {
        //多布局问题
        return mTypeSupport?.getLayoutId(mData[position], position)
                ?: super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(isRecyclable)
        //绑定数据
        bindData(holder, mData[position], position)
        //条目点击事件
        mItemClickListener?.let {
            holder.itemView.setOnClickListener { mItemClickListener!!.onItemClick(mData[position], position) }
        }

       // 长按点击事件
        mItemLongClickListener?.let {
            holder.itemView.setOnLongClickListener { mItemLongClickListener!!.onItemLongClick(mData[position], position) }
        }
    }

    /**
     * 将必要参数传递出去
     *
     * @param holder
     * @param data
     * @param position
     */
    protected abstract fun bindData(holder: ViewHolder, data: T, position: Int)

    override fun getItemCount(): Int {
        return mData.size
    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener<T>) {
        this.mItemClickListener = itemClickListener
    }

    fun setOnItemLongClickListener(itemLongClickListener: OnItemLongClickListener<T>) {
        this.mItemLongClickListener = itemLongClickListener
    }

    /**
     * recycleview 没有左右margin
     */
    fun handlerHMargin(columns:Int,marginDp:Float,position: Int,holder: ViewHolder){
        val margin = DisplayManager.dip2px(marginDp)!!
        val ratio=(columns+1)/columns.toFloat()-1
        val params = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
        for(i in 0 until columns){
            if((position-i) %columns==0) {
                params.leftMargin = (margin * (i*ratio)).toInt()
                params.rightMargin = (margin * (1-(1+i)*ratio)).toInt()
            }
        }
        holder.itemView.layoutParams = params
    }

    /**
     * recycleview 有左右margin
     */
    fun handlerHMargin2(columns:Int,marginDp:Float,position: Int,holder: ViewHolder){
        val margin = DisplayManager.dip2px(marginDp)!!
        val ratio=(columns+1)/columns.toFloat()-1
        val params = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
        for(i in 0 until columns){
            if((position-i) %columns==0) {
                params.leftMargin = (margin * (1-i*ratio)).toInt()
                params.rightMargin = (margin * ((i+1)*ratio)).toInt()
            }
        }
        holder.itemView.layoutParams = params
    }
}
