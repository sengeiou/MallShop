package com.epro.mall.ui.adapter


import android.app.Dialog
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.CartGoods
import com.epro.mall.mvp.model.bean.ShopCart
import com.epro.mall.ui.view.CommonDialog
import com.mike.baselib.utils.*
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import com.mike.baselib.view.recyclerview.adapter.OnItemLongClickListener

/**
 * Created by xuhao on 2017/11/29.
 */

class ShopCartAdapter(mContext: Context, shopCarts: ArrayList<ShopCart>, layoutId: Int = R.layout.item_shopcart) :
        CommonAdapter<ShopCart>(mContext, shopCarts, layoutId) {
    // 点击弹窗取消按钮回调
    interface OnItemClickListener {
        fun onClick(data: ShopCart)
    }

    interface OnShopCartListEmptyListener {
        fun onShopCartListEmpty()
    }

    interface OnItemNumChangeListener {
        fun onItemNumberChange(cartGoods: CartGoods, position: Int)
    }

    interface OnItemDeleteListener {
        fun onItemDelete(cartGoods: CartGoods, position: Int)

    }

    interface OnItemConfirmListener {
        fun onItemConfirm(shopCart: ShopCart, position: Int)

    }

    interface OnItemSelectedListener {
        fun onItemSelected(shopCart: ShopCart, position: Int)
    }

    interface OnShopSelectedListener {
        fun onShopSelected(shopCart: ShopCart, position: Int)
    }

    var onItemClickListener: OnItemClickListener? = null
    var onShopCartListEmptyListener: OnShopCartListEmptyListener? = null
    var onItemNumChangeListener: OnItemNumChangeListener? = null
    var onItemDeleteListener: OnItemDeleteListener? = null
    var onItemConfirmListener: OnItemConfirmListener? = null
    var onItemSelectedListener: OnItemSelectedListener? = null
    var onShopSelectedListener: OnShopSelectedListener? = null


    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: ShopCart, position: Int) {

        val tvUnValidTitle = holder.getView<TextView>(R.id.tvUnValidTitle)
        val llValidTitle = holder.getView<LinearLayout>(R.id.llValidTitle)
        if (data.isValid == 0) {  //失效商品
            tvUnValidTitle.visibility = View.VISIBLE
            llValidTitle.visibility = View.GONE
        } else {
            tvUnValidTitle.visibility = View.GONE
            llValidTitle.visibility = View.VISIBLE
        }
        val cbSelect = holder.getView<CheckBox>(R.id.cbSelect)
        holder.setText(R.id.tvShopName, data.shopName)
        cbSelect.isChecked = data.judgeValue
        val rvShopCarts = holder.getView<RecyclerView>(R.id.rvShopCarts)
        rvShopCarts.layoutManager = LinearLayoutManager(mContext)
        val adapter = CartGoodsAdapter(mContext, data.products)
        rvShopCarts.adapter = adapter
        val cbAllSelect = holder.getView<CheckBox>(R.id.cbAllSelect)
        cbAllSelect.isChecked = data.judgeValue
        val btnCheckout = holder.getView<Button>(R.id.btnCheckout)

        btnCheckout.ext_doubleClick(View.OnClickListener {
            onItemConfirmListener?.onItemConfirm(data, position)
        })
        cbAllSelect.setOnClickListener(View.OnClickListener {
            for (goods in adapter?.mData!!) {
                goods.judgeValue = cbAllSelect.isChecked
                ext_setAllValue(adapter.mData, cbAllSelect.isChecked)
            }
            cbSelect.isChecked = cbAllSelect.isChecked
            data.judgeValue = cbAllSelect.isChecked
            adapter.notifyDataSetChanged()
            //updateCartUI(holder, adapter)
        })


        btnCheckout.ext_doubleClick(View.OnClickListener {
            onItemConfirmListener?.onItemConfirm(data, position)
        })

        adapter.onItemSelectListener = object : CartGoodsAdapter.OnItemSelectListener {
            override fun onItemSelect(cartGoods: CartGoods) {
                cbSelect.isChecked = ext_isAllTrue(adapter.mData)
                data.judgeValue = cbSelect.isChecked
                cbAllSelect.isChecked = cbSelect.isChecked
               // updateCartUI(holder, adapter)
                onItemSelectedListener?.onItemSelected(data, position)
            }
        }

        adapter.onItemNumChangeListener = object : CartGoodsAdapter.OnItemNumChangeListener {
            override fun onItemNumberChange(cartGoods: CartGoods, isMinus: Boolean) {
                onItemNumChangeListener?.onItemNumberChange(cartGoods, position)
            }
        }
        adapter.onItemNumZeroListener = object : CartGoodsAdapter.OnItemNumZeroListener {
            override fun onItemNumberZero(cartGoods: CartGoods) {
                showDeleteDialog(cartGoods, position)
            }
        }

        adapter.setOnItemLongClickListener(object : OnItemLongClickListener<CartGoods> {
            override fun onItemLongClick(m: CartGoods, childPosition: Int): Boolean {
                showDeleteDialog(m, position)
                return true
            }
        })

        cbSelect.setOnClickListener(View.OnClickListener {
            data.judgeValue = cbSelect.isChecked
            for (cartGoods in adapter.mData) {
                cartGoods.judgeValue = data.judgeValue
            }
            adapter.notifyDataSetChanged()
            cbAllSelect.isChecked = cbSelect.isChecked
           // updateCartUI(holder, adapter)
            onShopSelectedListener?.onShopSelected(data, position)
        })

        holder.getView<TextView>(R.id.tvShopName).setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onClick(data)
                }
            }
        })
        //updateCartUI(holder, adapter)

        val params=holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
        if(position==mData.size-1){
            params.bottomMargin=DisplayManager.dip2px(12F)!!
        }else{
            params.bottomMargin=0
        }
        holder.itemView.layoutParams=params
    }


    /**
     * 结算个数,金额的更新
     */
//    private fun updateCartUI(holder: ViewHolder, adapter: CartGoodsAdapter) {
//        val tvSelectNum = holder.getView<TextView>(R.id.tvSelectNum)
//        val tvTotal = holder.getView<TextView>(R.id.tvTotal)
//        var count = 0
//        var amount = 0.toDouble()
//        for (goods in adapter.mData) {
//            if (goods.judge()) {
//                amount += goods.salePrice.toDouble() * goods.productCount
//                count += goods.productCount
//            }
//        }
//        tvSelectNum.text = "已选($count)"
//        tvTotal.text = amount.ext_formatAmountWithUnit()
//        if (mData.isEmpty()) {
//            onShopCartListEmptyListener?.onShopCartListEmpty()
//        }
//    }

    private fun showDeleteDialog(cartGoods: CartGoods, position: Int) {
        CommonDialog.Builder(mContext)
                .setContent(mContext.getString(R.string.confirm_delete_product))
                .setOnConfirmListener(object : CommonDialog.OnConfirmListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                        onItemDeleteListener!!.onItemDelete(cartGoods, position)
                    }
                })
                .create()
                .show()
    }

    fun updateDeleteUI(cartGoods: CartGoods, position: Int) {
        //删除购物车商品
        val shopCart = mData!![position]
        shopCart.products.remove(cartGoods)
        shopCart.judgeValue = shopCart.products.size == ext_sizeOfTrue(shopCart.products)
        if (shopCart.products.size == 0) {
            mData!!.removeAt(position)
            notifyDataSetChanged()
        } else {
            notifyItemChanged(position)
        }
    }
}
