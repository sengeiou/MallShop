package com.epro.comp.im.utils

import android.app.Activity
import android.content.Intent
import android.view.View

class ActivityUtil {
    companion object {
        fun launch2AppShopDetail(activity: Activity, shopId: String) {
            activity.startActivity(Intent(activity, Class.forName("com.epro.mall.ui.activity.ShopDetailActivity")).putExtra("shopId", shopId))
        }

        fun launch2AppLogin(activity: Activity) {
            activity.startActivity(Intent(activity, Class.forName("com.epro.mall.ui.login.LoginActivity")))
        }
    }
}