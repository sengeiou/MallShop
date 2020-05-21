package com.mike.baselib.bean

data class ModifyPasswordSend(val authType:String, var account:String, val code: String, var password:String)