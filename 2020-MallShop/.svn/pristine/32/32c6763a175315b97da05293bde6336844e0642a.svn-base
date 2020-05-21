package com.epro.mall.ui.activity;

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.mike.baselib.activity.BaseTitleBarCustomActivity
import com.epro.mall.R
import com.epro.mall.mvp.contract.ScanBarPurchaseContract
import com.epro.mall.mvp.model.bean.NearlyShop
import com.epro.mall.mvp.presenter.ScanBarPurchasePresenter
import com.epro.mall.ui.fragment.OpenLocationServiceBottomPopup
import com.epro.mall.listener.GetNearlyShopSuccessEvent
import com.mike.baselib.fragment.BaseBottomPopup
import com.mike.baselib.utils.AppUtils
import com.mike.baselib.utils.ext_getFromJsonWithClassKey
import com.mike.baselib.utils.toast
import kotlinx.android.synthetic.main.activity_scan_bar_purchase.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class ScanBarPurchaseActivity : BaseTitleBarCustomActivity<ScanBarPurchaseContract.View, ScanBarPurchasePresenter>(), ScanBarPurchaseContract.View, View.OnClickListener {

    override fun onClick(p0: View?) {
        val shopId = intent.getStringExtra(SHOP_ID)
        if (TextUtils.isEmpty(shopId) && p0 != tvCurrentShop) {
            toast("请选择门店")
            return
        }
        when (p0) {
            //扫码购
            ivScanBar -> {
                ScanBuyCartActivity.launchWithShopId_Type(this, shopId!!, ScanBuyCartActivity.BAR_CODE_GETYPE_SCAN)
            }
            //手输入
            manualInput -> {
                ScanBuyCartActivity.launchWithShopId_Type(this, shopId!!, ScanBuyCartActivity.BAR_CODE_GETYPE_INPUT)
            }
            //订单
            scanOrder -> {
                ScanCodeOrderListActivity.launchWithStr(this, shopId!!)
            }
            tvCurrentShop -> {
                var id = intent.getStringExtra(SHOP_ID)
                if (TextUtils.isEmpty(id)) {
                    id = ""
                }
                NearlyShopListActivity.launchWithShopIdForResult(this, id!!, FOR_SHOP)
            }
        }
    }

    companion object {
        const val TAG = "SanBuy"
        const val SHOP_ID = "shop_id"
        const val SHOP_NAME = "shop_name"
        const val FOR_SHOP = 10
        fun launchWithShopId_ShopName(context: Context, shopId: String, shopName: String) {
            context.startActivity(Intent(context, ScanBarPurchaseActivity::class.java).putExtra(SHOP_ID, shopId).putExtra(SHOP_NAME, shopName))
        }

        fun launch(context: Context) {
            context.startActivity(Intent(context, ScanBarPurchaseActivity::class.java))
        }
    }

    override fun getPresenter(): ScanBarPurchasePresenter {
        return ScanBarPurchasePresenter()
    }

    override fun onScanBarPurchaseSuccess() {
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                FOR_SHOP -> {
                    val nealyShop = data!!.ext_getFromJsonWithClassKey(NearlyShop::class.java)
                    intent.putExtra(SHOP_ID, nealyShop!!.shopId)
                    intent.putExtra(SHOP_NAME, nealyShop!!.shopName)
                    tvCurrentShop.text = nealyShop!!.shopName
                }
            }
        }
    }


    override fun layoutContentId(): Int {
        return R.layout.activity_scan_bar_purchase
    }

    override fun initData() {
        val shopId = intent.getStringExtra(SHOP_ID)
        if (TextUtils.isEmpty(shopId)) {
            NearlyShopListActivity.launch(this)
        }
    }

    override fun initView() {
        super.initView()
        val shopName = intent.getStringExtra(SHOP_NAME)
        if (!TextUtils.isEmpty(shopName)) {
            tvCurrentShop.text = shopName
        }
        getTitleView().text = "易喵扫码购"
        getTitleBar().findViewById<View>(R.id.vLine).visibility = View.GONE
    }

    override fun initListener() {
        ivScanBar.setOnClickListener(this)
        manualInput.setOnClickListener(this)
        scanOrder.setOnClickListener(this)
        tvCurrentShop.setOnClickListener(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onGetNearlyShopSuccess(event: GetNearlyShopSuccessEvent) {
        if (event.nealyShop != null) {
            intent.putExtra(SHOP_ID, event.nealyShop!!.shopId)
            intent.putExtra(SHOP_NAME, event.nealyShop!!.shopName)
            tvCurrentShop.text = event.nealyShop!!.shopName
        } else {
            tvCurrentShop.text = "请选择门店"
            intent.putExtra(SHOP_ID, "")
            intent.putExtra(SHOP_NAME, "")
        }
    }
}


