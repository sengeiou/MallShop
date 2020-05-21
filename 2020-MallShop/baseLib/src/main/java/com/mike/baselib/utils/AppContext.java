package com.mike.baselib.utils;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

//import com.squareup.leakcanary.LeakCanary;
//import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

import org.litepal.LitePal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 替代application 使其能在各层被调用
 * Created by Mike on 2016/10/11.
 */
public class AppContext {

    private static AppContext appContext;
    private Handler mainHandler;
  //  private RefWatcher refWatcher=RefWatcher.DISABLED;
    public Context getContext() {
        return context;
    }

//    public RefWatcher getRefWatcher() {
//        return refWatcher;
//    }

    private Context context;

    public static AppContext getInstance() {
        if (appContext == null) {
            synchronized (AppContext.class) {
                if (appContext == null) {
                    appContext = new AppContext();
                }
            }
        }
        return appContext;
    }


    public void init(Application application) {
        this.context = application;
        this.mainHandler = new Handler(Looper.getMainLooper());
        context=application;
        AppConfig.Companion.init();
        DisplayManager.INSTANCE.init(context);
        //refWatcher=setupLeakCanary();
        disableAPIDialog();
        AppBusManager.Companion.setDeviceName();
        //Bugly 初始化
        CrashReport.initCrashReport(context,Constants.Companion.getBUGLY_APPID(),true);
       // Bugly.init(context, Constants.Companion.getBUGLY_APPID(), true);
        if (!TextUtils.isEmpty(AppBusManager.Companion.getUsername())) {
            CrashReport.setUserId(AppBusManager.Companion.getUsername());
        }
        LitePal.initialize(application);
    }

    public Handler getMainHandler() {
        return mainHandler;
    }

    public String getString(int id) {
        return context.getResources().getString(id);
    }

    public Resources getResources() {
        return context.getResources();
    }

//    private RefWatcher setupLeakCanary(){
//        return LeakCanary.isInAnalyzerProcess(context)?
//                RefWatcher.DISABLED: LeakCanary.install((Application) context);
//    }
//


    private void disableAPIDialog() {
        if (Build.VERSION.SDK_INT >= 28) {
            try {
                Class clazz = Class.forName("android.app.ActivityThread");
                Method currentActivityThread = clazz.getDeclaredMethod("currentActivityThread");
                currentActivityThread.setAccessible(true);
                Object activityThread = currentActivityThread.invoke((Object)null);
                Field mHiddenApiWarningShown = clazz.getDeclaredField("mHiddenApiWarningShown");
                mHiddenApiWarningShown.setAccessible(true);
                mHiddenApiWarningShown.setBoolean(activityThread, true);
            } catch (Exception var5) {
                var5.printStackTrace();
            }

        }
    }
    public int appStatus = Constants.STATUS_RECYCLE;//APP状态 初始值被系统回收

    //得到状态
    public int getAppStatus() {
        return appStatus;
    }
    //设置状态
    public void setAppStatus(int appStatus) {
        this.appStatus = appStatus;
    }


    public boolean isInstalledVirtualLocationPackage() {
        try {
            List<String> virtualLocationPackages = new ArrayList<>();
            //分身App列表
            virtualLocationPackages.add("com.tencent.mm");
            Process process = Runtime.getRuntime().exec("pm list package -3");
            BufferedReader bis = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = bis.readLine()) != null) {
                for (String packageName : virtualLocationPackages) {
                    if (packageName.equals(line.substring(8, line.length()))) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
        }
        return false;
    }
}
