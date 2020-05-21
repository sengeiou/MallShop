package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.AddNewAddressBean
import com.epro.mall.mvp.model.bean.UpdateAddressBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface EditAddressContract {

    interface View : IBaseView {
        fun onEditAddressSuccess(result:UpdateAddressBean)
        fun onAddNewAddressSuccess(result: AddNewAddressBean)
    }


    interface Presenter : IPresenter<View> {
        fun editAddress(type: String,id:String,receive:String,province:String, city:String, area:String,
                        location:String,address:String, mobile:String,isDefult:Int,longitude:String,latitude:String)
        fun addNewAddress(type: String,receive:String,province:String,
                          city:String,area:String,location:String,address:String, mobile:String,isDefult:Int,longitude:String,latitude:String)
    }
}