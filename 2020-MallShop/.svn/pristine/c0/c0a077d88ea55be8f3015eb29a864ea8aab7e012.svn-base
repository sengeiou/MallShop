package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.Address
import com.epro.mall.mvp.model.bean.AddressListBean
import com.epro.mall.mvp.model.bean.AddressListItem
import com.epro.mall.mvp.model.bean.DeleteAddressBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter
import com.mike.baselib.base.ListView

interface AddressListContract {

    interface View : ListView<Address> {
        fun onAddressDeleteSuccess(result: DeleteAddressBean)
    }


    interface Presenter : IPresenter<View> {
        fun getAddressList(type: String, parentId: String)
        fun deleteAddress(type: String, id: String)
    }
}