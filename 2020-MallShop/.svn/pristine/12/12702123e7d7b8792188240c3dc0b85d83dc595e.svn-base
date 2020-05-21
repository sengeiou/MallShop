package com.epro.mall.ui.adapter

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.LanguageSelectBean
import com.mike.baselib.utils.SPUtils
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter

class LanguageAdapter(mContext:Context,list:ArrayList<LanguageSelectBean>,layoutId:Int = R.layout.language_select_item):CommonAdapter<LanguageSelectBean>(mContext,list,layoutId) {

    var selectPosition:Int? =null
    var onItemClickListener:OnItemClickListener?=null
    val STATUS_CLICK:String = "status_click"
    var  prefs: SharedPreferences =PreferenceManager.getDefaultSharedPreferences(mContext)
    //当列表数据发生变化时,用此方法来更新列表
    fun updateListView(select: Int) {
        this.selectPosition = select
        notifyDataSetChanged()
    }

    interface OnItemClickListener{
        fun onClick(item:LanguageSelectBean)
    }

    override fun bindData(holder: ViewHolder, data: LanguageSelectBean, position: Int) {
          holder.setText(R.id.tvLang,data.title)
          var click =prefs.getInt(STATUS_CLICK,1)
          if (2 == position){
              holder.getView<View>(R.id.lineView).visibility = View.GONE
          }else{
               holder.getView<View>(R.id.lineView).visibility = View.VISIBLE
          }
          if (position==click){
            holder.getView<ImageView>(R.id.ivIcon).visibility = View.VISIBLE
          }
          if (selectPosition == position){
             prefs.edit().putInt(STATUS_CLICK,position).commit()
          }
          holder.setOnItemClickListener(object : View.OnClickListener {
              override fun onClick(view: View?) {
                  if (onItemClickListener != null) {
                      onItemClickListener!!.onClick(data)
                  }
              }
          })
    }
}