package com.mike.baselib.base

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.mike.baselib.utils.AppContext
import com.mike.baselib.utils.LocaleManager
import com.mike.baselib.utils.LogTools

open class BaseApplication : MultiDexApplication(){

    val logTools = LogTools("BaseApplication_" + this.javaClass.simpleName)

    companion object {
        var localeManager: LocaleManager? = null
    }

    override fun onCreate() {
        super.onCreate()
        // registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)
        AppContext.getInstance().init(this)
        MultiDex.install(this)


    }

    override fun attachBaseContext(base: Context?) {
        localeManager = LocaleManager(base)
        super.attachBaseContext(localeManager?.setLocale(base))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localeManager?.setLocale(this)
    }

    private val mActivityLifecycleCallbacks = object : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            logTools.d("onCreated: " + activity.componentName.className)
        }

        override fun onActivityStarted(activity: Activity) {
            logTools.d("onStart: " + activity.componentName.className)
        }

        override fun onActivityResumed(activity: Activity) {

        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {

        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {

        }
    }

}
