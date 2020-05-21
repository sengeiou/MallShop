package com.epro.comp.im;

import android.app.Activity;
import android.util.Log;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCUtil;
import com.billy.cc.core.component.IComponent;
import com.epro.comp.im.api.ApiService;
import com.epro.comp.im.utils.IMBusManager;
import com.mike.baselib.net.RetrofitManager;
import com.mike.baselib.utils.AppConfig;
import com.mike.baselib.utils.LogTools;

public class CompIM implements IComponent {
    private static ApiService apiService;

    public static ApiService getApiService() {
        if (apiService == null) {
            AppConfig.Companion.init();
            apiService = RetrofitManager.INSTANCE.getRetrofit().create(ApiService.class);
        }
        return apiService;
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();
        switch (actionName) {
            case "showActivityChat":
                openActivity(cc);
                break;
            default:
                //这个逻辑分支上没有调用CC.sendCCResult(...),是一种错误的示例
                //并且方法的返回值为false，代表不会异步调用CC.sendCCResult(...)
                //在LocalCCInterceptor中将会返回错误码为-10的CCResult
                break;
        }
        return true;
    }

    private void openActivity(CC cc) {
        LogTools.debug("ccThread", Thread.currentThread().getName());
        LogTools.debug("ccThread", cc.getContext());
        LogTools.debug("ccThread", IMBusManager.Companion.getProcessName(cc.getContext()));
        LogTools.debug("ccThread", cc.getContext().getClassLoader());
        LogTools.debug("ccThread", this.getClass().getClassLoader());

        try {
            CCUtil.navigateTo(cc, (Class<? extends Activity>) Class.forName("com.epro.comp.im.ui.activity.ChatActivity"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


}
