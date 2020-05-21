package com.mike.baselib.bean

data class LoginResponse( val code: Int,  val message: String,  val result:Result){
    data class Result(val randomKey: String, val token: String)
}