package com.epro.mall

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.mike.baselib.listener.TokenExpiredEvent
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.AppUtils
import org.greenrobot.eventbus.EventBus

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("YB", "监听")
        val appPackage = "com.epro.mall"
        if (intent?.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
            var packageName = intent?.getData()?.getSchemeSpecificPart()
            Log.i("YB", "安装成功"+packageName)
        }
        if (intent?.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
            var packageName = intent?.getData()?.getSchemeSpecificPart()
            Log.i("YB", "卸载成功 "+packageName+" versionName : "+AppUtils.getVerName(context!!)+"  Token : "+AppBusManager.getToken())
        }
        if (intent?.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {
            var packageName = intent?.getData()?.getSchemeSpecificPart()
            Log.i("YB", "替换成功"+packageName)
            if (appPackage.equals(packageName)) {
                Log.i("YB", "替换成功 22 "+packageName+" versionName : "+AppUtils.getVerName(context!!)+"  Token : "+AppBusManager.getToken())
                AppBusManager.setToken("")
                EventBus.getDefault().post(TokenExpiredEvent())
            }
        }
    }
}