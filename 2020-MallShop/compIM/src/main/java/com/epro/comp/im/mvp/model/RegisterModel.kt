package com.epro.comp.im.mvp.model

import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.epro.comp.im.mvp.model.bean.RegisterBean
import io.reactivex.Observable

class RegisterModel:BaseModel() {

    /**
     * 注册
     */
    fun register( loginname:String,name:String,details:String): Observable<RegisterBean> {
        return getApiSevice().register(loginname,name,details)
                .flatMap {
                    if(it.code== ErrorStatus.SUCCESS){
                        return@flatMap  Observable.just(it)
                    }else{
                        return@flatMap Observable.error<RegisterBean>(ApiException(it.message,it.code))
                    }
                }
               // .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

}
