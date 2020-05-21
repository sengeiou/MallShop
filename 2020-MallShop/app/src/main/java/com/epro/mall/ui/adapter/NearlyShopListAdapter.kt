package com.epro.mall.ui.adapter


import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.Location
import com.epro.mall.mvp.model.bean.NearlyShop
import com.mike.baselib.utils.DisplayManager
import com.mike.baselib.utils.ext_Highlight
import com.mike.baselib.utils.ext_allScientificNotation_to_formatDouble
import org.jetbrains.anko.textColor

/**
 * desc: 分类的 Adapter
 */

class NearlyShopListAdapter(mContext: Context, list: ArrayList<NearlyShop>, layoutId: Int = R.layout.item_nearly_shoplist) :
        CommonAdapter<NearlyShop>(mContext, list, layoutId) {

    interface OnItemClickListener {
        fun onClick(item: NearlyShop)
    }
    interface OnLocationClickListener {
        fun onClick(item: NearlyShop)
    }

    var onItemClickListener: OnItemClickListener? = null
    var onLocationClickListener: OnLocationClickListener? = null
    var location: Location? = null
    var keyword:String?=null

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: NearlyShop, position: Int) {
        val tvShopName=holder.getView<TextView>(R.id.tvShopName)
        tvShopName.textColor=mContext.resources.getColor(if(data.judge()) R.color.mainColor else R.color.mainTextColor)
        holder.setViewVisibility(R.id.tvSelectText, if(data.judge()) View.VISIBLE else View.GONE)
        tvShopName.text=data.shopName

        val tvAddress=holder.getView<TextView>(R.id.tvAddress)
        holder.setText(R.id.tvAddress, data.address)
        if(!TextUtils.isEmpty(keyword)){
            tvShopName.ext_Highlight(data.shopName,keyword!!)
            tvAddress.ext_Highlight(data.address,keyword!!)
        }

        if(data.distance>=1000){
            holder.setText(R.id.tvDistance,(data.distance.toDouble()/1000).toString().ext_allScientificNotation_to_formatDouble(1)+"km")
        }else{
            holder.setText(R.id.tvDistance, "" + data.distance + "m")
        }
        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                    onItemClickListener?.onClick(data)
            }
        })
        val params=holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
        if(position==0){
            params.topMargin=DisplayManager.dip2px(12F)!!
        }else{
            params.topMargin=0
        }
        holder.itemView.layoutParams=params
        if(data.distance>200){
            holder.itemView.alpha=0.5F
            holder.itemView.isEnabled=false
        }else{
            holder.itemView.alpha=1F
            holder.itemView.isEnabled=true
        }
        if(location==null||TextUtils.isEmpty(location!!.cityName)){
            holder.setImageResource(R.id.ivLocation,R.mipmap.home_icon_location_big)
            holder.setViewVisibility(R.id.tvDistance,View.GONE)
        }else{
            holder.setImageResource(R.id.ivLocation,R.mipmap.home_icon_location_small)
            holder.setViewVisibility(R.id.tvDistance,View.VISIBLE)
        }

        holder.getView<View>(R.id.llRight).setOnClickListener(View.OnClickListener {
            onLocationClickListener?.onClick(data)
        })
    }
}
