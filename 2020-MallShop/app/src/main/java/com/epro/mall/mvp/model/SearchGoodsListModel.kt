package com.epro.mall.mvp.model

import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.mike.baselib.listener.RetryWithDelay
import com.epro.mall.mvp.model.bean.SearchGoodsListBean
import io.reactivex.Observable

class SearchGoodsListModel : BaseModel() {
    fun searchGoodsList(keyword:String,page:Int,shopId:String?=null): Observable<SearchGoodsListBean> {
        val p=SearchGoodsListBean.PageList(page)
        val send=SearchGoodsListBean.Send(keyword,p,shopId)
        return getApiSevice().searchGoodsList(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<SearchGoodsListBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }


}