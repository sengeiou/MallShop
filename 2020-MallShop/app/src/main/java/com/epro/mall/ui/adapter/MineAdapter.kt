package com.epro.mall.ui.adapter


import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.Item
import com.epro.mall.mvp.model.bean.ItemMine
import com.mike.baselib.utils.AppUtils
import com.mike.baselib.utils.DisplayManager

/**
 * desc: 分类的 Adapter
 */

class MineAdapter(mContext: Context, itemList: ArrayList<ItemMine>, layoutId: Int=R.layout.item_mine) :
        CommonAdapter<ItemMine>(mContext, itemList, layoutId) {
    // 点击弹窗取消按钮回调
    interface OnItemClickListener {
        fun onClick(item:ItemMine)
    }

    var onItemClickListener:OnItemClickListener?=null

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: ItemMine, position: Int) {
        holder.setImageResource(R.id.ivFlag,data.icon)
        holder.setText(R.id.tvTitle,data.content)
        holder.setBackgroundResource(R.id.rlBg,data.background!!)
        if(0 == position){
            var p = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
            p.bottomMargin = DisplayManager.dip2px(12.toFloat())!!
            holder.itemView.layoutParams = p
            holder.getView<View>(R.id.vLine).visibility = View.GONE
        }else{
            var p = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
            p.bottomMargin = 0
            holder.itemView.layoutParams = p
            holder.getView<View>(R.id.vLine).visibility = View.VISIBLE
        }
        if (mContext.getString(R.string.about_us)==mData[position].content){
            holder.getView<TextView>(R.id.tvVersion).visibility = View.VISIBLE
            holder.setText(R.id.tvVersion,mContext.getString(R.string.version_name)+AppUtils.getVerName(mContext))
        }else{
            holder.getView<TextView>(R.id.tvVersion).visibility = View.GONE
        }

        holder.setOnItemClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                if(onItemClickListener!=null){
                    onItemClickListener!!.onClick(data)
                }
            }
        })
    }
}
