package com.epro.mall.mvp.contract

import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface PhoneServiceTermsContract {

    interface View : IBaseView {
        fun onPhoneServiceTermsSuccess()

    }


    interface Presenter : IPresenter<View> {
        fun PhoneServiceTerms(type: String)
    }
}