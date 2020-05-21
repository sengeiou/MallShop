package com.epro.mall.ui.adapter

import android.content.Context
import android.view.View
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.ScanCodeOrderItemBean
import com.epro.mall.mvp.model.bean.ScanCodeOrderListOneBean
import com.epro.mall.ui.activity.ScanCodeOrderListDetailActivity
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter

class ScanCodeOrderItemAdapter(mContext: Context, itemList: ArrayList<ScanCodeOrderListOneBean.ScanCodeProductsBean>, layoutId: Int= R.layout.scan_code_list_item) :
        CommonAdapter<ScanCodeOrderListOneBean.ScanCodeProductsBean>(mContext, itemList, layoutId) {

    interface OnItemClickListener{
        fun onClick(itemList: ScanCodeOrderListOneBean.ScanCodeProductsBean)
    }

    var onItemClickListener:OnItemClickListener?=null

    override fun bindData(holder: ViewHolder, data: ScanCodeOrderListOneBean.ScanCodeProductsBean, position: Int) {
        holder.setText(R.id.commodityName,data.goodsName)
        holder.setText(R.id.shopCount,"x"+data.productCount)
        holder.setOnItemClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                if (onItemClickListener != null){
                    onItemClickListener!!.onClick(data)
                }
            }
        })
    }
}