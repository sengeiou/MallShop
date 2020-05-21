package com.epro.mall.ui.activity;

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import com.epro.mall.R
import com.epro.mall.mvp.contract.OrderInfoContract
import com.epro.mall.mvp.model.bean.*
import com.epro.mall.mvp.presenter.OrderInfoPresenter
import com.epro.mall.ui.adapter.OrderInfoAdapter
import com.epro.mall.ui.fragment.PayManagerFragment
import com.epro.mall.ui.fragment.SelectPayModeBottomPopup
import com.epro.mall.utils.MallBusManager
import com.epro.mall.utils.MallConst
import com.epro.mall.listener.CartChangeEvent
import com.epro.mall.listener.OrderPayEvent
import com.mike.baselib.activity.BaseTitleBarCustomActivity
import com.mike.baselib.fragment.BaseBottomPopup
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.utils.*
import kotlinx.android.synthetic.main.activity_order_info.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 订单信息确认页面
 */
class OrderInfoActivity : BaseTitleBarCustomActivity<OrderInfoContract.View, OrderInfoPresenter>(), OrderInfoContract.View, View.OnClickListener {
    override fun onGetAddressListSuccess(addressList: ArrayList<Address>) {
        if (addressList.isNotEmpty()) {
            if(shopInfo==null){
                toast("店铺信息为空")
                return
            }
            orderAdapter!!.address = MallBusManager.getRightAddress(shopInfo!!,addressList)
            orderAdapter!!.notifyDataSetChanged()
        }
    }

