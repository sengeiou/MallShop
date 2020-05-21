package com.epro.mall.ui.adapter


import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.Order
import com.epro.mall.mvp.model.bean.OrderProduct
import com.epro.mall.ui.activity.GoodsDetailActivity
import com.epro.mall.utils.MallBusManager
import com.mike.baselib.utils.*
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter

/**
 * desc: 分类的 Adapter
 */

class OrderListChildAdapter(mContext: Context, list: ArrayList<OrderProduct>, layoutId: Int = R.layout.item_order_stub) :
        CommonAdapter<OrderProduct>(mContext, list, layoutId) {

    interface OnItemClickListener {
        fun onClick(item: OrderProduct)
    }

    var onItemClickListener: OnItemClickListener? = null

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: OrderProduct, position: Int) {
        holder.setText(R.id.tvSymbol,AppBusManager.getAmountUnit())
        holder.setText(R.id.tvDesc,data.goodsName)
        holder.setText(R.id.tvSpec,data.goodsSpecifitionNameValue)
        holder.setText(R.id.tvPrice,data.salePrice.ext_formatAmount())
        holder.setText(R.id.tvBuyNumber,"x"+data.productCount)
        val ivImage=holder.getView<ImageView>(R.id.ivGoodsImg)
        ivImage.ext_loadConnersImageWithDomain(data.listPicUrl)
        holder.setOnItemClickListener(View.OnClickListener {
            if (onItemClickListener != null) {
                onItemClickListener!!.onClick(data)
            }
        })

        //当价格有优惠的情况
        val llActivity= holder.getView<View>(R.id.llActivity)
        val tvOriginalPrice= holder.getView<TextView>(R.id.tvOriginalPrice)
        if( data.discount==1F || TextUtils.isEmpty(data.discountPrice)){
            llActivity.visibility=View.GONE
        }else{
            llActivity.visibility=View.VISIBLE
            holder.setText(R.id.tvPrice,data.discountPrice.ext_formatAmount())
            tvOriginalPrice.ext_setTextWithHorizontalLine(AppBusManager.getAmountUnit()+" "+data.salePrice.ext_formatAmount())
        }
    }
}
