package com.epro.mall.ui.activity;

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.epro.mall.R
import com.epro.mall.listener.OrderDeleteEvent
import com.epro.mall.mvp.contract.OrderDetailContract
import com.epro.mall.mvp.model.bean.OrderDetailBean
import com.epro.mall.mvp.model.bean.PayInfo
import com.epro.mall.mvp.presenter.OrderDetailPresenter
import com.epro.mall.ui.adapter.OrderListChildAdapter
import com.epro.mall.ui.fragment.PayManagerFragment
import com.epro.mall.utils.MallConst
import com.epro.mall.listener.CartChangeEvent
import com.epro.mall.listener.OrderPayEvent
import com.mike.baselib.activity.BaseTitleBarCustomActivity
import com.mike.baselib.utils.*
import kotlinx.android.synthetic.main.activity_order_detail.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.w3c.dom.Text

/**
 * 订单详情页面
 */
class OrderDetailActivity : BaseTitleBarCustomActivity<OrderDetailContract.View, OrderDetailPresenter>(), OrderDetailContract.View, View.OnClickListener {

    override fun onOrderAgainSuccess(productIds: ArrayList<String>) {
         EventBus.getDefault().post(CartChangeEvent())
         CartActivity.launchWithProductIds(this,productIds,ORDER_DETAIL_FOR_AGAIN)
    }

    override fun cancelOrderSuccess() {
        var msg = OrderDeleteEvent()
        msg.type = "1"
        EventBus.getDefault().postSticky(msg)
        toast(getString(R.string.cancel_success))
        finish()
    }

    override fun deleteOrderSuccess() {
        var msg = OrderDeleteEvent()
        msg.type = "0"
        EventBus.getDefault().postSticky(msg)
        toast(getString(R.string.delete_success))
        finish()
    }

    //订单详情加载
    var mAdpater: OrderListChildAdapter? = null
    var bean: OrderDetailBean.Result? = null
    var mTimer: CountDownTimer?=null
    override fun onOrderDetailSuccess(result: OrderDetailBean.Result) {
        initTimerDown(result.leftPayTime.toLong())
        tvSymbol.text = AppBusManager.getAmountUnit()
        tvSymbol2.text =  AppBusManager.getAmountUnit()
        tvSymbol3.text = AppBusManager.getAmountUnit()
        if ("1".equals(result.orderStatus)) { //待支付
            mTimer!!.start()
            titleStatus.text = getString(R.string.order_list_title_2)
            tvAlertLeft.text = getString(R.string.order_detail_list)
            tvAlertRight.text = getString(R.string.order_detail_list_end)
            tvShopName.text = result.shopName
            delieryType(result.deliveryType,result)
            tvOrderNo.text = result.orderSn
            tvOrderTime.text = result.createTime
            payStyle(result.payType)
            tvGoodsAmount.text = result.orderActualAmount.ext_formatAmount()
            tvFreight.text = result.logisticsFee.ext_formatAmount()
            tvGoodsNum.text = getString(R.string.scan_code_shop_count_start) + result.productCount + getString(R.string.scan_code_shop_count_end)
            tvAmount.text = result.orderActualAmount.ext_formatAmount()
            btnLeft.text = getString(R.string.order_item_cancel)
            btnRight.text = getString(R.string.order_item_pay)
        } else if ("3".equals(result.orderStatus) || "4".equals(result.orderStatus) || "5".equals(result.orderStatus)) {
            titleStatus.text = getString(R.string.order_list_title_3)            //待收货
            tvAlertLeft.text = getString(R.string.order_pick_up_code)
            tvAlertRight.visibility = View.GONE
            tvAlertCenter.text = result.deliveryCode
            tvShopName.text = result.shopName
            delieryType(result.deliveryType,result)
            tvOrderNo.text = result.orderSn
            tvOrderTime.text = result.createTime
            payStyle(result.payType)
            tvGoodsAmount.text = result.orderActualAmount.ext_formatAmount()
            tvFreight.text = result.logisticsFee.ext_formatAmount()   // 运费
            tvGoodsNum.text = getString(R.string.scan_code_shop_count_start) + result.productCount + getString(R.string.scan_code_shop_count_end)
            tvAmount.text = result.orderActualAmount.ext_formatAmount()
            llBottom.visibility = View.GONE
        } else if ("6".equals(result.orderStatus)) {
            titleStatus.text = getString(R.string.order_list_title_4)            //交易完成
            tvAlertLeft.visibility = View.GONE
            tvAlertCenter.text = getString(R.string.order_transaction_complete)
            tvAlertRight.visibility = View.GONE
            tvShopName.text = result.shopName
            delieryType(result.deliveryType,result)
            tvOrderNo.text = result.orderSn
            tvOrderTime.text = result.createTime
            payStyle(result.payType)
            tvGoodsAmount.text = result.orderActualAmount.ext_formatAmount()
            tvFreight.text = result.logisticsFee.ext_formatAmount()  // 运费
            tvGoodsNum.text = getString(R.string.scan_code_shop_count_start) + result.productCount + getString(R.string.scan_code_shop_count_end)
            tvAmount.text = result.orderActualAmount.ext_formatAmount()
            llBottom.visibility = View.GONE
        } else if ("2".equals(result.orderStatus) || "7".equals(result.orderStatus)) {
            titleStatus.text = getString(R.string.order_list_title_5)            //交易取消
            tvAlertLeft.visibility = View.GONE
            tvAlertCenter.text = result.orderCancelReason
            tvAlertRight.visibility = View.GONE
            tvShopName.text = result.shopName
            delieryType(result.deliveryType,result)
            tvOrderNo.text = result.orderSn
            tvOrderTime.text = result.createTime
            payStyle(result.payType)
            tvGoodsAmount.text = result.orderActualAmount.ext_formatAmount()
            tvFreight.text = result.logisticsFee.ext_formatAmount()  //运费
            tvGoodsNum.text = getString(R.string.scan_code_shop_count_start) + result.productCount + getString(R.string.scan_code_shop_count_end)
            tvAmount.text = result.orderActualAmount.ext_formatAmount()
            btnLeft.text = getString(R.string.order_item_btn_del)
            btnRight.text = getString(R.string.order_item_renew_add)
        }
        //是否显示优惠价
        showDiscountView(result)
        if (result.products != null) {
            mAdpater!!.setData(result.products)
            bean = result
        }
    }

