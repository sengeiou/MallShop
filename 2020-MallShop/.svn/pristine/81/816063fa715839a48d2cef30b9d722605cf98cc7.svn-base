package com.epro.mall.mvp.model

import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.mike.baselib.listener.RetryWithDelay
import com.epro.mall.mvp.model.bean.BindAccountBean
import com.epro.mall.mvp.model.bean.GetVfBean
import io.reactivex.Observable

class BindAccountModel : BaseModel() {
    fun getBindAccount(account:String,password:String,code:String): Observable<BindAccountBean> {
        val send=BindAccountBean.Send(account,password,code)
        return getApiSevice().bindAccount(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<BindAccountBean>(ApiException(it.message, it.code))
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