package com.epro.mall.ui.adapter


import android.content.Context
import android.view.View
import android.widget.ImageView
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.Address
import com.epro.mall.mvp.model.bean.AddressListBean
import com.epro.mall.ui.activity.EditAddressActivity
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter


/**
 * desc: 分类的 Adapter
 */

class AddressListAdapter(mContext: Context, list: ArrayList<Address>, layoutId: Int = R.layout.item_select_address) :
        CommonAdapter<Address>(mContext, list, layoutId) {

    interface OnItemClickListener {
        fun onClick(item: Address)
    }

    interface OnLongClickListener {
        fun onLongClick(item: Address)
    }

    interface OnItemEditClickListener {
        fun onItemEditClick(item: Address)
    }

    var address: Address? = null
    var onItemClickListener: OnItemClickListener? = null
    var onItemLongClickListener: OnLongClickListener? = null
    var onItemEditClickListener: OnItemEditClickListener? = null

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: Address, position: Int) {
        holder.setText(R.id.tvPhone, data.receive + " " + data.mobile)
        holder.setText(R.id.tvAddress, data.city + data.area + data.location + "(" + data.address + ")")
        holder.setViewVisibility(R.id.tvDefault, if (data.isDefult == 0) View.GONE else View.VISIBLE)
        val imEdit = holder.getView<ImageView>(R.id.ivEdit)
        imEdit.setOnClickListener(View.OnClickListener {
            onItemEditClickListener?.onItemEditClick(data)
        })
        if (address != null) {
            holder.setImageResource(R.id.ivSelect, if (data.judge()) R.mipmap.icon_selected_choice else R.mipmap.icon_default_choice)
            holder.setViewVisibility(R.id.ivSelect, View.VISIBLE)
            if (data.isEnabled) {
                holder.itemView.alpha = 1F
                holder.itemView.isEnabled = true
            } else {
                holder.itemView.alpha = 0.5F
                holder.itemView.isEnabled = false
            }
        } else {
            holder.setViewVisibility(R.id.ivSelect, View.GONE)
        }
        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onClick(data)
                }
            }
        })
        holder.setOnItemLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(p0: View?): Boolean {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener!!.onLongClick(data)
                }
                return true
            }
        })
    }
}
