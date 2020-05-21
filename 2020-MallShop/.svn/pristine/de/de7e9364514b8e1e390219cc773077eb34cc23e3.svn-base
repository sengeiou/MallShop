package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.City
import com.epro.mall.mvp.model.cn.CNPinyin
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface SelectCityContract {

    interface View : IBaseView {
        fun onGetCitiesSuccess(cnpCityList:ArrayList<CNPinyin<City>>)
        fun onGetCitiesSuccess()

    }


    interface Presenter : IPresenter<View> {
        fun getCities(type: String)
    }
}