package com.epro.mall.mvp.model.bean

data class DeleteCartGoodsBean(override val code: Int, override val message: String, override val result: Any?) : BaseBean<Any?> {
    data class Send(val productIds:ArrayList<String>)
}
