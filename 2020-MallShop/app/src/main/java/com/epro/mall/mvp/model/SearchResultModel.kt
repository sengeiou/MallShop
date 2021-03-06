package com.epro.mall.mvp.model

import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.mike.baselib.listener.RetryWithDelay
import com.epro.mall.mvp.model.bean.SearchAllBean
import com.epro.mall.mvp.model.bean.SearchInShopBean
import io.reactivex.Observable

class SearchResultModel : BaseModel() {
    fun searchAll(keyword: String): Observable<SearchAllBean> {
        val send = SearchAllBean.Send(keyword)
        return getApiSevice().searchAll(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<SearchAllBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun searchInShop(shopId: String, keyword: String): Observable<SearchInShopBean> {
        val send = SearchInShopBean.Send(shopId, keyword)
        return getApiSevice().searchInShop(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<SearchInShopBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

}