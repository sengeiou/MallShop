package com.epro.mall.mvp.model

import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.mike.baselib.listener.RetryWithDelay
import com.epro.mall.mvp.model.bean.GetShopGoodsListBean
import com.epro.mall.mvp.model.bean.GetShopHomeViewBean
import com.epro.mall.mvp.model.bean.ShopHomeBean
import io.reactivex.Observable

class ShopHomeModel : BaseModel() {
    fun getShopGoodsListHot(shopId: String): Observable<GetShopGoodsListBean> {
        val send = GetShopGoodsListBean.Send(shopId)
        return getApiSevice().getShopGoodsListHot(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetShopGoodsListBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun getShopGoodsListRecommend(shopId: String): Observable<GetShopGoodsListBean> {
        val send = GetShopGoodsListBean.Send(shopId)
        return getApiSevice().getShopGoodsListRecommend(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetShopGoodsListBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

}