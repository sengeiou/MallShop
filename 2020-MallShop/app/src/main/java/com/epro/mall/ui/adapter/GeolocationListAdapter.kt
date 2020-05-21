package com.epro.mall.ui.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.GeolocationBean
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import org.jetbrains.anko.backgroundDrawable
import java.util.ArrayList

class GeolocationListAdapter(mContext: Context, list: ArrayList<GeolocationBean>, layoutId: Int = R.layout.item_geolocation) :
        CommonAdapter<GeolocationBean>(mContext, list, layoutId) {

    interface OnItemClickListener {
        fun onClick(item: GeolocationBean)
    }

    var onItemClickListener: OnItemClickListener? = null

    override fun bindData(holder: ViewHolder, data: GeolocationBean, position: Int) {
        logTools.t("YB").d("bindData: $data")
        val tvAddress = holder.getView<TextView>(R.id.tvAddress)
        val ivMark = holder.getView<ImageView>(R.id.ivMark)
        if (0 == position) {
            ivMark.setImageResource(R.mipmap.group_1)
            tvAddress.setTextColor(mContext.resources.getColor(R.color.mainColor))
        } else {
            ivMark.setImageResource(R.mipmap.group_2)
            tvAddress.setTextColor(mContext.resources.getColor(R.color.mainTextColor))
        }
        holder.setText(R.id.tvAddress, data.title)
        holder.setText(R.id.tvSnippet, data.snippet)

        holder.setOnItemClickListener(View.OnClickListener {
            if (onItemClickListener != null) {
                onItemClickListener!!.onClick(data)
            }
        })
    }
}