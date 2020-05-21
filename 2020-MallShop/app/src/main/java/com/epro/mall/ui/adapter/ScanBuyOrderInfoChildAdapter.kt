package com.epro.mall.ui.adapter


import android.content.Context
import android.view.View
import android.widget.TextView
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.ScanBuyCartGoods
import com.epro.mall.utils.MallBusManager
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.ext_formatAmount
import com.mike.baselib.utils.ext_setTextWithHorizontalLine
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter

/**
 * desc: 分类的 Adapter
 */

class ScanBuyOrderInfoChildAdapter(mContext: Context, list: ArrayList<ScanBuyCartGoods>, layoutId: Int = R.layout.item_scanbuy_orderinfo) :
        CommonAdapter<ScanBuyCartGoods>(mContext, list, layoutId) {

    interface OnItemClickListener {
        fun onClick(item: ScanBuyCartGoods)
    }


    var onItemClickListener: OnItemClickListener? = null

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: ScanBuyCartGoods, position: Int) {
        holder.setText(R.id.tvDesc,data.goodsName)
        holder.setText(R.id.tvUnit,AppBusManager.getAmountUnit())
        holder.setText(R.id.tvPrice,data.retailPrice.ext_formatAmount())
        holder.setText(R.id.tvBuyNumber,"x"+data.buyNum)

        //如果商品有活动
        val llActivity=holder.getView<View>(R.id.llActivity)
        val tvOriginalPrice=holder.getView<TextView>(R.id.tvOriginalPrice)
        val a= MallBusManager.getProductActivity(data)
        if(a==null){
            llActivity.visibility=View.GONE
        }else{
            llActivity.visibility=View.VISIBLE
            holder.setText(R.id.tvPrice,a.discountPrice.ext_formatAmount())
            tvOriginalPrice.ext_setTextWithHorizontalLine(AppBusManager.getAmountUnit()+" "+data.retailPrice.ext_formatAmount())
        }
        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onClick(data)
                }
            }
        })
    }
}
