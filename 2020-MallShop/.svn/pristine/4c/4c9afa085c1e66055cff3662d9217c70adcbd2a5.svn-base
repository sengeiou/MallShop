package com.epro.mall.mvp.model

import com.mike.baselib.listener.RetryWithDelay
import com.epro.mall.mvp.model.bean.*
import com.mike.baselib.net.RetrofitManager
import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.mike.baselib.utils.Constants
import io.reactivex.Observable

class HomeModel : BaseModel() {
    fun getCartGoodsCount(): Observable<GetCartGoodsCountBean> {
        return getApiSevice().getCartGoodsCount()
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetCartGoodsCountBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun getHomeShopList(latitude: String, longitude: String,isCache:Boolean=true): Observable<GetResultListBean<HomeShop>> {
        return getApiSevice().getHomeShopList(GetResultListBean.GetHomeShopListSend(latitude,longitude),
                if(isCache) Constants.ONLINE_CACHETIME.toString() else 0.toString(),
                if(isCache) Constants.OFFLINE_CACHETIME.toString() else 0.toString())
                .flatMap {
                    logTools.d(it.code)
                    logTools.d(it.message)
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetResultListBean<HomeShop>>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun getHomeBannerList(type: Int,isCache:Boolean=true): Observable<GetResultListBean<AdBanner>> {
        return getApiSevice().getHomeBannerList(0,
                if(isCache) Constants.ONLINE_CACHETIME.toString() else 0.toString(),
                if(isCache) Constants.OFFLINE_CACHETIME.toString() else 0.toString())
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetResultListBean<AdBanner>>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }


}
