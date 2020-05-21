package com.epro.mall.ui.adapter


import android.content.Context
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.CartGoods
import com.epro.mall.ui.activity.GoodsDetailActivity
import com.epro.mall.utils.MallBusManager
import com.mike.baselib.utils.*
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter

/**
 * Created by xuhao on 2017/11/29.
 */

class CartGoodsAdapter(mContext: Context, cartGoodss: ArrayList<CartGoods>, layoutId: Int= R.layout.item_cartgoods) :
        CommonAdapter<CartGoods>(mContext,cartGoodss, layoutId) {
    // 点击弹窗取消按钮回调
    interface OnItemClickListener {
        fun onClick(data: CartGoods)

    }
    interface OnItemSelectListener {
        fun onItemSelect(cartGoods: CartGoods)

    }
    interface OnItemNumChangeListener {
        fun onItemNumberChange(cartGoods: CartGoods,isMinus:Boolean)

    }
    interface OnItemNumZeroListener {
        fun onItemNumberZero(cartGoods: CartGoods)

    }

    var onItemClickListener:OnItemClickListener?=null
    var onItemSelectListener:OnItemSelectListener?=null
    var onItemNumChangeListener:OnItemNumChangeListener?=null
    var onItemNumZeroListener:OnItemNumZeroListener?=null


    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: CartGoods, position: Int) {
        val cbSelect=holder.getView<CheckBox>(R.id.cbSelect)
        val tvUnValid=holder.getView<TextView>(R.id.tvUnValid)
        val llRight=holder.getView<LinearLayout>(R.id.llRight)
        if(data.isValid==0){
            cbSelect.visibility=View.GONE
            tvUnValid.visibility=View.VISIBLE
            holder.setViewVisibility(R.id.llChangeNum,View.GONE)
            llRight.alpha=0.2f
        }else{
            cbSelect.visibility=View.VISIBLE
            tvUnValid.visibility=View.GONE
            holder.setViewVisibility(R.id.llChangeNum,View.VISIBLE)
            llRight.alpha=1f
        }
        holder.setText(R.id.tvDesc,data.shoppingMallName.toString())
        holder.setText(R.id.tvSpec,data.goodsSpecifitionNameValue)
        holder.setText(R.id.tvUnit,AppBusManager.getAmountUnit())
        holder.setText(R.id.tvPrice,data.salePrice.ext_formatAmount())
        val ivImage=holder.getView<ImageView>(R.id.ivGoodsImage)
        ivImage.ext_loadConnersImageWithDomain(data.listPicUrl)
        cbSelect.isChecked=data.judgeValue

        holder.getView<LinearLayout>(R.id.llCheck).setOnClickListener(View.OnClickListener {
            cbSelect.toggle()
            data.judgeValue=cbSelect.isChecked
            if(onItemSelectListener!=null){
                onItemSelectListener!!.onItemSelect(data)
            }
        })

        holder.setOnItemClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                if(onItemClickListener!=null){
                    onItemClickListener!!.onClick(data)
                }
                if(data.isValid==0){
                    mContext.toast(mContext.getString(R.string.product_lapse))
                    return
                }
                GoodsDetailActivity.launchWithGoodsId(mContext,data.goodsId)
            }
        })

        val ivMinus=holder.getView<View>(R.id.tvMinus)
        val ivPlus=holder.getView<View>(R.id.tvPlus)
        val tvNum=holder.getView<TextView>(R.id.tvNum)

        tvNum.text = data.productCount.toString()
        //val cartGoods= Gson().fromJson<CartGoods>(Gson().toJson(data),CartGoods::class.java)
        ivMinus.setOnClickListener(View.OnClickListener {
            var num=Integer.valueOf(tvNum.text.toString())
            num--
            if(num==0){
                //mContext.toast("不能再少了")
                data.productCount=1
                onItemNumZeroListener?.onItemNumberZero(data)
                return@OnClickListener
            }
            data.productCount=num
            tvNum.text=""+num
            onItemNumChangeListener?.onItemNumberChange(data,true)
        })
        ivPlus.setOnClickListener(View.OnClickListener {
            var num=Integer.valueOf(tvNum.text.toString())
            num++
            tvNum.text=""+num
            data.productCount=num
            onItemNumChangeListener?.onItemNumberChange(data,false)
        })
        tvNum.setOnClickListener(View.OnClickListener {
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
            tvOriginalPrice.ext_setTextWithHorizontalLine(AppBusManager.getAmountUnit()+" "+data.salePrice.ext_formatAmount())
        }
    }
}
