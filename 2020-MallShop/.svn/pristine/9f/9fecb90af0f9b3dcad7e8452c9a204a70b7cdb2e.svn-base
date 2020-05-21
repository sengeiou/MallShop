package com.epro.mall.ui.adapter


import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.GetShopNewGoodsListBean
import com.epro.mall.mvp.model.bean.Goods
import com.epro.mall.ui.activity.GoodsDetailActivity
import com.epro.mall.utils.MallBusManager
import com.mike.baselib.utils.*

/**
 * desc: 分类的 Adapter
 */

class ShopNewGoodsListAdapter(mContext: Context, list: ArrayList<GetShopNewGoodsListBean.NewGoods>, layoutId: Int = R.layout.item_goodslist) :
        CommonAdapter<GetShopNewGoodsListBean.NewGoods>(mContext, list, layoutId) {

    interface OnItemClickListener {
        fun onClick(item: GetShopNewGoodsListBean.NewGoods)
    }

    var onItemClickListener: OnItemClickListener? = null

    /**
     * 绑定数据
     */
    var imageViewWidth = ((DisplayManager.getScreenWidth()!! - DisplayManager.dip2px(36f)!!) / 2F).toInt()
    override fun bindData(holder: ViewHolder, data: GetShopNewGoodsListBean.NewGoods ,position: Int) {
        holder.getView<TextView>(R.id.tvDesc).text=data.shopGoodsTitle
        val ivImage=holder.getView<ImageView>(R.id.ivImage)
        ivImage.ext_loadConnersImageWithDomain(data.goodsPicUrl)
        var params = ivImage.layoutParams
        params.width = imageViewWidth
        params.height = (params.width * (100F / 100)).toInt()
        ivImage.layoutParams = params
        holder.setText(R.id.tvUnit,AppBusManager.getAmountUnit())
        //holder.setText(R.id.tvBuyNumber,data.sortOrder+"人付款")
        val param=holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
        val margin=DisplayManager.dip2px(6f)!!
        if(position==0||position==1){
            param.topMargin=margin*2
            param.bottomMargin=margin
        }else{
            param.topMargin=margin
            param.bottomMargin=margin
        }
        holder.itemView.layoutParams=param
        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onClick(data)
                }
            }
        })

        holder.setText(R.id.tvPrice, MallBusManager.getMiniPrice(data.onlineSalesPrice))
        holder.setViewVisibility(R.id.tvQi,if(MallBusManager.isIntervalPrice(data.onlineSalesPrice)) View.VISIBLE else View.GONE)
        //处理有活动的情况
        val tvOriginalPrice = holder.getView<TextView>(R.id.tvOriginalPrice)
        val llActivity = holder.getView<View>(R.id.llActivity)
        if(!MallBusManager.isProductHaveActivity(data.activityOnlinePrice,data.onlineSalesPrice)){
            llActivity.visibility=View.GONE
        }else{
            holder.setViewVisibility(R.id.tvQi,if(MallBusManager.isIntervalPrice(data.activityOnlinePrice!!)) View.VISIBLE else View.GONE)
            holder.setText(R.id.tvPrice, MallBusManager.getMiniPrice(data.activityOnlinePrice!!))
            tvOriginalPrice.ext_setTextWithHorizontalLine(MallBusManager.getMiniPrice(data.onlineSalesPrice).ext_formatAmountWithUnit())
            llActivity.visibility=View.VISIBLE
        }

    }
}
