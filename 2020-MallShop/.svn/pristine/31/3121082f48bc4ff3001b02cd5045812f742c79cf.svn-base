package com.epro.mall.mvp.presenter

import com.mike.baselib.base.ListPresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.mall.mvp.contract.ShopNewGoodsListContract
import com.epro.mall.mvp.model.ShopNewGoodsListModel
import com.epro.mall.mvp.model.bean.GetShopNewGoodsListBean
import com.epro.mall.mvp.model.bean.Goods
import com.epro.mall.utils.MallConst
import com.mike.baselib.net.exception.ErrorStatus

class ShopNewGoodsListPresenter : ListPresenter<GetShopNewGoodsListBean.NewGoods, ShopNewGoodsListContract.View>(), ShopNewGoodsListContract.Presenter {

    private val model by lazy { ShopNewGoodsListModel() }
   fun getShopNewGoodsList(shopId:String,type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
       val disposable = model.getShopNewGoodsList(shopId)
               .subscribe({ bean ->
                   mRootView?.getListDataSuccess(bean.result, type)
                   mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
               }, { throwable ->
                   //处理异常
                   ExceptionHandle.handleException(throwable)
                   mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                   mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
               })
       addSubscription(disposable)
    }

}
