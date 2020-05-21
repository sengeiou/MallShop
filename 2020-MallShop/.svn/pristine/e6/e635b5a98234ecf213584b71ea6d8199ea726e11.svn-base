package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.AboutBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface AboutContract {

    interface  View:IBaseView{
        fun onAboutSuccess(result:AboutBean.Result)
    }

    interface Presenter:IPresenter<View>{
        fun aboutApp(type: String,authType:String)
    }

}