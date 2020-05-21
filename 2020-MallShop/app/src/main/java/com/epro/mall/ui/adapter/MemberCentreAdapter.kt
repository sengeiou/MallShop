package com.epro.mall.ui.adapter

import android.content.Context
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.MemberCentreBean
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter

class MemberCentreAdapter (mContext: Context, list: ArrayList<MemberCentreBean>, layoutId: Int = R.layout.member_centre_item) :
        CommonAdapter<MemberCentreBean>(mContext, list, layoutId) {

    init {
        for (index in 0..5){
            list.add(MemberCentreBean(index,"ok","会员"+index))
        }
    }

    override fun bindData(holder: ViewHolder, data: MemberCentreBean, position: Int) {
        holder.setText(R.id.memberId,data.result)
    }

}