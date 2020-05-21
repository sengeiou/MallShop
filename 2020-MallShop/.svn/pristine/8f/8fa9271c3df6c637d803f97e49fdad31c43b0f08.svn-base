package com.epro.mall.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.epro.mall.R
import com.mike.baselib.activity.BaseTitleBarSimpleActivity
import kotlinx.android.synthetic.main.activity_scan_buy.*
@Deprecated("废弃")
class ScanBuyActivity : BaseTitleBarSimpleActivity(), View.OnClickListener {
    override fun onClick(p0: View?) {
        when (p0) {
            ivScan -> {
                val shopId = intent.getStringExtra(SHOP_ID)
                ScanBuyCartActivity.launchWithShopId_Type(this,shopId!!,ScanBuyCartActivity.BAR_CODE_GETYPE_SCAN)
            }
            tvInputBarCode -> {
                val shopId = intent.getStringExtra(SHOP_ID)
                ScanBuyCartActivity.launchWithShopId_Type(this,shopId!!,ScanBuyCartActivity.BAR_CODE_GETYPE_INPUT)
            }
            getRightView()->{
                ScanCodeOrderListActivity.launchWithStr(this,shopIdToOrder!!)
            }
        }
    }

    override fun layoutContentId(): Int {
        return R.layout.activity_scan_buy
    }


    companion object {
        const val TAG = "SanBuy"
        const val SHOP_ID = "shop_id"
        const val SHOP_NAME = "shop_name"
        var shopIdToOrder:String?=null
        fun launchWithShopId_ShopName(context: Context, shopId: String,shopName:String) {
            shopIdToOrder = shopId
            context.startActivity(Intent(context, ScanBuyActivity::class.java).putExtra(SHOP_ID, shopId).putExtra(SHOP_NAME,shopName))
        }
    }

    override fun initData() {
    }

    override fun initView() {
        super.initView()
        getTitleView().text =intent.getStringExtra(SHOP_NAME)
        getRightView().visibility = View.VISIBLE
        getRightView().text = getString(R.string.order)
    }

    override fun initListener() {
        ivScan.setOnClickListener(this)
        tvInputBarCode.setOnClickListener(this)
        getRightView().setOnClickListener(this)
    }

}


