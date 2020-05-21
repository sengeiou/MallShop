package com.epro.mall.mvp.model

import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.mike.baselib.listener.RetryWithDelay
import com.epro.mall.mvp.model.bean.GetUserVfBean
import com.epro.mall.mvp.model.bean.GetVfBean
import com.epro.mall.mvp.model.bean.ModifyPasswordBean
import com.epro.mall.mvp.model.bean.MyInfoBean
import io.reactivex.Observable

class ModifyPasswordModel :BaseModel(){
    fun modifyPassword(authType:String,account:String,code:String, password:String): Observable<ModifyPasswordBean> {
        return getApiSevice().modifyPassword(ModifyPasswordBean.Send(authType, account, code, password))
                .flatMap {
                    if(it.code== ErrorStatus.SUCCESS){
                        return@flatMap  Observable.just(it)
                    }else{
                        return@flatMap Observable.error<ModifyPasswordBean>(ApiException(it.message,it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

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

    /**
     * 获取验证码
     */
    fun getVfCode(authType:String,account:String,type:Int): Observable<GetUserVfBean> {
        return getApiSevice().getUserVfcode(GetUserVfBean.Send(authType,account,type))
                .flatMap {
                    if(it.code== ErrorStatus.SUCCESS){
                        return@flatMap  Observable.just(it)
                    }else{
                        return@flatMap Observable.error<GetUserVfBean>(ApiException(it.message,it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }
}