    private fun showDiscountView(result: OrderDetailBean.Result) {
        if (!TextUtils.isEmpty(result.orderTotalAmount)){
            val orderActualAmount = result.orderActualAmount.toDouble()
            val orderTotalAmount = result.orderTotalAmount.toDouble()
            if (orderActualAmount == orderTotalAmount){
                oderDiscount.visibility = View.GONE
            }else{
                    oderDiscount.visibility = View.VISIBLE
                    val discountPrice = orderTotalAmount-orderActualAmount
                    oderPrice.text = discountPrice.ext_formatAmount()
            }
        }else{
            oderDiscount.visibility = View.GONE
        }

    }

    private fun initTimerDown(time:Long) {
        mTimer = object : CountDownTimer(time, 1000) {
            override fun onTick(l: Long) {
                tvAlertCenter.text = DateUtils.msecToTime(l.toInt())
            }
            override fun onFinish() {
                tvAlertCenter.text = getString(R.string.time_out_to_pay)
            }
        }
    }

    //配送方式
    private fun delieryType(deliveryType: String,result: OrderDetailBean.Result) {
        if ("0".equals(deliveryType)) {
            tvTakeMode.text = getString(R.string.order_self_goto_pickup)
            shopPhone.text = getString(R.string.shop_phone_address)
            shopAddressTitle.text = getString(R.string.shop_phone_address_title)
            shopPhoneNum.text = result.shopMobile
            if (!TextUtils.isEmpty(result.pickUpAdress)){
                shopAddress.text = result.pickUpAdress
            }
        } else if ("1".equals(deliveryType)) {
            tvTakeMode.text = getString(R.string.order_goto_delivery)
            shopPhone.text = getString(R.string.pickup_phone_name)+"    "
            shopAddressTitle.text = getString(R.string.pickup_address)
            shopPhoneNum.text = result.consignee + "  " + result.mobile
            if (!TextUtils.isEmpty(result.address)){
                shopAddress.text = result.address
            }
        }
    }

