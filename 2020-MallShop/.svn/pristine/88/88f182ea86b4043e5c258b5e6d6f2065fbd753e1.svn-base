package com.epro.mall.ui.adapter


import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.CartGoods
import com.epro.mall.utils.MallBusManager
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.ext_formatAmount
import com.mike.baselib.utils.ext_loadImageWithDomain
import com.mike.baselib.utils.ext_setTextWithHorizontalLine
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter

/**
 * desc: 分类的 Adapter
 */

class OrderInfoChildAdapter(mContext: Context, list: ArrayList<CartGoods>, layoutId: Int = R.layout.item_order_stub) :
        CommonAdapter<CartGoods>(mContext, list, layoutId) {

    interface OnItemClickListener {
        fun onClick(item: CartGoods)
    }


    var onItemClickListener: OnItemClickListener? = null

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: CartGoods, position: Int) {
        holder.setText(R.id.tvDesc,data.shoppingMallName.toString())
        holder.setText(R.id.tvSpec,data.goodsSpecifitionNameValue)
        holder.setText(R.id.tvSymbol,AppBusManager.getAmountUnit())
        holder.setText(R.id.tvPrice,data.salePrice.ext_formatAmount())
        holder.setText(R.id.tvBuyNumber,"x"+data.productCount)
        val ivImage=holder.getView<ImageView>(R.id.ivGoodsImg)
        ivImage.ext_loadImageWithDomain(data.listPicUrl)

        //如果商品有活动
        val llActivity=holder.getView<View>(R.id.llActivity)
        val tvOriginalPrice=holder.getView<TextView>(R.id.tvOriginalPrice)
        val a=MallBusManager.getProductActivity(data)
        if(a==null){
            llActivity.visibility=View.GONE
        }else{
            llActivity.visibility=View.VISIBLE
            holder.setText(R.id.tvPrice,a.discountPrice.ext_formatAmount())
            tvOriginalPrice.ext_setTextWithHorizontalLine(AppBusManager.getAmountUnit()+" "+data.salePrice.ext_formatAmount())
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
