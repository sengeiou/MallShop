package com.epro.mall.ui.adapter

import android.content.Context
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.UpdateContent
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter

class UpdateContentAdapter (mContext: Context, list: ArrayList<String>, layoutId: Int = R.layout.item_update_content) :
        CommonAdapter<String>(mContext, list, layoutId){


    override fun bindData(holder: ViewHolder, data: String, position: Int) {
        //logTools.t("YB").d("data: "+data.content)
        holder.setText(R.id.tvContent,""+data)
    }

}