package com.epro.mall.ui.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.GeolocationBean
import com.epro.mall.utils.MallMapUtils
import com.mike.baselib.utils.ext_Highlight
import com.mike.baselib.utils.ext_formatDiscount
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import java.util.ArrayList

class SearchGeolocationListAdapter(mContext: Context, list: ArrayList<GeolocationBean>, layoutId: Int = R.layout.item_search_geolocation) :
        CommonAdapter<GeolocationBean>(mContext, list, layoutId) {

    interface OnItemClickListener {
        fun onClick(item: GeolocationBean)
    }

    var keyword=""
    var latitude:Double=0.toDouble()
    var longtitude:Double=0.toDouble()
    var onItemClickListener: OnItemClickListener? = null

    override fun bindData(holder: ViewHolder, data: GeolocationBean, position: Int) {

        logTools.t("YB").d("bindData: $data")
        val tvAddress = holder.getView<TextView>(R.id.tvAddress)
        val tvSnippet = holder.getView<TextView>(R.id.tvSnippet)
//        if (0 == position) {
//            tvAddress.setTextColor(mContext.resources.getColor(R.color.mainColor))
//        } else {
//            tvAddress.setTextColor(mContext.resources.getColor(R.color.mainTextColor))
//        }
        tvAddress.ext_Highlight(data.title,keyword)
        tvSnippet.ext_Highlight(data.snippet,keyword)
//        holder.setText(R.id.tvAddress, data.title)
//        holder.setText(R.id.tvSnippet, data.snippet)
        val distance=MallMapUtils.calculateLineDistance(latitude,longtitude,
                data.latitude.toDouble(),data.longitude.toDouble()).toInt()
        if(distance>1000){
            holder.setText(R.id.tvDistance,(distance/1000F).ext_formatDiscount().toString()+"km")
        }else{
            holder.setText(R.id.tvDistance,distance.toString()+"m")
        }

        holder.setOnItemClickListener(View.OnClickListener {
            if (onItemClickListener != null) {
                onItemClickListener!!.onClick(data)
            }
        })
    }
}