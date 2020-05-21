package com.epro.mall.mvp.model

import com.mike.baselib.listener.RetryWithDelay
import com.epro.mall.mvp.model.bean.LogoutBean
import com.epro.mall.mvp.model.bean.MyInfoBean
import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

class MineModel :BaseModel() {
    fun MyInfo(): Observable<MyInfoBean> {
        return getApiSevice().getMyInfo()
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<MyInfoBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun loginOut(): Observable<LogoutBean> {
        return getApiSevice().logout()
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<LogoutBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }
}
