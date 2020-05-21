package com.epro.mall.mvp.contract

import com.epro.mall.mvp.model.bean.MyInfoBean
import com.epro.mall.mvp.model.bean.UpdateImageBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter
import java.io.File

interface MyInfoContract {

    interface View : IBaseView {
        fun onMyInfoSuccess(result: MyInfoBean.Result)
        fun onUpdateImageSucess(result: UpdateImageBean)
        fun onModifyImageSuccess()
    }


    interface Presenter : IPresenter<View> {
        fun MyInfo(type: String)
        //上传图片
        fun updateImage(type:String, image: File, isCreateThumb:Int)
        fun modifyImage(type: String,image:String)
    }
}