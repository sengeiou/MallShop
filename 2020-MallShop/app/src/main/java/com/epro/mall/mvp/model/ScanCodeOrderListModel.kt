package com.epro.mall.mvp.model

import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.mike.baselib.listener.RetryWithDelay
import com.epro.mall.mvp.model.bean.ScanCodeOrderListBean
import io.reactivex.Observable

class ScanCodeOrderListModel : BaseModel() {
    fun ScanCodeOrderList(shopId:String,page:Int): Observable<ScanCodeOrderListBean> {

        val p = ScanCodeOrderListBean.PageList(page)
        return getApiSevice().ScanCodeOrderList(ScanCodeOrderListBean.Send(shopId,p))
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<ScanCodeOrderListBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }
}