package com.epro.mall.ui.adapter


import android.content.Context
import android.view.View
import android.widget.CheckBox
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.City
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import org.jetbrains.anko.textColor

/**
 * Created by xuhao on 2017/11/29.
 */

class HotCityAdapter(mContext: Context, citys: ArrayList<City>, layoutId: Int = R.layout.item_hot_city) :
        CommonAdapter<City>(mContext, citys, layoutId) {
    // 点击弹窗取消按钮回调
    interface OnItemClickListener {
        fun onClick(data: City)

    }

    interface OnItemSelectListener {
        fun onItemSelect(city: City)

    }

    var onItemClickListener: OnItemClickListener? = null
    var onItemSelectListener: OnItemSelectListener? = null

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: City, position: Int) {
        val cbHotCity = holder.getView<CheckBox>(R.id.cbHotCity)
        cbHotCity.text = data.city_name
        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onClick(data)
                }
            }
        })

    }
}
