package com.epro.mall.mvp.model

import android.bluetooth.le.AdvertiseData
import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.mike.baselib.listener.RetryWithDelay
import com.epro.mall.mvp.model.bean.AddToCartBean
import com.epro.mall.mvp.model.bean.GetGoodsDetailBean
import com.epro.mall.mvp.model.bean.GetShopInfoBean
import com.epro.mall.mvp.model.bean.Product
import io.reactivex.Observable

class GoodsDetailModel : BaseModel() {
    fun getGoodsDetail(goodsId: String): Observable<GetGoodsDetailBean> {
        return getApiSevice().getGoodsDetail(goodsId)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetGoodsDetailBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun addToCart(productId: String, number: Int): Observable<AddToCartBean> {
        val pList = ArrayList<AddToCartBean.Product>()
        val cp = AddToCartBean.Product(productId, number)
        pList.add(cp)
        val send = AddToCartBean.Send(pList)
        return getApiSevice().addToCart(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<AddToCartBean>(ApiException(it.message, it.code))
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