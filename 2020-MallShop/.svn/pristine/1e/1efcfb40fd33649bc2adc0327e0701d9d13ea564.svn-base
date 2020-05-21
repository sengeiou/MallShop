package com.epro.mall.mvp.model

import com.mike.baselib.base.IBaseModel
import com.mike.baselib.utils.LogTools
import com.epro.mall.MallApplication
import com.epro.mall.api.ApiService

open class BaseModel: IBaseModel {
    val logTools= LogTools("BaseModel_"+this.javaClass.simpleName)
    fun getApiSevice(): ApiService {
        return MallApplication.apiService
    }
}

