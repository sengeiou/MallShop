package com.epro.mall.ui.adapter


import android.content.Context
import android.view.View
import android.widget.CheckBox
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.SpecValue
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import org.jetbrains.anko.textColor

/**
 * Created by xuhao on 2017/11/29.
 */

class SpecValueAdapter(mContext: Context, specValues: ArrayList<SpecValue>, layoutId: Int= R.layout.item_spec_value) :
        CommonAdapter<SpecValue>(mContext,specValues, layoutId) {
    // 点击弹窗取消按钮回调
    interface OnItemClickListener {
        fun onClick(data: SpecValue)

    }
    interface OnItemSelectListener {
        fun onItemSelect(specValue: SpecValue)

    }
    var onItemClickListener:OnItemClickListener?=null
    var onItemSelectListener:OnItemSelectListener?=null

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: SpecValue, position: Int) {
        val cbSpecValue=holder.getView<CheckBox>(R.id.cbSpecValue)
        cbSpecValue.text=data.specificationsValue
        cbSpecValue.isEnabled=!data.isLock
        cbSpecValue.isChecked=data.judgeValue
        if(data.isLock){
            cbSpecValue.textColor=mContext.resources.getColor( R.color.gray_cccccc)
        }else{
            cbSpecValue.textColor=mContext.resources.getColor(if(data.judge()) R.color.mainColor else R.color.mainTextColor)
        }
        //cbSpecValue.alpha=if(data.isLock) 0.2.toFloat() else 1.toFloat()
        cbSpecValue.setOnClickListener(View.OnClickListener {
            data.judgeValue=cbSpecValue.isChecked
            if(data.judgeValue){
                for(specValue in mData){
                   specValue.judgeValue= specValue.id==data.id
                }
            }
            notifyDataSetChanged()
            if(onItemSelectListener!=null){
                onItemSelectListener!!.onItemSelect(data)
            }
        })

//        holder.setOnItemClickListener(object :View.OnClickListener{
//            override fun onClick(p0: View?) {
//                if(onItemClickListener!=null){
//                    onItemClickListener!!.onClick(data)
//                }
//            }
//        })

    }
}
