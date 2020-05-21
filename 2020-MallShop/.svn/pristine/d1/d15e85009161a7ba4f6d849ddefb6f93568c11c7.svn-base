package com.epro.mall.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.mall.mvp.contract.CartContract
import com.epro.mall.mvp.model.CartModel
import com.epro.mall.mvp.model.bean.ShopCart
import com.mike.baselib.net.exception.ErrorStatus

class CartPresenter : BasePresenter<CartContract.View>(), CartContract.Presenter {

    private val model by lazy { CartModel() }

    override fun getCartGoodsList(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getCartGoodsList()
                .subscribe({ bean ->
                    val deleteList=ArrayList<ShopCart>()
                    //无效的数组
                    for(s in bean.result){
                        if(s.isValid==0&&s.products.isEmpty()){
                            deleteList.add(s)
                        }
                    }
                    bean.result.removeAll(deleteList)
                    mRootView?.onGetCartGoodsListSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    override fun deleteCartGoods(productId: String, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.deleteCartGoods(productId)
                .subscribe({ bean ->
                    mRootView?.onDeleteCartGoodsSuccess()
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    override fun modifyCartGoods(productId: String, number: Int, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.modifyCartGoods(productId, number)
                .subscribe({ bean ->
                    mRootView?.onModifyCartGoodsSuccess()
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