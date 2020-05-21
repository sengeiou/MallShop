package com.epro.mall.ui.adapter


import android.content.Context
import android.view.View
import android.widget.CheckBox
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.Item
import com.epro.mall.utils.MallBusManager
import com.epro.mall.utils.MallConst

/**
 * desc: 分类的 Adapter
 */

class PayModeAdapter(mContext: Context, itemList: ArrayList<Item>, pay_mode: Int, layoutId: Int = R.layout.item_paymode) :
        CommonAdapter<Item>(mContext, itemList, layoutId) {
    // 点击弹窗取消按钮回调
    interface OnItemClickListener {
        fun onClick(item: Item)
    }
    var pay_mode = MallConst.PAY_MODE_ZFB
    init {
        this.pay_mode = pay_mode
        itemList.add(Item(MallConst.PAY_MODE_ZFB, MallBusManager.getPayModeText(MallConst.PAY_MODE_ZFB), MallConst.PAY_MODE_ZFB == pay_mode))
        itemList.add(Item(MallConst.PAY_MODE_WX, MallBusManager.getPayModeText(MallConst.PAY_MODE_WX), MallConst.PAY_MODE_WX == pay_mode))
    }

    var onItemClickListener: OnItemClickListener? = null

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: Item, position: Int) {
        holder.setText(R.id.tvName, data.content)
        val cbSelect = holder.getView<CheckBox>(R.id.cbSelect)
        cbSelect.isChecked = data.judgeValue
        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                data.judgeValue = true
                if (onItemClickListener != null) {
                    onItemClickListener!!.onClick(data)
                }
            }
        })
    }
}
