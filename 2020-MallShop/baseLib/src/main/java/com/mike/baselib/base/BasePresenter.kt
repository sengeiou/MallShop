package com.mike.baselib.base

import com.mike.baselib.utils.AppContext
import com.mike.baselib.utils.LogTools
import com.mike.baselib.utils.toast
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BasePresenter<T : IBaseView> : IPresenter<T> {

    val logTools=LogTools("BasePresenter_"+this.javaClass.simpleName)

    var mRootView: T? = null
        private set

    private var compositeDisposable = CompositeDisposable()

    override fun attachView(mRootView: T) {
        this.mRootView = mRootView
    }

    override fun detachView() {
        mRootView = null

         //保证activity结束时取消所有正在执行的订阅
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }

    }

    private val isViewAttached: Boolean
        get() = mRootView != null

    fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }

    fun addSubscription(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun clearSubscription(){
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }

    fun toast(message :CharSequence){
        AppContext.getInstance().context.toast(message)
    }
    fun toast(message: Int){
        AppContext.getInstance().context.toast(message)
    }

    private class MvpViewNotAttachedException internal constructor() : RuntimeException("Please call IPresenter.attachView(IBaseView) before" + " requesting data to the IPresenter")
}