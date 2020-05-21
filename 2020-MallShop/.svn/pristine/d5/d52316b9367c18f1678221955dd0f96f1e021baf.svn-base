package com.epro.mall.ui.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.ScanCodeOrderListBean
import com.epro.mall.mvp.model.bean.ScanCodeOrderListOneBean
import com.epro.mall.ui.activity.ScanCodeOrderListDetailActivity
import com.mike.baselib.utils.ext_formatAmountWithUnit
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter

class ScanCodeOrderAdapter(mContext: Context, itemList: ArrayList<ScanCodeOrderListOneBean>, layoutId: Int= R.layout.scan_code_order_list) :
        CommonAdapter<ScanCodeOrderListOneBean>(mContext, itemList, layoutId) {

    interface OnItemClickListener{
        fun onClick(itemList: ScanCodeOrderListOneBean)
    }

    var onItemClickListener:OnItemClickListener?=null


    override fun bindData(holder: ViewHolder, data: ScanCodeOrderListOneBean, position: Int) {
        holder.setText(R.id.shopName,data.shopName)
        holder.setText(R.id.shopCreatedTime,data.createTime)
        holder.setText(R.id.shopTotalCount,mContext.getString(R.string.scan_code_shop_count_start)+data.productCount+mContext.getString(R.string.scan_code_shop_count_end))
        holder.setText(R.id.allShopMoney,data.orderActualAmount.ext_formatAmountWithUnit())
        holder.setText(R.id.orderStatus,getOrderStatus(data.orderStatus))
        val rvListView =   holder.getView<RecyclerView>(R.id.rvListView)
        rvListView.layoutManager = LinearLayoutManager(mContext)
        rvListView.adapter = ScanCodeOrderItemAdapter(mContext,data.products)
        ( rvListView.adapter as ScanCodeOrderItemAdapter).onItemClickListener = object :ScanCodeOrderItemAdapter.OnItemClickListener{
            override fun onClick(itemList: ScanCodeOrderListOneBean.ScanCodeProductsBean) {
                ScanCodeOrderListDetailActivity.launchWithStr(mContext,data.orderSn)
            }
        }

        holder.setOnItemClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                if (onItemClickListener != null){
                    onItemClickListener!!.onClick(data)
                }
                ScanCodeOrderListDetailActivity.launchWithStr(mContext,data.orderSn)
            }
        })
    }

    private fun getOrderStatus(status: String): String {
        if (1 == status.toInt()){
            return  mContext.getString(R.string.order_list_title_2)
        }else if (2==status.toInt()|| 7==status.toInt()){
            return  mContext.getString(R.string.order_list_title_5)
        }else if (3==status.toInt()|| 4==status.toInt()|| 5==status.toInt()){
            return  mContext.getString(R.string.order_list_title_3)
        }else if (6==status.toInt()){
            return  mContext.getString(R.string.order_list_title_4)
        }
        return status
    }

}