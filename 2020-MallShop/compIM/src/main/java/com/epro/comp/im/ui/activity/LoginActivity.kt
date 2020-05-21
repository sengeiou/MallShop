package com.epro.comp.im.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import com.billy.cc.core.component.CC
import com.epro.comp.im.R
import com.epro.comp.im.mvp.contract.LoginContract
import com.epro.comp.im.mvp.model.bean.LoginBean
import com.epro.comp.im.mvp.model.bean.User
import com.epro.comp.im.mvp.presenter.LoginPresenter
import com.mike.baselib.activity.BaseTitleBarCustomActivity
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.LogTools
import com.mike.baselib.utils.ToastUtil
import com.mike.baselib.utils.toast
import kotlinx.android.synthetic.main.comp_im_activity_login.*
import org.jetbrains.anko.startActivity


class LoginActivity : BaseTitleBarCustomActivity<LoginContract.View, LoginPresenter>(), LoginContract.View, View.OnClickListener {
    override fun loginSuccess(user: User) {
       // startActivity<SplashActivity>()
        toast("登录成功")
        AppBusManager.setUsername(etUsername.text.toString())
        AppBusManager.setPassword(etPassword.text.toString())
        AppBusManager.setToken(etToken.text.toString())
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        LogTools.debug("ccTime", System.currentTimeMillis())
        super.onCreate(savedInstanceState)
        LogTools.debug("ccTime", System.currentTimeMillis())
    }
    override fun onClick(p0: View?) {
        when (p0) {
            btnLogin -> {
//                val username = etUsername.text.toString()
//                val password = etPassword.text.toString()
//
//                if(TextUtils.isEmpty(etToken.text.toString())){
//                    toast("token 不能为空")
//                    return
//                }
//                AppBusManager.Companion.setToken(etToken.text.toString())
//                if (!mPresenter.checkParam(username, password)) {
//                    return
//                }
//                mPresenter.login(username, password)
                goService()
            }
        }
    }
    fun goService() {
        LogTools.debug("ccTime", System.currentTimeMillis())
        CC.obtainBuilder("CompIM")
                .setActionName("showActivityChat")
                .build().call()
    }

    override fun initListener() {
        btnLogin.setOnClickListener(this)
        tvRegister.setOnClickListener(this)
    }

    override fun getPresenter(): LoginPresenter {
        return LoginPresenter()
    }

    companion object {
        private const val REQUEST_CODE_REGISTER = 1

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_REGISTER ->
                if (resultCode == RESULT_OK) {
                    finish()
                }
        }
    }


    override fun layoutContentId(): Int {
        return R.layout.comp_im_activity_login
    }

    override fun finish() {
//        logTools.d(CCUtil.getNavigateCallId(this))
//        if (CCUtil.getNavigateCallId(this) != null) {
//            CC.sendCCResult(CCUtil.getNavigateCallId(this), if (true) CCResult.success() else CCResult.error("cancel"))
//        }
        super.finish()
    }

    override fun initData() {
    }

    override fun initView() {
        super.initView()
        getTitleBar().visibility = View.GONE
        etUsername.setText(AppBusManager.getUsername())
        etPassword.setText( AppBusManager.getPassword())
        etToken.setText(AppBusManager.getToken())
//        val username = AppBusManager.getUsername()
//        val password = AppBusManager.getPassword()
//        if (!mPresenter.checkParam(username, password)) {
//            return
//        }
//        mPresenter.login(username, password)
    }

    private var mExitTime: Long = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
                finish()
            } else {
                mExitTime = System.currentTimeMillis()
                ToastUtil.showToast("再按一次退出程序")
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}

