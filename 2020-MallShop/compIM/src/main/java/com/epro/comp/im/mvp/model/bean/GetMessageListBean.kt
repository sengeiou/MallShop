package com.epro.comp.im.mvp.model.bean

data class GetMessageListBean<T:MsgBody>(override val code: Int, override val message: String, override val result: ArrayList<ChatMessage<T>>) : BaseBean<ArrayList<ChatMessage<T>>>
