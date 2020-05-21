package com.epro.comp.im.ui.activity;

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.CompoundButton
import com.mike.baselib.activity.BaseTitleBarCustomActivity
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.AppUtils
import com.epro.comp.im.R
import com.epro.comp.im.listener.LoginSuccessEvent
import com.epro.comp.im.mvp.contract.RegisterContract
import com.epro.comp.im.mvp.model.bean.User
import com.epro.comp.im.mvp.presenter.RegisterPresenter
import com.epro.comp.im.utils.IMConst
import kotlinx.android.synthetic.main.comp_im_activity_register.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.toast


class RegisterActivity : BaseTitleBarCustomActivity<RegisterContract.View, RegisterPresenter>(), RegisterContract.View {
    override fun initListener() {
    }

    override fun getVerifyCodeSuccess(msg: String) {
    }

    override fun registerSuccess(user: User) {
        AppBusManager.setUsername(etPhoneNo.text.toString().trim())
        EventBus.getDefault().post(LoginSuccessEvent())
        setResult(Activity.RESULT_OK)
        toast("登录成功")
        finish()
    }

    override fun getPresenter(): RegisterPresenter {
        return RegisterPresenter()
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
        return R.layout.comp_im_activity_register
    }


    override fun initData() {

    }

    override fun initStatusBar() {
       // StatusBarUtil.immersive(this, resources.getColor(R.color.white), 0f)
    }


    override fun initView() {
        super.initView()
        getTitleBar().visibility = View.GONE
        etPhoneNo.setText(AppBusManager.getUsername())
        btnRegister.setOnClickListener(View.OnClickListener {
            //注册
            val loginname = etPhoneNo.text.toString().trim()
            val name = etName.text.toString().trim()
            val unit = etUnit.text.toString().trim()
            if (!mPresenter.checkParam(loginname, name,unit)) {
                return@OnClickListener
            }
            mPresenter.register(loginname, name,unit, IMConst.REGISTER!!)
            AppUtils.closeKeyboard(this)
        })

        tvForget.setOnClickListener(View.OnClickListener {
        })

        cbDelete.isChecked = false
        cbDelete.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            etPhoneNo.setText("")
        })

        cbBack.isChecked = false
        cbBack.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            etName.setText("")
        })
        cbBack2.isChecked = false
        cbBack2.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            etUnit.setText("")
        })

    }
}

