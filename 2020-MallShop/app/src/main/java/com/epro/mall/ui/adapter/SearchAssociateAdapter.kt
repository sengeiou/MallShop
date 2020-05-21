package com.epro.mall.ui.adapter


import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.Item
import com.mike.baselib.utils.ext_Highlight

/**
 * desc: 分类的 Adapter
 */

class SearchAssociateAdapter(mContext: Context, itemList: ArrayList<String>, layoutId: Int=R.layout.item_search) :
        CommonAdapter<String>(mContext, itemList, layoutId) {
    // 点击弹窗取消按钮回调
    interface OnItemClickListener {
        fun onClick(item:String)
    }


    var onItemClickListener:OnItemClickListener?=null
    var keyword:String?=null
    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: String, position: Int) {
        val tvContent=holder.getView<TextView>(R.id.tvContent)
        if(!TextUtils.isEmpty(keyword)){
            tvContent.ext_Highlight(data,keyword!!)
        }

        //holder.setText(R.id.tvContent,data)
        holder.setOnItemClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                if(onItemClickListener!=null){
                    onItemClickListener!!.onClick(data)
                }
            }
        })
    }
}
