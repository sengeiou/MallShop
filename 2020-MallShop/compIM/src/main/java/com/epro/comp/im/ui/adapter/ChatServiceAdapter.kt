package com.epro.comp.im.ui.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.epro.comp.im.R
import com.epro.comp.im.mvp.model.bean.CustomerService
import com.epro.comp.im.utils.IMBusManager
import com.mike.baselib.utils.AppConfig
import com.mike.baselib.utils.AppConfig.Companion.getBaseurl
import com.mike.baselib.utils.ImageLoader
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter

class ChatServiceAdapter (mContext: Context, list: ArrayList<CustomerService>, layoutId: Int = R.layout.customer_service_item_list) :
        CommonAdapter<CustomerService>(mContext, list, layoutId) {

    constructor(context: Context):this(context,ArrayList())
    interface OnItemClickListener {
        fun onClick(item: CustomerService, position: Int)
    }

    var onItemClickListener: OnItemClickListener? = null

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: CustomerService, position: Int) {
        holder.setText(R.id.tvService,data.name)
        var imageView = holder.getView<ImageView>(R.id.serviceImg)
        var basePath = getBaseurl() + "/image/"
        ImageLoader.loadCircleImage(mContext,basePath+IMBusManager.getCustomerService(data.account)!!.avatar,R.mipmap.ic_head_default_left,R.mipmap.ic_head_default_left,imageView)
        holder.setOnItemClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                if (onItemClickListener!=null){
                    onItemClickListener!!.onClick(data,position)
                }
            }
        })

    }
}