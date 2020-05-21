package com.epro.mall.mvp.model

import android.app.Activity
import android.text.TextUtils
import com.alipay.sdk.app.PayTask
import com.epro.mall.R
import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.mike.baselib.listener.RetryWithDelay
import com.epro.mall.mvp.model.bean.AliPayBean
import com.epro.mall.mvp.model.bean.AliPayResult
import com.epro.mall.mvp.model.bean.CheckOrderPayBean
import com.epro.mall.mvp.model.bean.OrderPayBean
import com.epro.mall.utils.MallBusManager
import com.epro.mall.utils.MallConst
import com.mike.baselib.net.exception.PayException
import com.mike.baselib.utils.AppContext
import io.reactivex.Observable

class PayManagerModel : BaseModel() {
    /**
     * 支付宝支付
     */
    fun aliPay(payInfo: String, activity: Activity): Observable<AliPayBean> {
        return Observable.just(true).flatMap {
            logTools.d(payInfo)
            logTools.d(PayTask(activity).version)
            val payResult = AliPayResult(PayTask(activity).payV2(payInfo, true))
            logTools.d("do here")
            logTools.toJson(payResult)
            if (payResult.resultStatus.toInt() == 9000) {
                return@flatMap Observable.just(AliPayBean(payResult.resultStatus.toInt(), payResult.result, ""))
            } else {
                return@flatMap Observable.error<AliPayBean>(PayException(MallBusManager.getZfbResultText(payResult.resultStatus.toInt()), payResult.resultStatus.toInt()))
            }
        }.compose(SchedulerUtils.ioToMain())
    }

    fun checkOrderPay(orderSn: String): Observable<CheckOrderPayBean> {
        val send = CheckOrderPayBean.Send(orderSn)
        return getApiSevice().checkOrderPay(send)
                .flatMap {
                    var code = it.code
                    if (it.code == MallConst.PAY_SUCCESS || it.code == MallConst.PAY_UNKOWN || it.code == MallConst.PAY_FAILED) {
                        it.result = it.code.toString()
                        code = ErrorStatus.SUCCESS
                        it.checkResult = CheckOrderPayBean.Result(it.code, it.message)
                    }
                    if (code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<CheckOrderPayBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun orderPay(orderSn: String): Observable<OrderPayBean> {
        val send = OrderPayBean.Send(orderSn)
        return getApiSevice().orderPay(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        if(TextUtils.isEmpty(it.result.notifyStr)||TextUtils.isEmpty(it.result.orderSn)){
                            return@flatMap Observable.error<OrderPayBean>(ApiException(AppContext.getInstance().getString(R.string.service_is_busy), -10000))
                        }
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<OrderPayBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }


}