package com.epro.mall.mvp.model

import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.mike.baselib.listener.RetryWithDelay
import com.epro.mall.mvp.model.bean.CreateScanBuyOrderBean
import com.epro.mall.mvp.model.bean.GetGoodsByBarcodeBean
import com.epro.mall.mvp.model.bean.GetShopInfoBean
import io.reactivex.Observable
import retrofit2.http.Body

class ScanBuyCartModel : BaseModel() {
    fun getGoodsByBarcode(shopId: String, productBarCode:String): Observable<GetGoodsByBarcodeBean> {
        val send=GetGoodsByBarcodeBean.Send(shopId,productBarCode)
        return getApiSevice().getGoodsByBarcode(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetGoodsByBarcodeBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun getShopInfo(shopId: String): Observable<GetShopInfoBean> {
        return getApiSevice().getShopInfo(shopId)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetShopInfoBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

}