package com.epro.mall.mvp.contract

import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface MemberCentreContract {

    interface View : IBaseView {
        fun onMemberCentreSuccess()

    }


    interface Presenter : IPresenter<View> {
        fun MemberCentre(type: String)
    }
}