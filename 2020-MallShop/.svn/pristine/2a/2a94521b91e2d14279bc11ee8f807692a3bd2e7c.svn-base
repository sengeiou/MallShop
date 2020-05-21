package com.epro.mall.mvp.model

import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.mike.baselib.listener.RetryWithDelay
import com.epro.mall.mvp.model.bean.GetResultListBean
import com.epro.mall.mvp.model.bean.ShopGoodsCategory
import io.reactivex.Observable

class ShopCategoryModel : BaseModel() {
    fun getShopGoodsCategoryList(shopId:String): Observable<GetResultListBean<ShopGoodsCategory>> {
        val send=GetResultListBean.ShopGoodsCategorySend(shopId)
        return getApiSevice().getShopGoodsCategoryList(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetResultListBean<ShopGoodsCategory>>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

}