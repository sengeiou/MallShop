package com.mike.baselib.net.exception

import com.google.gson.JsonParseException
import com.mike.baselib.R
import com.tencent.bugly.crashreport.CrashReport
import com.mike.baselib.listener.TokenExpiredEvent
import com.mike.baselib.utils.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.appcompat.v7.Appcompat

import org.json.JSONException

import java.net.ConnectException

import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

/**
 * Created by xuhao on 2017/12/5.
 * desc: 异常处理类
 */

class ExceptionHandle {

    companion object {
        var errorCode = ErrorStatus.UNKNOWN_ERROR
        var errorMsg = AppContext.getInstance().getString(R.string.request_failed_try_again_later)
        val logTools = LogTools(this.javaClass.simpleName)

        fun handleException(e: Throwable): String {
            if (!NetworkUtil.isNetworkAvailable(AppContext.getInstance().context)) {
                //throw NoNetworkException("请检查网路是否正常",ErrorStatus.NETWORK_ERROR)
                errorMsg = AppContext.getInstance().getString(R.string.pls_check_net_normal)
                errorCode = ErrorStatus.NETWORK_ERROR
                return errorMsg
            }
            e.printStackTrace()
            CrashReport.postCatchedException(e) // bugly会将这个throwable上报

            if (!AppConfig.isPublish) { //Debug调试
                if (e is SocketTimeoutException) {//网络超时
                    logTools.d(AppContext.getInstance().getString(R.string.net_connect_exception) + e.message)
                    errorMsg = AppContext.getInstance().getString(R.string.net_connect_exception_null)
                    errorCode = ErrorStatus.NETWORK_ERROR
                } else if (e is ConnectException) { //均视为网络错误
                    logTools.e(AppContext.getInstance().getString(R.string.net_connect_exception) + e.message)
                    errorMsg = AppContext.getInstance().getString(R.string.net_connect_exception_null)
                    errorCode = ErrorStatus.NETWORK_ERROR
                } else if (e is JsonParseException
                        || e is JSONException
                        || e is ParseException) {   //均视为解析错误
                    logTools.e(AppContext.getInstance().getString(R.string.data_parse_exception) + e.message)
                    errorMsg = AppContext.getInstance().getString(R.string.data_parse_exception_null)
                    errorCode = ErrorStatus.SERVER_ERROR
                } else if (e is ApiException) {//服务器返回的错误信息
                    logTools.e(AppContext.getInstance().getString(R.string.the_server_returned_an_error) + e.message)
                    errorMsg = ("" + e.message).replace("java.lang.Throwable: ", "")
                    errorCode = e.code!!
                    if (errorCode == ErrorStatus.TOKEN_EXPIERD || errorCode == ErrorStatus.TOKEN_ERROR || errorCode == ErrorStatus.PASSWORD_ERROR) {//token失效
//                    AppBusManager.setToken("")
//                    EventBus.getDefault().post(TokenExpiredEvent())
                    }
                } else if (e is UnknownHostException) {
                    logTools.e(AppContext.getInstance().getString(R.string.net_connect_exception) + e.message)
                    errorMsg = AppContext.getInstance().getString(R.string.net_connect_exception_null)
                    errorCode = ErrorStatus.NETWORK_ERROR
                } else if (e is IllegalArgumentException) {
                    errorMsg = AppContext.getInstance().getString(R.string.parameter_error)
                    errorMsg = e.message!!
                    errorCode = ErrorStatus.SERVER_ERROR
                } else if (e is PayException) {
                    errorMsg = ("" + e.message).replace("java.lang.Throwable: ", "")
                    errorCode = e.code
                } else {//未知错误
                    try {
                        logTools.e("错误: " + e.message)
                    } catch (e1: Exception) {
                        logTools.e("未知错误Debug调试 ")
                    }
                    errorMsg = AppContext.getInstance().getString(R.string.unknown_mistake)
                    errorCode = ErrorStatus.UNKNOWN_ERROR
                }
            } else { //发布环境
                if (e is ApiException) {//服务器返回的错误信息
                    logTools.e(AppContext.getInstance().getString(R.string.the_server_returned_an_error) + e.message)
                    errorMsg = e.message.toString().replace("java.lang.Throwable: ", "")
                    errorCode = e.code!!
                    if(e.code==ErrorStatus.TOKEN_EXPIERD){
                        errorMsg=""
                    }
                } else if (e is PayException) {
                    logTools.e(AppContext.getInstance().getString(R.string.the_server_returned_an_error) + e.message)
                    errorMsg = e.message.toString().replace("java.lang.Throwable: ", "")
                    errorCode = e.code!!
                }else {
                    errorMsg = "系统服务繁忙,请稍后再试"
                    errorCode = ErrorStatus.UNKNOWN_ERROR
                }
            }
            return errorMsg
        }
    }


}
