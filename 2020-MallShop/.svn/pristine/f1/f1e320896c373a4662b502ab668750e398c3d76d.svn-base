package com.epro.comp.im.mvp.model

import com.mike.baselib.base.IBaseModel
import com.mike.baselib.utils.LogTools
import com.epro.comp.im.CompIM
import com.epro.comp.im.api.ApiService


open class BaseModel: IBaseModel {
    val logTools= LogTools("BaseModel_"+this.javaClass.simpleName)
    fun getApiSevice(): ApiService {
        return CompIM.getApiService()
    }
}

