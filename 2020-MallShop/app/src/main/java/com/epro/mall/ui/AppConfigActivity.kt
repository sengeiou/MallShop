package com.epro.mall.ui

import android.content.Context
import android.content.Intent
import android.widget.RadioGroup
import com.epro.mall.R
import com.epro.mall.utils.MallBusManager
import com.mike.baselib.activity.BaseTitleBarSimpleActivity
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.Constants
import kotlinx.android.synthetic.main.activity_app_config.*

class AppConfigActivity:BaseTitleBarSimpleActivity(){
    companion object {
        const val TAG = "AppConfigActivity"
        fun launch(context: Context) {
            launchWithStr(context, "")
        }

        fun launchWithStr(context: Context, str: String) {
            context.startActivity(Intent(context, AppConfigActivity::class.java).putExtra(TAG, str))
        }
    }
    override fun layoutContentId(): Int {
      return R.layout.activity_app_config
    }

    override fun initView() {
        super.initView()
        getTitleView().setText(getString(R.string.configuration))
        radioGroup.check(MallBusManager.getDevCheckId())

    }

    override fun initData() {
    }

    override fun initListener() {
        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.rbTest->  AppBusManager.setDev(Constants.DV_TEST)
                R.id.rbPreRelease-> AppBusManager.setDev(Constants.DV_PRE_RELEASE);
                R.id.rbRelease-> AppBusManager.setDev(Constants.DV_RELEASE)
            }
            AppBusManager.setToken("")
            System.exit(0)
        })
    }

}