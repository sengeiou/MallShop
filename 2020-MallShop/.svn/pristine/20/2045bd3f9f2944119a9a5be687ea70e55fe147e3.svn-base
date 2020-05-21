package com.epro.mall.mvp.model

import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.mike.baselib.listener.RetryWithDelay
import com.epro.mall.mvp.model.bean.FindPasswordBean
import com.epro.mall.mvp.model.bean.GetVfBean
import com.epro.mall.mvp.model.bean.LoginBean
import io.reactivex.Observable

class FindPasswordModel : BaseModel() {
    fun findPassword( account:String,password:String,rePassword:String,code:String,authType:String): Observable<FindPasswordBean> {
        val send=FindPasswordBean.Send(account,password,rePassword,code,authType)
        return getApiSevice().findPassword(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<FindPasswordBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 获取验证码
     */
    fun getVfCode(account:String,type:Int): Observable<GetVfBean> {
        return getApiSevice().getVfcode(GetVfBean.Send(account,type))
                .flatMap {
                    if(it.code== ErrorStatus.SUCCESS){
                        return@flatMap  Observable.just(it)
                    }else{
                        return@flatMap Observable.error<GetVfBean>(ApiException(it.message,it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }


}