    private fun showPayPayResult(payStatus: Int) {
        if (payStatus == MallConst.PAY_SUCCESS) {
            PayResultActivity.launchWithPayResult(this, PayResult(payStatus, orderSn, shopCart!!.shopId, shopCart!!.shopName))
            setResult(Activity.RESULT_OK)
        } else {
            OrderDetailActivity.launchWithStr(this, orderSn)
        }
        val event = OrderPayEvent()
        event.payStatus = payStatus
        finish()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onOrderPayEvent(event: OrderPayEvent) {
        val payStatus = event.payStatus
        showPayPayResult(payStatus)
    }

    override fun onGetShopInfoSuccess(shopInfo: ShopInfo) {
        this.shopInfo=shopInfo
        shopCart!!.shopName = shopInfo.shopName
        shopCart!!.shopAddress = shopInfo.province + shopInfo.city + shopInfo.area + shopInfo.address
        val list = ArrayList<ShopCart>()
        list.add(shopCart!!)
        orderAdapter!!.shopInfo = shopInfo
        orderAdapter!!.setData(list)
    }


    var payMode = MallConst.PAY_MODE_ZFB
    var orderSn = ""
    var signData = ""
    override fun onClick(p0: View?) {
        when (p0) {
            rlPayMode -> {
                showSelectPayModeBottomPopup()
            }
            btnConfirm -> {  //生成订单
                if (TextUtils.isEmpty(orderSn) || TextUtils.isEmpty(signData)) {  //新生成订单
                    val send = orderAdapter!!.mData[0].send
                    if (send!!.deliveryType == MallConst.DISTRIBUTION_TYPE_SEND) {//配送方式
                        if (orderAdapter!!.address == null) {
                            toast("请选择配送地址")
                            return
                        }
                        send.addressId = orderAdapter!!.address!!.id
                    } else { //自提
//                    if (!send!!.pickUpTime.contains("-")) {
//                        toast("请选择自提时间")
//                        return
//                    }
                    }
                    send!!.payType = payMode
                    mPresenter.createOrder(send, MallConst.CREATE_ORDER)
                } else {
                   // PayManagerActivity.launchWithPayInfo(this, PayInfo(orderSn, payMode, signData), FOR_PAY_RESULT)
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentPay,
                            PayManagerFragment.newInstance( PayInfo(orderSn, payMode, signData))).commitAllowingStateLoss()
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Activity.RESULT_OK == resultCode) {
            when (requestCode) {
                FOR_PAY_RESULT -> {
                    val payStatus = data!!.getIntExtra(PayManagerActivity.PAY_STATUS, MallConst.PAY_FAILED)
                    showPayPayResult(payStatus)
                }
                FOR_ADDRESS_SELECT -> {
                    val address = data!!.ext_getFromJsonWithClassKey(Address::class.java)
                    orderAdapter!!.address = address
                    orderAdapter!!.notifyDataSetChanged()
                }
            }
        }
    }

    override fun showError(errorMsg: String, errorCode: Int, type: String) {
        super.showError(errorMsg, errorCode, type)
        if (type == MallConst.CREATE_ORDER && errorCode != ErrorStatus.NETWORK_ERROR) {
            EventBus.getDefault().post(CartChangeEvent())
            //finish()
        }
    }

    private fun showSelectPayModeBottomPopup() {
        BaseBottomPopup.Builder(SelectPayModeBottomPopup.newInstance(payMode))
                .setOnPopupDismissListener(object : BaseBottomPopup.OnPopupDismissListener {
                    override fun onPopupDismiss(bundle: Bundle?) {
                        if (bundle != null) {
                            val json = bundle.getString(ext_createJsonKey(Item::class.java))
                            if (!TextUtils.isEmpty(json)) {
                                val item = AppBusManager.fromJson(json, Item::class.java)
                                tvPayMode.text = item?.content!!
                                if (this@OrderInfoActivity.payMode != item.icon) {
                                    this@OrderInfoActivity.payMode = item.icon
                                    signData = ""
                                }
                            }
                        }
                    }
                })
                .create()
                .show(supportFragmentManager, "pay_mode")
    }

    companion object {
        const val TAG = "createOrder"
        const val FOR_PAY_RESULT = 11
        const val FOR_ADDRESS_SELECT = 12
        //        fun launchWithShopCart(activity: Activity, shopCart: ShopCart, requestCode: Int) {
//            activity.startActivityForResult(Intent(activity, OrderInfoActivity::class.java)
//                    .putExtra(ext_createJsonKey(ShopCart::class.java), AppBusManager.toJson(shopCart)), requestCode)
//        }
        fun launchWithShopCart(activity: Activity, shopCart: ShopCart, requestCode: Int) {
            activity.startActivityForResult(Intent(activity, OrderInfoActivity::class.java)
                    .ext_putJsonExtra(shopCart), requestCode)
        }

        fun launchWithShopCart(fragment: Fragment, shopCart: ShopCart, requestCode: Int) {
            fragment.startActivityForResult(Intent(fragment.context, OrderInfoActivity::class.java)
                    .putExtra(ext_createJsonKey(ShopCart::class.java), AppBusManager.toJson(shopCart)), requestCode)
        }
    }

    override fun getPresenter(): OrderInfoPresenter {
        return OrderInfoPresenter()
    }


    override fun onCreateOrderSuccess(result: CreateOrderBean.Result) {
        toast(getString(R.string.orders_submitted_successfully))
        EventBus.getDefault().post(CartChangeEvent())
        signData = result.notifyStr
        orderSn = result.orderSn
       // PayManagerActivity.launchWithPayInfo(this, PayInfo(result.orderSn, payMode, result.notifyStr), FOR_PAY_RESULT)
        supportFragmentManager.beginTransaction().replace(R.id.fragmentPay,
                PayManagerFragment.newInstance( PayInfo(result.orderSn, payMode, result.notifyStr))).commitAllowingStateLoss()
    }


    override fun layoutContentId(): Int {
        return R.layout.activity_order_info
    }

    override fun initData() {
        if (shopCart != null) {
            mPresenter.getShopInfo(shopCart!!.shopId, MallConst.GET_SHOP_INFO)
        }
    }

    var shopCart: ShopCart? = null
    var orderAdapter: OrderInfoAdapter? = null
    var shopInfo:ShopInfo?=null
    override fun initView() {
        super.initView()
        getTitleView().text = getString(R.string.confirm_order)
        shopCart = intent.ext_getFromJsonWithClassKey(ShopCart::class.java)
        var count = 0
        var amount = 0.toDouble()
        for (goods in shopCart!!.products) {
            amount += goods.salePrice.toDouble() * goods.productCount
            count += goods.productCount
        }
        tvUnit.text = AppBusManager.getAmountUnit()
        tvTotalAmount.text = amount.ext_formatAmount()
        tvPayMode.text = MallBusManager.getPayModeText(payMode)
        val send = CreateOrderBean.Send(shopCart!!.shopId, MallConst.DISTRIBUTION_TYPE_SELFTAKE
                , "", "0", count, amount.ext_formatAmount(), MallConst.PAY_MODE_ZFB, "", shopCart!!.products)
        val send2=handlerActivity()
        shopCart!!.send =if(send2==null) send else send2
        shopCart!!.shopAddress = ""
        val list = ArrayList<ShopCart>()
        list.add(shopCart!!)
        rvShopOrders.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        orderAdapter = OrderInfoAdapter(this, list)
        rvShopOrders.adapter = orderAdapter
        orderAdapter!!.onDistributionTypeChangeListener = object : OrderInfoAdapter.OnDistributionTypeChangeListener {
            override fun onDistributionTypeChange(item: ShopCart) {
                if (item.send!!.deliveryType == MallConst.DISTRIBUTION_TYPE_SEND&&orderAdapter?.address==null) {//配送方式
                    mPresenter.getAddressList(MallConst.GET_MY_ADDRESS_LIST)
                }
            }
        }
        orderAdapter!!.onAddressClickListener = object : OrderInfoAdapter.OnAddressClickListener {
            override fun onClick(item: ShopCart) {
                var address = Address("", "", "", "", "", 0, "", "", "", "", "", "", "")
                if (orderAdapter!!.address != null) {
                    address = orderAdapter!!.address!!
                }
                if(shopInfo==null){
                    toast("店铺信息为空")
                    return
                }
                AddressListActivity.launchWithShopInfo_Address(this@OrderInfoActivity, shopInfo!!,address, FOR_ADDRESS_SELECT)
            }
        }
    }

    /**
     * 处理有活动的情况
     */
    private fun handlerActivity():CreateOrderBean.Send?{
        if(!MallBusManager.isHaveActivity(shopCart!!)){
            return null
        }
        val products=ArrayList<CartGoods>()
        var totalRealAmount = 0.toDouble()//总实际支付金额
        var count = 0
        for (goods in shopCart!!.products) {
            val newGoods=goods.copy()
            val a=MallBusManager.getProductActivity(goods)
            if (a != null) {
                totalRealAmount += a.discountPrice.toDouble() * goods.productCount
                newGoods.salePrice=a.discountPrice
            } else {
                totalRealAmount += goods.salePrice.toDouble() * goods.productCount
            }
            count += goods.productCount
            products.add(newGoods)
        }
        tvTotalAmount.text = totalRealAmount.ext_formatAmount()
        val send = CreateOrderBean.Send(shopCart!!.shopId, MallConst.DISTRIBUTION_TYPE_SELFTAKE
                , "", "0", count, totalRealAmount.ext_formatAmount(), MallConst.PAY_MODE_ZFB, "", products)
        return send
    }


    override fun initListener() {
        rlPayMode.setOnClickListener(this)
        btnConfirm.ext_doubleClick(this)
    }


}