    //付款方式
    private fun payStyle(payType: String) {
        if ("1".equals(payType)) {
            tvPayMode.text = getString(R.string.pay_style_1)
        } else if ("2".equals(payType)) {
            tvPayMode.text = getString(R.string.pay_style_2)
        } else if ("3".equals(payType)) {
            tvPayMode.text = getString(R.string.pay_style_3)
        } else if ("4".equals(payType)) {
            tvPayMode.text = getString(R.string.pay_style_4)
        } else if ("5".equals(payType)) {
            tvPayMode.text = getString(R.string.pay_style_5)
        } else if ("6".equals(payType)) {
            tvPayMode.text = getString(R.string.pay_style_6)
        }
    }

    override fun onClick(p0: View?) {
        when (p0) {
            btnLeft -> {
                if (1 == bean!!.orderStatus.toInt()) {
                    mPresenter.cancelOrder(MallConst.CANCEL_ORDER, bean!!.orderSn, getString(R.string.user_cancel_order))
                } else if (2 == bean!!.orderStatus.toInt() || 7 == bean!!.orderStatus.toInt()) {
                    mPresenter.deleteOrder(MallConst.DELETE_ORDER, bean!!.orderSn)
                }
            }
            btnRight -> {
            if (1 == bean!!.orderStatus.toInt()) { //待支付 点击去支付
               // PayManagerActivity.launchWithPayInfo(this, PayInfo(bean!!.orderSn,bean!!.payType.toInt(),""),ORDER_DETAIL_FOR_PAY)
                supportFragmentManager.beginTransaction().replace(R.id.fragmentPay,
                        PayManagerFragment.newInstance( PayInfo(bean!!.orderSn,bean!!.payType.toInt(),""))).commitAllowingStateLoss()
            } else if (2 == bean!!.orderStatus.toInt() || 7 == bean!!.orderStatus.toInt()) {
                //已取消 点击重新购买
                mPresenter.orderAgain(bean!!.orderSn, MallConst.ORDER_AGAIN)
            }
        }

            //点击店铺跳转
            tvShopName -> {
                ShopDetailActivity.launchWithShopId(this, bean!!.shopId)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            ORDER_DETAIL_FOR_PAY->{
                if (Activity.RESULT_OK==resultCode){
                    val payStatus=data!!.getIntExtra(PayManagerActivity.PAY_STATUS,MallConst.PAY_FAILED)
                    showPayPayResult(payStatus)
                }
            }
            ORDER_DETAIL_FOR_AGAIN->{
                  if (Activity.RESULT_OK == resultCode){
                      finish()
                  }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onOrderPayEvent(event: OrderPayEvent) {
        val payStatus = event.payStatus
        showPayPayResult(payStatus)
    }

    private fun showPayPayResult(payStatus: Int) {
        if (payStatus == MallConst.PAY_SUCCESS) {
            toast(getString(R.string.payment_successful))
            finish()
        } else {
            toast(getString(R.string.payment_failed))
        }
    }

    companion object {
        const val TAG = "OrderDetail"
        const val ORDER_DETAIL_FOR_PAY = 3
        const val ORDER_DETAIL_FOR_AGAIN = 4
        fun launch(context: Context) {
            launchWithStr(context, "")
        }

        fun launchWithStr(context: Context, str: String) {
            context.startActivity(Intent(context, OrderDetailActivity::class.java).putExtra(TAG, str))
        }
    }

    override fun getPresenter(): OrderDetailPresenter {
        return OrderDetailPresenter()
    }

    override fun layoutContentId(): Int {
        return R.layout.activity_order_detail
    }

    override fun initData() {
    }

    var orderSn:String?=null
    override fun initView() {
        super.initView()
        getTitleView().text = getString(R.string.order_detail)
        orderSn  = intent.getStringExtra(TAG)!!
        mPresenter.getOrderDetail(MallConst.GET_ORDER_DETAIL, orderSn!!)
        rvOrderGoods.layoutManager = LinearLayoutManager(this)
        mAdpater = OrderListChildAdapter(this, ArrayList())
        rvOrderGoods.adapter = mAdpater
    }

    override fun initListener() {
        btnLeft.ext_doubleClick(this)
        btnRight.ext_doubleClick(this)
        tvShopName.setOnClickListener(this)
    }

    override fun onDestroy() {
        if(mTimer!=null){
            mTimer!!.cancel()
        }
        super.onDestroy()
    }
}


