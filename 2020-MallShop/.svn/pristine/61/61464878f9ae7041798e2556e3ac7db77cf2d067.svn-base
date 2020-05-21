package com.epro.mall.mvp.model

import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.mike.baselib.listener.RetryWithDelay
import com.epro.mall.mvp.model.bean.ScanCodeOrderListDetailBean
import io.reactivex.Observable

class ScanCodeOrderListDetailModel : BaseModel() {
    fun ScanCodeOrderListDetail(orderSn:String): Observable<ScanCodeOrderListDetailBean> {
        return getApiSevice().ScanCodeOrderListDetail(ScanCodeOrderListDetailBean.Send(orderSn))
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<ScanCodeOrderListDetailBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }
}