package com.mike.baselib.net.exception

/**
 * Created by xuhao on 2017/12/5.
 * desc:
 */
object ErrorStatus {
    /**
     * 响应成功
     */
    @JvmField
    val SUCCESS = 200

    @JvmField
    val SUCCESS_MSG="ok"

    /**
     * 响应失败
     */
    @JvmField
    val FAILED = -200

    /**
     * 未知错误
     */
    @JvmField
    val UNKNOWN_ERROR = 1002

    /**
     * 系统错误
     */
    @JvmField
    val SERVER_ERROR = -66

    /**
     * 网络连接超时
     */
    @JvmField
    val NETWORK_ERROR = 1004

    /**
     * API解析异常（或者第三方数据结构更改）等其他异常
     */
    @JvmField
    val API_ERROR = 1005


    /**
     * token错误
     */
    @JvmField
    val TOKEN_ERROR = -12

    /**
     * token过期
     */
    @JvmField
    val TOKEN_EXPIERD =700

    /**
     * 账户或密码错误
     */
    @JvmField
    val PASSWORD_ERROR = -16

    @JvmField
    val MULTI_DEVICE_ERROR =1000003

    @JvmField
    val ACCOUNT_FROZEN =1000011
}