package com.epro.mall.ui.adapter


import android.content.Context
import android.graphics.Paint
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.HomeShop
import com.epro.mall.ui.activity.GoodsDetailActivity
import com.epro.mall.utils.MallBusManager
import com.mike.baselib.utils.*
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter

/**
 * desc: 分类的 Adapter
 */

class ShopImageAdapter(mContext: Context, itemList: ArrayList<HomeShop.RecommendGoods>, layoutId: Int = R.layout.item_shopimage) :
        CommonAdapter<HomeShop.RecommendGoods>(mContext, itemList, layoutId) {
    // 点击弹窗取消按钮回调
    interface OnItemClickListener {
        fun onClick(item: HomeShop.RecommendGoods)
    }


    var onItemClickListener: OnItemClickListener? = null

    /**
     * 绑定数据
     */
    var imageViewWidth = ((DisplayManager.getScreenWidth()!! - DisplayManager.dip2px(60f)!!) / 3F).toInt()

    override fun bindData(holder: ViewHolder, data: HomeShop.RecommendGoods, position: Int) {

        holder.setText(R.id.tvDesc, data.shopGoodsTitle)
        val ivImage = holder.getView<ImageView>(R.id.ivImage)
        ivImage.ext_loadConnersImageWithDomain(data.goodsPicUrl, 4F, R.mipmap.bg_empty)
//        val vto = ivImage.viewTreeObserver
//        vto.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
//            override fun onGlobalLayout() {
//               ivImage.viewTreeObserver.removeOnGlobalLayoutListener(this)
//            }
//        })

        var params = ivImage.layoutParams
        params.width = imageViewWidth
        params.height = (params.width * (100F / 100)).toInt()
        ivImage.layoutParams = params

        val margin = DisplayManager.dip2px(6F)!!
        val columns = 3
        val ratio = (columns + 1) / columns.toFloat() - 1
        params = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
        for (i in 0 until columns) {
            if ((position - i) % columns == 0) {
                params.leftMargin = (margin * (i * ratio)).toInt()
                params.rightMargin = (margin * (1 - (1 + i) * ratio)).toInt()
            }
        }
        holder.itemView.layoutParams = params
        holder.setText(R.id.tvUnit, AppBusManager.getAmountUnit())
        holder.setText(R.id.tvPrice, MallBusManager.getMiniPrice(data.onlineSalesPrice))
        holder.setViewVisibility(R.id.tvQi,if(MallBusManager.isIntervalPrice(data.onlineSalesPrice)) View.VISIBLE else View.GONE)
        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onClick(data)
                }
                GoodsDetailActivity.launchWithGoodsId(mContext, data.goodsId)
            }
        })

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
