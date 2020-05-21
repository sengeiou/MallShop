package com.epro.mall.mvp.model

import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.mike.baselib.listener.RetryWithDelay
import com.epro.mall.mvp.model.bean.PhoneServiceTermsBean
import io.reactivex.Observable

class PhoneServiceTermsModel : BaseModel() {
    fun PhoneServiceTerms(): Observable<PhoneServiceTermsBean> {
        return getApiSevice().PhoneServiceTerms()
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<PhoneServiceTermsBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }
}