package com.epro.mall.ui.adapter


import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.ScanBuyCartGoods
import com.epro.mall.ui.view.CommonDialog
import com.epro.mall.utils.MallBusManager
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.ext_formatAmount
import com.mike.baselib.utils.ext_setTextWithHorizontalLine
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import com.mike.baselib.utils.toast

/**
 * Created by xuhao on 2017/11/29.
 */

class ScanBuyCartGoodsAdapter(mContext: Context, cartGoodss: ArrayList<ScanBuyCartGoods>, layoutId: Int = R.layout.item_scanbuy_cartgoods) :
        CommonAdapter<ScanBuyCartGoods>(mContext, cartGoodss, layoutId, false) {
    // 点击弹窗取消按钮回调
    interface OnItemClickListener {
        fun onClick(data: ScanBuyCartGoods)

    }

    interface OnItemDeleteListener {
        fun onItemDelete( position: Int)

    }
    interface OnBuyNumChangeListener {
        fun onBuyNumChange( position: Int)

    }

    var onItemClickListener: OnItemClickListener? = null
    var onItemDeleteListener: OnItemDeleteListener? = null
    var onBuyNumChangeListner:OnBuyNumChangeListener?=null

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: ScanBuyCartGoods, position: Int) {

        holder.setText(R.id.tvDesc, data.goodsName)
        holder.setText(R.id.tvPrice, data.retailPrice.ext_formatAmount())
        holder.setText(R.id.tvUnit, AppBusManager.getAmountUnit())
        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onClick(data)
                }
                //GoodsDetailActivity.launchWithGoodsId(mContext, data.goodsId)
            }
        })

        val ivMinus = holder.getView<ImageView>(R.id.tvMinus)
        val ivPlus = holder.getView<ImageView>(R.id.tvPlus)
        val tvNum = holder.getView<TextView>(R.id.tvNum)

        tvNum.setText(data.buyNum.toString())
        ivMinus.setOnClickListener(View.OnClickListener {
            var num = Integer.valueOf(tvNum.text.toString())
            if (num == 1) {
                mContext.toast(mContext.getString(R.string.not_be_less_anymore))
                return@OnClickListener
            }
            num--
            data.buyNum = num
            tvNum.text = "" + num
            onBuyNumChangeListner?.onBuyNumChange(position)
        })
        ivPlus.setOnClickListener(View.OnClickListener {
            var num = Integer.valueOf(tvNum.text.toString())
            num++
            tvNum.text = "" + num
            data.buyNum = num
            onBuyNumChangeListner?.onBuyNumChange(position)
        })
        tvNum.setOnClickListener(View.OnClickListener {

        })

        holder.setOnItemLongClickListener(View.OnLongClickListener {
            showDeleteDialog(position)
            return@OnLongClickListener true
        })

        //如果商品有活动
        val llActivity=holder.getView<View>(R.id.llActivity)
        val tvOriginalPrice=holder.getView<TextView>(R.id.tvOriginalPrice)
        val a= MallBusManager.getProductActivity(data)
        if(a==null){
            llActivity.visibility=View.GONE
        }else{
            llActivity.visibility=View.VISIBLE
            holder.setText(R.id.tvPrice,a.discountPrice.ext_formatAmount())
            tvOriginalPrice.ext_setTextWithHorizontalLine(AppBusManager.getAmountUnit()+" "+data.retailPrice.ext_formatAmount())
        }
    }

    private fun showDeleteDialog(position: Int) {
        CommonDialog.Builder(mContext)
                .setContent(mContext.getString(R.string.confirm_detele_this_product))
                .setOnConfirmListener(object : CommonDialog.OnConfirmListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                        mData.removeAt(position)
                        notifyDataSetChanged()
                        onItemDeleteListener?.onItemDelete(position)
                    }
                })
                .create()
                .show()
    }

}
