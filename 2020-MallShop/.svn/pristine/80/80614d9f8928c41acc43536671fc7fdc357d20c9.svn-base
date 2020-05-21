package com.mike.baselib.utils

import android.content.Context
import org.jetbrains.anko.longToast

class ToastUtil {
    companion object {

        fun showToast(msgResId: Int) {
            showToast(AppContext.getInstance().context.getString(msgResId))
        }

        fun showToast(msg: String) {
            showToast(AppContext.getInstance().context, msg)
        }

        fun showToast(context: Context, msgResId: Int) {
            showToast(context, context.getString(msgResId))
        }

        fun showToast(context: Context, msg: String) {
            context.toast(msg)
        }

        fun showLongToast(msgResId: Int) {
            AppContext.getInstance().context.longToast(msgResId)
        }

        fun showLongToast(msg: String) {
            AppContext.getInstance().context.longToast(msg)
        }
    }

}