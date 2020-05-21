package com.epro.mall.mvp.model

import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.epro.mall.listener.RetryWithDelay
import com.epro.mall.mvp.model.bean.MemberCentreBean
import io.reactivex.Observable

class MemberCentreModel : BaseModel() {
    fun MemberCentre(): Observable<MemberCentreBean> {
        return getApiSevice().MemberCentre()
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<MemberCentreBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }
}