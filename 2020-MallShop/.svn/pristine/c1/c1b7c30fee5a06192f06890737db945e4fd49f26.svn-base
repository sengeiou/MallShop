package com.epro.mall.ui.adapter


import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.GoodsCategory
import com.epro.mall.mvp.model.bean.City
import com.epro.mall.mvp.model.bean.HomeShop
import com.epro.mall.mvp.model.cn.CNPinyin
import com.epro.mall.ui.adapter.stickyheader.StickyHeaderAdapter
import com.mike.baselib.view.recyclerview.MultipleType
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter


/**
 * Created by xuhao on 2017/11/29.
 * desc: 分类的 Adapter
 */

class CityAdapter(mContext: Context, cnpCityList: ArrayList<CNPinyin<City>>, layoutId: MultipleType<CNPinyin<City>>) :
        CommonAdapter<CNPinyin<City>>(mContext, cnpCityList, layoutId), StickyHeaderAdapter<CityAdapter.HeaderHolder> {

    override fun getHeaderId(childAdapterPosition: Int): Long {
        return mData[childAdapterPosition].firstChar.toLong()
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup?): HeaderHolder {
        return HeaderHolder(LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_city_header, parent, false))
    }

    override fun onBindHeaderViewHolder(holder: HeaderHolder?, childAdapterPosition: Int) {
        holder?.tvTag!!.text = mData[childAdapterPosition].firstChar.toString()
    }

    // 点击弹窗取消按钮回调
    interface OnItemClickListener {
        fun onItemClick(city: City, position: Int)
    }

    var onItemClickListener: OnItemClickListener? = null

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: CNPinyin<City>, position: Int) {
        if (position == 0) {
            val rvHotCitys = holder.getView<RecyclerView>(R.id.rvHotCities)
            rvHotCitys.layoutManager = GridLayoutManager(mContext, 4)
            val list = ArrayList<City>()
            list.add( City("", "", "440300", "深圳市"))
            list.add( City("", "", "320100", "南京市"))
            rvHotCitys.adapter = HotCityAdapter(mContext, list)
            (rvHotCitys.adapter as HotCityAdapter).onItemClickListener = object : HotCityAdapter.OnItemClickListener {
                override fun onClick(data: City) {
                    onItemClickListener?.onItemClick(data, position)
                }
            }
        } else {
            holder.setText(R.id.tvCity, data.data.city_name)
        }
        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                onItemClickListener?.onItemClick(data.data, position)
            }
        })
    }

    class HeaderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTag: TextView = itemView.findViewById<View>(R.id.tvTag) as TextView
    }
}
