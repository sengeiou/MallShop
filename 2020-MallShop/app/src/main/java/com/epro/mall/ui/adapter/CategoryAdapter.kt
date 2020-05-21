package com.epro.mall.ui.adapter


import android.content.Context
import android.view.View
import android.widget.TextView
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.GoodsCategory
import com.epro.mall.mvp.model.bean.ShopGoodsCategory
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter


/**
 * Created by xuhao on 2017/11/29.
 * desc: 分类的 Adapter
 */

class CategoryAdapter(mContext: Context, categoryList: ArrayList<ShopGoodsCategory>, layoutId: Int=R.layout.item_category) :
        CommonAdapter<ShopGoodsCategory>(mContext, categoryList, layoutId) {
    // 点击弹窗取消按钮回调
    interface OnItemClickListener {
        fun onItemClick(category:ShopGoodsCategory,position: Int)
    }
    var onItemClickListener:OnItemClickListener?=null

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: ShopGoodsCategory, position: Int) {

        holder.setText(R.id.tvCategoryName,data.goodsTypeName)
        holder.itemView.setBackgroundColor(mContext.resources.getColor( if (data.judgeValue) R.color.white else android.R.color.transparent))
        holder.setViewVisibility(R.id.ivIndicator,if (data.judgeValue) View.VISIBLE else View.GONE)
        val textView=holder.getView<TextView>(R.id.tvCategoryName)
        textView.paint.isFakeBoldText=data.judgeValue
        textView.setTextColor(if (data.judgeValue) mContext.resources.getColor(R.color.mainColor) else mContext.resources.getColor(R.color.mainTextColor))
        holder.setOnItemClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                if(onItemClickListener!=null){
                    onItemClickListener!!.onItemClick(data,position)
                    for(item in mData){
                        item.judgeValue=item.goodsTypeId.equals(data.goodsTypeId)
                    }
                    notifyDataSetChanged()
                }

            }
        })
    }
}
