package com.epro.mall.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.epro.mall.mvp.contract.HomeContract
import com.epro.mall.mvp.model.HomeModel
import com.epro.mall.mvp.model.bean.HomeShop
import com.epro.mall.utils.MallConst
import com.mike.baselib.base.ListPresenter
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.net.exception.ExceptionHandle

class HomePresenter : ListPresenter<HomeShop, HomeContract.View>(), HomeContract.Presenter {
    override fun getCartGoodsCount(type: String) {
        checkViewAttached()
        //mRootView?.showLoading(type)
        val disposable = model.getCartGoodsCount()
                .subscribe({ bean ->
                    mRootView?.onGetCartGoodsCountSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS, type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

    fun getBannerList(bannerType: Int,isCache:Boolean=true, type: String=MallConst.GET_HOME_BANNER_LIST) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getHomeBannerList(bannerType,isCache)
                .subscribe({ bean ->
                    mRootView?.onGetBannerListSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS, type)
                }, { throwable ->
                    //处理异常

                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

    fun getHomeShopList(latitude: String, longitude: String,isCache:Boolean=true, type: String=MallConst.GET_HOME_SHOP_LIST) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getHomeShopList(latitude, longitude,isCache)
                .subscribe({ bean ->
                    mRootView?.getListDataSuccess(bean.result, type)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS, type)
                }, { throwable ->
                    //处理异常
                    logTools.d(throwable.message)
                    logTools.d(throwable.toString())
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

    private val model by lazy { HomeModel() }
}