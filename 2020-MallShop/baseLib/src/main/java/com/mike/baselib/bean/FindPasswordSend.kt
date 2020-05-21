package com.mike.baselib.bean

data class FindPasswordSend(var account:String, var password:String, var passwordConfirm:String, val code:String, val authType:String)