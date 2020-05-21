package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.AboutBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface UpdateAppContract {

    interface View : IBaseView {
        fun onUpdateAppSuccess()
        fun onAboutSuccess(result: AboutBean.Result)

    }


    interface Presenter : IPresenter<View> {
        fun UpdateApp(type: String)
        fun aboutApp(type: String,authType:String)
    }
}