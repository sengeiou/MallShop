package com.epro.comp.im.mvp.model.bean


data class GetChatRecordListBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<GetChatRecordListBean.Result> {
    data class Result(val records: List<ChatRecord>)
}
