package com.epro.mall.mvp.model

import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.mike.baselib.listener.RetryWithDelay
import com.epro.mall.mvp.model.bean.AddressListBean
import com.epro.mall.mvp.model.bean.DeleteAddressBean
import io.reactivex.Observable

class AddressListModel : BaseModel() {
    fun getAddressList(parentId:String): Observable<AddressListBean> {

        return getApiSevice().getAddressList(parentId)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<AddressListBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun deleteAddress(id:String):Observable<DeleteAddressBean>{

        return getApiSevice().deleteAddress(id)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS){
                        return@flatMap Observable.just(it)
                    }else{
                        return@flatMap Observable.error<DeleteAddressBean>(ApiException(it.message,it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }
}