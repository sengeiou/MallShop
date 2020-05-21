package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.AccountAssociationBean
import com.epro.mall.mvp.model.bean.AssociationAccountListBean
import com.epro.mall.mvp.model.bean.UnbindAccountBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface AccountAssociationContract  {

    interface View:IBaseView{
        fun onAssociationSuccess(result:AccountAssociationBean.Result)

        fun unBindAssociation(result:UnbindAccountBean)

        fun associationAccountListSuccess(result: List<AssociationAccountListBean.Result>)
    }

    interface Presenter:IPresenter<View>{
        fun associationAccount(type: String,providerId:String,providerType:String,name:String)

        fun unBindAccount(type: String,id:String)

        //绑定账号列表
        fun associationAccountList(type: String)
    }

}