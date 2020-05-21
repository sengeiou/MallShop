package com.epro.mall.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import com.alipay.sdk.app.EnvUtils
import com.epro.mall.R
import com.epro.mall.listener.WeixinPayResultEvent
import com.epro.mall.mvp.contract.PayManagerContract
import com.epro.mall.mvp.model.bean.OrderPayBean
import com.epro.mall.mvp.model.bean.PayInfo
import com.epro.mall.mvp.model.bean.WeiXinPayInfo
import com.epro.mall.mvp.presenter.PayManagerPresenter
import com.epro.mall.utils.MallConst
import com.epro.mall.listener.OrderPayEvent
import com.mike.baselib.activity.BaseTitleBarCustomActivity
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.utils.*
import com.tencent.mm.opensdk.constants.Build
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions


class PayManagerActivity : BaseTitleBarCustomActivity<PayManagerContract.View, PayManagerPresenter>(), PayManagerContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.ONLINE)// just used for sandbox env, otherwise, pls delete it.
        intent.putExtra("cannotRequestedOrientation", true)
        super.onCreate(savedInstanceState)
       // setActivitySize(1,1)
    }

    override fun onOrderPaySuccess(result: OrderPayBean.Result) {
        signData = result.notifyStr
        orderSn = result.orderSn
        if (TextUtils.isEmpty(signData) || TextUtils.isEmpty(orderSn)) {
            toast(getString(R.string.the_server_is_busy))
            showPayPayResult(MallConst.PAY_FAILED)
            return
        }
        switchPayMode()
    }

    companion object {
        const val TAG = "PayManager"
        const val RC_ALIPAY_PERM = 100
        const val PAY_STATUS = "pay_status"
        const val MAX_CHECK_COUNT = 5//轮询次数

        fun launchWithPayInfo(activity: Activity, payInfo: PayInfo, requestCode: Int) {
            activity.startActivityForResult(Intent(activity, PayManagerActivity::class.java)
                    .putExtra(ext_createJsonKey(PayInfo::class.java), AppBusManager.toJson(payInfo)), requestCode)
        }

        fun launchWithPayInfo(fragment: Fragment, payInfo: PayInfo, requestCode: Int) {
            fragment.startActivityForResult(Intent(fragment.context, PayManagerActivity::class.java)
                    .putExtra(ext_createJsonKey(PayInfo::class.java), AppBusManager.toJson(payInfo)), requestCode)
        }
    }

    override fun getPresenter(): PayManagerPresenter {
        return PayManagerPresenter()
    }


    override fun layoutContentId(): Int {
        return R.layout.activity_pay_manager
    }

    override fun initData() {

    }

    var payMode = MallConst.PAY_MODE_ZFB
    val rxTimer = RxTimer()
    var orderSn = ""
    var signData = ""
    var payInfo = PayInfo(orderSn, payMode, signData)
    var isDoingPay=false
    override fun initView() {
        super.initView()
        getTitleBar().visibility = View.GONE
        payInfo = AppBusManager.fromJsonWithClassKey(intent, PayInfo::class.java)!!
        orderSn = payInfo.orderSn
        payMode = payInfo.payMode
        signData = payInfo.paySignData
        if (!TextUtils.isEmpty(signData)) {
            switchPayMode()
        } else {
            mPresenter.orderPay(orderSn, MallConst.ORDER_PAY)
        }
        logTools.d(signData)
    }

    override fun onCheckOrderPaySuccess(payStatus: Int) {
        if (payStatus == MallConst.PAY_SUCCESS || payStatus == MallConst.PAY_FAILED) {
            showPayPayResult(payStatus)
        } else { //轮询,支付失败

        }
    }

    private fun showPayPayResult(payStatus: Int) {
        getMultipleStatusView().showContent()
        EventBus.getDefault().post(OrderPayEvent())
        setResult(Activity.RESULT_OK, Intent().putExtra(PAY_STATUS, payStatus))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        rxTimer.cancel()
        AppContext.getInstance().mainHandler.removeCallbacksAndMessages(null)
    }


    override fun onAliPaySuccess() { //支付成功
        //toast("支付宝支付成功")
        startPollingCheckOrder()
    }

