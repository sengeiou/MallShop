package com.mike.baselib.net.exception

/**
 * Created by xuhao on 2017/12/5.
 * desc:
 */
class ApiException : RuntimeException {

     var code: Int = 0


    constructor(throwable: Throwable, code: Int) : super(throwable) {
        this.code = code
    }

    constructor(message: String) : super(Throwable(message))
    constructor(message: String, code:Int) : super(Throwable(message)){
        this.code=code
    }
}