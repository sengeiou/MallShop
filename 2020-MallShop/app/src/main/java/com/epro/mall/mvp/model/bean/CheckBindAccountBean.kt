package com.epro.mall.mvp.model.bean

data class CheckBindAccountBean(override val code: Int,
                                override val message: String,
                                override val result: Result):BaseBean<CheckBindAccountBean.Result>{
    data class Result(val code: Int, val message: String,val result:Any)
    data class Send(val account: String, val code: String, val authType: String)
}