//    fun setActivitySize(width:Int,height:Int){
//        val window = getWindow()
//        val layoutParams = window.getAttributes()
//        layoutParams.x = 0
//        layoutParams.y = 0
//        layoutParams.width = width
//        layoutParams.height = height
//        if(width==1){
//            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE
//            layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
//        }
//        window.setBackgroundDrawableResource(R.color.transparent)
//        window.setAttributes(layoutParams)
//    }


    fun startPollingCheckOrder() {
        //setActivitySize(DisplayManager.getScreenWidth()!!,DisplayManager.getScreenHeight()!!)
        showLoading("")
        rxTimer.interval(3000, RxTimer.IRxNext {
            if (it > MAX_CHECK_COUNT - 1) {
                showPayPayResult(MallConst.PAY_UNKOWN)
            } else {
                mPresenter.checkOrderPay(orderSn, MallConst.CHECK_ORDER_PAY)
            }
        })
    }

    override fun showError(errorMsg: String, errorCode: Int, type: String) {
        super.showError(errorMsg, errorCode, type)
        when (type) {
            MallConst.CHECK_ORDER_PAY -> {
                //支付异常
                showPayPayResult(MallConst.PAY_UNKOWN)
            }
            MallConst.ALIPAY_ACTION -> {
                //支付失败
                showPayPayResult(MallConst.PAY_FAILED)
            }
            else->{
                finish()
            }
        }
    }

    override fun dismissLoading(errorMsg: String, errorCode: Int, type: String) {
        if (MallConst.CHECK_ORDER_PAY == type) {
            if (ErrorStatus.SUCCESS == errorCode) {
                if (rxTimer.recyCount > MAX_CHECK_COUNT - 1) {
                    super.dismissLoading(errorMsg, errorCode, type)
                }
            }
        } else {
            super.dismissLoading(errorMsg, errorCode, type)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        logTools.d(requestCode)
        logTools.d(resultCode)
        if (Activity.RESULT_OK == resultCode) {

        }
    }

    private fun switchPayMode() {
        when (payMode) {
            MallConst.PAY_MODE_ZFB -> {
                requestAliPayPermissions()
            }
            MallConst.PAY_MODE_WX -> {
                requestWeixinPay()
            }
        }
    }

    fun requestWeixinPay() {
        val weiXinPayInfo = AppBusManager.fromJson(signData, WeiXinPayInfo::class.java)
        logTools.d(weiXinPayInfo?.appid)
        if (weiXinPayInfo != null) {
            val api = WXAPIFactory.createWXAPI(this, weiXinPayInfo.appid)
            api.registerApp(weiXinPayInfo.appid)
            AppBusManager.setAppId(weiXinPayInfo.appid)
            val isPaySupported = api.wxAppSupportAPI >= Build.PAY_SUPPORTED_SDK_INT
            if (isPaySupported) {
                val req = PayReq()
                req.appId = weiXinPayInfo.appid
                req.partnerId = weiXinPayInfo.partnerid
                req.prepayId = weiXinPayInfo.prepayid
                req.nonceStr = weiXinPayInfo.noncestr
                req.timeStamp = weiXinPayInfo.timestamp
                req.packageValue = weiXinPayInfo.package1
                req.sign = weiXinPayInfo.sign
                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                if (api.sendReq(req)) {
                    //finish()
                } else {
                    showPayPayResult(MallConst.PAY_FAILED)
                }
            } else {
                toast(getString(R.string.wechat_version_too_low))
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onWeixinPayResultEvent(event: WeixinPayResultEvent) {
        isDoingPay=true
        if (event.isPaySuccess) { //微信结果成功
            startPollingCheckOrder()
        } else {
            showPayPayResult(MallConst.PAY_FAILED)
        }
    }


    override fun initListener() {
//        MutiAppUIListener.setListener(this,object :MutiAppUIListener.OnMutiAppUIChangeListener{
//            override fun mutiAppUIShow(height: Int) {
//                toast("多应用界面弹出")
//            }
//
//            override fun mutiAppUIHide(height: Int) {
//                toast("多应用界面弹出")
//                finish()
//            }
//        })
    }

    @AfterPermissionGranted(RC_ALIPAY_PERM)
    private fun requestAliPayPermissions() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            mPresenter.aliPay(signData, MallConst.ALIPAY_ACTION)

        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this,"请求电话等权限",
                    RC_ALIPAY_PERM, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    var resusme=0
    var pause=0
    override fun onResume() {
        super.onResume()
        logTools.d("onResume"+resusme)
        resusme++
//        if(!isDoingPay&&isPaused&&payMode==MallConst.PAY_MODE_WX){
//            finish()
//        }
        logTools.d(AppContext.getInstance().isInstalledVirtualLocationPackage)
    }
    var isPaused=false
    override fun onPause() {
        super.onPause()
        logTools.d("onPause"+pause)
        pause++
        //可能是意图界面打开执行
        isPaused=true
    }



}


