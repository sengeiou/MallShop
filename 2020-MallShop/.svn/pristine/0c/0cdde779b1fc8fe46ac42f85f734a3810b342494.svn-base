package com.epro.mall.ui.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.View
import com.epro.mall.R
import com.epro.mall.ui.MainActivity
import com.epro.mall.ui.login.LoginActivity
import com.epro.mall.ui.view.CommonDialog
import com.mike.baselib.activity.BaseSimpleActivity
import com.mike.baselib.utils.ext_removeFragment
import kotlinx.android.synthetic.main.activity_address_list.*


/**
 * 提示token过期,登录过期等弹窗
 */

class AlertActivity : BaseSimpleActivity(){
    override fun layoutContentId(): Int {
        return R.layout.activity_base_simple
    }

    companion object {
        const val TAG = "AlertActivity"
        const val ALERT="alert"
        fun launch(context: Context) {
            launchWithStr(context, "")
        }

        fun launchWithStr(context: Context, str: String) {
            context.startActivity(Intent(context, AlertActivity::class.java).putExtra(ALERT, str))
        }
    }

    override fun initData() {
    }

    override fun initView() {
        super.initView()
        showAlertDialog(intent.getStringExtra(ALERT)!!)
    }

    override fun initListener() {
    }
    private fun showAlertDialog(alert: String) {
        CommonDialog.Builder(this)
                .setContent(alert)
                .setOnConfirmListener(object : CommonDialog.OnConfirmListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                        startActivityForResult(Intent(this@AlertActivity, LoginActivity::class.java), 0)
                        finish()
                    }
                })
                .setOnCancelListener(object : CommonDialog.OnCancelListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                        finish()
                    }
                })
                .create()
                .show()
    }

}


