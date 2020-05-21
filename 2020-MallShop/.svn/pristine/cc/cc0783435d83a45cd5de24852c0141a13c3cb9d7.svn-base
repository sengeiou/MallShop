package com.epro.mall.ui.adapter


import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.GetShopHomeViewBean
import com.epro.mall.utils.MallBusManager
import com.mike.baselib.utils.*

/**
 * desc: 分类的 Adapter
 */

class RecomGoodsAdapter(mContext: Context, itemList: ArrayList<GetShopHomeViewBean.RecommendGoods>, layoutId: Int=R.layout.item_shop_recommend) :
        CommonAdapter<GetShopHomeViewBean.RecommendGoods>(mContext, itemList, layoutId) {
    // 点击弹窗取消按钮回调
    interface OnItemClickListener {
        fun onClick(item:GetShopHomeViewBean.RecommendGoods)
    }


    var onItemClickListener:OnItemClickListener?=null

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data:GetShopHomeViewBean.RecommendGoods, position: Int) {
        holder.setText(R.id.tvTitle,data.shopGoodsTitle)
        val ivImage=holder.getView<ImageView>(R.id.ivGoodsImage)
        ivImage.ext_loadConnersImageWithDomain(data.goodsCompressPriUrl)
        holder.setText(R.id.tvUnit,AppBusManager.getAmountUnit())
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

        holder.setOnItemClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                if(onItemClickListener!=null){
                    onItemClickListener!!.onClick(data)
                }
            }
        })
    }
}
