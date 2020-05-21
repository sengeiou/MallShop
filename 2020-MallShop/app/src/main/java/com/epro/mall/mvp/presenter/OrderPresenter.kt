package com.epro.mall.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.epro.mall.mvp.contract.OrderContract
import com.epro.mall.mvp.model.OrderModel

class OrderPresenter: BasePresenter< OrderContract.View>() ,OrderContract.Presenter{
    private val model by lazy { OrderModel() }
}