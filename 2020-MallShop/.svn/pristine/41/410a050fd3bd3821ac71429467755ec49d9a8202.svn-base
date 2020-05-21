package com.epro.mall.ui.adapter


import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.Address
import com.epro.mall.mvp.model.bean.ShopCart
import com.epro.mall.mvp.model.bean.ShopInfo
import com.epro.mall.utils.MallBusManager
import com.epro.mall.utils.MallConst
import com.mike.baselib.utils.*
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import java.util.*


/**
 * desc: 分类的 Adapter
 */

class OrderInfoAdapter(mContext: Context, list: ArrayList<ShopCart>, layoutId: Int = R.layout.item_orderinfo) :
        CommonAdapter<ShopCart>(mContext, list, layoutId) {

    interface OnItemClickListener {
        fun onClick(item: ShopCart)
    }

    interface OnAddressClickListener {
        fun onClick(item: ShopCart)
    }

    interface OnDistributionTypeChangeListener {
        fun onDistributionTypeChange(item: ShopCart)
    }

    var onItemClickListener: OnItemClickListener? = null
    var onAddressClickListener: OnAddressClickListener? = null
    var onDistributionTypeChangeListener: OnDistributionTypeChangeListener? = null

    var shopInfo: ShopInfo? = null
    var address: Address? = null

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: ShopCart, position: Int) {
        holder.setText(R.id.tvShopName, "" + data.shopName)
        initAddressUI(holder, data)
        val rgTakeMode = holder.getView<RadioGroup>(R.id.rgTakeMode)
        rgTakeMode.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
                when (p1) {
                    R.id.rbSelfTake -> {
                        data.send!!.deliveryType = MallConst.DISTRIBUTION_TYPE_SELFTAKE
                    }
                    R.id.rbDelivery -> {
                        data.send!!.deliveryType = MallConst.DISTRIBUTION_TYPE_SEND
                    }
                }
                initAddressUI(holder, data)
                onDistributionTypeChangeListener?.onDistributionTypeChange(data)
            }
        })
        if (data.send!!.deliveryType == MallConst.DISTRIBUTION_TYPE_SELFTAKE) {
            rgTakeMode.check(R.id.rbSelfTake)
        } else {
            rgTakeMode.check(R.id.rbDelivery)
        }
        val rvOrderGoods = holder.getView<RecyclerView>(R.id.rvOrderGoods)
        rvOrderGoods.layoutManager = LinearLayoutManager(mContext)
        rvOrderGoods.adapter = OrderInfoChildAdapter(mContext, data.products)
        val tvTime = holder.getView<TextView>(R.id.tvTime)
        tvTime.setOnClickListener(View.OnClickListener {
            showTimingPicker(holder, data)
        })
        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onClick(data)
                }
            }
        })
        updateAmountUI(holder, data)
    }


    /**
     * 结算个数,金额的更新
     */
    private fun updateAmountUI(holder: ViewHolder, shopCart: ShopCart) {
        val tvTotalNum = holder.getView<TextView>(R.id.tvTotalNum)
        val tvTotalAmount = holder.getView<TextView>(R.id.tvTotalAmount)
        val tvUnit = holder.getView<TextView>(R.id.tvUnit)
        var count = 0
        var amount = 0.toDouble()
        for (goods in shopCart.products) {
            amount += goods.salePrice.toDouble() * goods.productCount
            count += goods.productCount
        }
        tvUnit.text = AppBusManager.getAmountUnit()
        tvTotalNum.text = mContext.getString(R.string.scan_code_shop_count_start) + " $count " + "件"
        tvTotalAmount.text = amount.ext_formatAmount()
        calculateActivityDiscount(holder, shopCart, amount)
    }

    /**
     * 计算活动优惠
     */
    private fun calculateActivityDiscount(holder: ViewHolder, shopCart: ShopCart, amount: Double) {
        val isHaveActivity = MallBusManager.isHaveActivity(shopCart)
        if (!isHaveActivity) {
            return
        }
        val tvTotalAmount = holder.getView<TextView>(R.id.tvTotalAmount)
        val tvDiscount = holder.getView<TextView>(R.id.tvDiscount)
        val llDiscount = holder.getView<View>(R.id.llDiscount)
        var discount = 0.toDouble() //总优惠
        var totalRealAmount = 0.toDouble()//总实际支付金额
        for (goods in shopCart.products) {
            val a=MallBusManager.getProductActivity(goods)
            if (a != null) {
                totalRealAmount += a.discountPrice.toDouble() * goods.productCount
            } else {
                totalRealAmount += goods.salePrice.toDouble() * goods.productCount
            }
        }
        tvTotalAmount.text = totalRealAmount.ext_formatAmount()
        discount = amount - totalRealAmount
        if (discount > 0.toDouble()) {
            llDiscount.visibility = View.VISIBLE
            tvDiscount.text = discount.ext_formatAmountWithUnit()
        } else {
            llDiscount.visibility = View.GONE
        }
    }

    private fun showTimingPicker(holder: ViewHolder, data: ShopCart) {   //条件选择器初始化，自定义布局
        val tvTime = holder.getView<TextView>(R.id.tvTime)
        //时间选择器
        val pvTime = TimePickerBuilder(mContext, object : OnTimeSelectListener {
            override fun onTimeSelect(date: Date, v: View?) {
                val day = DateUtils.formatDate(date.time, DateUtils.yyyy_MM_dd)
                tvTime.text = day + " 9:00-17:00"
                data.send!!.pickUpTime = day
            }
        }).setCancelColor(mContext.resources.getColor(R.color.mainTextColor))
                .setSubmitColor(mContext.resources.getColor(R.color.mainColor))
                .setSubmitText(mContext.getString(R.string.submitText_ok))
                .setCancelText(mContext.getString(R.string.delete_dialog_cancel))
                .setTitleText(mContext.getString(R.string.date))
                .setTitleSize(16)
                .setTitleColor(mContext.resources.getColor(R.color.mainTextColor))
                .setDividerColor(mContext.resources.getColor(R.color.gray_dddddd))
                .setTitleBgColor(mContext.resources.getColor(R.color.white))
                .setContentTextSize(14)
                .setDate(Calendar.getInstance())
                .setRangDate(Calendar.getInstance(), DateUtils.getYearFirstDayCalendar(2))
                .build()

        pvTime.show()

    }

    private fun initAddressUI(holder: ViewHolder, data: ShopCart) {
        val tvName = holder.getView<TextView>(R.id.tvName)
        val tvAddress = holder.getView<TextView>(R.id.tvAddress)
        val tvAddressArrow = holder.getView<TextView>(R.id.tvAddressArrow)
        val takeTimeView = holder.getView<View>(R.id.llTime)
        if (data.send!!.deliveryType == MallConst.DISTRIBUTION_TYPE_SELFTAKE) { //自提
            holder.setText(R.id.tvTakeTitle, mContext.getString(R.string.order_send_address_1))
            takeTimeView.visibility = View.VISIBLE
            tvName.visibility = View.VISIBLE
            tvAddress.visibility = View.VISIBLE
            tvAddressArrow.visibility = View.GONE
            tvAddress.hint = "自提地址"
            if (shopInfo != null) {
                tvName.text = shopInfo!!.mobile
                tvAddress.text = shopInfo!!.province + shopInfo!!.city + shopInfo!!.area + shopInfo!!.address
            }
            holder.getView<View>(R.id.llAddress).isEnabled=false
        } else { //配送
            holder.setText(R.id.tvTakeTitle, mContext.getString(R.string.send_info))
            takeTimeView.visibility = View.GONE
            tvName.visibility = View.VISIBLE
            tvAddress.visibility = View.VISIBLE
            tvAddressArrow.visibility = View.VISIBLE
            tvAddress.hint = "配送地址"
            holder.getView<View>(R.id.llAddress).isEnabled=true
            holder.getView<View>(R.id.llAddress).setOnClickListener(View.OnClickListener {
                onAddressClickListener?.onClick(data)
            })
            if (address == null) {
                tvAddress.visibility = View.GONE
                tvName.text = ""
                tvName.hint = "暂无配送地址,点击可添加"
            } else {
                tvAddress.visibility = View.VISIBLE
                tvName.text = address!!.receive + " " + address!!.mobile
                tvAddress.text = address!!.city + address!!.area + address!!.location + "(" + address!!.address + ")"
            }
        }
    }
}
