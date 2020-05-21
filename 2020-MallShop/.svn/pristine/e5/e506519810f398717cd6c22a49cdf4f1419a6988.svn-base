package com.epro.mall.ui.login;

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.RadioGroup
import com.epro.mall.R
import com.epro.mall.mvp.contract.BindAccountContract
import com.epro.mall.mvp.model.bean.GetVfBean
import com.epro.mall.mvp.presenter.BindAccountPresenter
import com.epro.mall.ui.activity.AreaSelectActivity
import com.epro.mall.utils.MallConst
import com.mike.baselib.activity.BaseTitleBarCustomActivity
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_bind_account.*
import kotlinx.android.synthetic.main.layout_bind_email.*
import kotlinx.android.synthetic.main.layout_bind_phone.*


class BindAccountActivity : BaseTitleBarCustomActivity<BindAccountContract.View, BindAccountPresenter>(), BindAccountContract.View ,View.OnClickListener{
    override fun onGetVfCodeSuccess(result: GetVfBean.Result) {
        when(radioGroup.checkedRadioButtonId){
            R.id.rbPhoneBind->{
                btnPhoneGetCode.start()
            }
            R.id.rbEmailBind->{
                btnEmailGetCode.start()
            }
        }
    }

    companion object {
        const val TAG = "BindAccount"
        const val FOR_AREA_CODE=1
        fun launch(context: Context) {
            launchWithStr(context, "")
        }

        fun launchWithStr(context: Context, str: String) {
            context.startActivity(Intent(context, BindAccountActivity::class.java).putExtra(TAG, str))
        }
    }

    override fun getPresenter(): BindAccountPresenter {
        return BindAccountPresenter()
    }

    override fun onBindAccountSuccess() {
        finish()
    }


    override fun layoutContentId(): Int {
        return R.layout.activity_bind_account
    }

    override fun initData() {

    }

    var isPhoneFirstPage = true
    var isEmailFirstPage = true

    override fun onClick(p0: View?) {
        when (p0) {
            btnPhoneNext -> {
                val account = etPhone.text.toString().trim()
                val code = etPhoneVfcode.text.toString().trim()
                if (!mPresenter.checkParams(account, code)) {
                    return
                }
                vfPhone.showNext()
                isPhoneFirstPage = false
            }
            btnPhoneGetCode -> {
                val account = etPhone.text.toString().trim()
                if (!mPresenter.checkPhone(account)) {
                    return
                }
                mPresenter.getVfCode(account, MallConst.VF_TYPE_FINDPASSWORD, MallConst.REGISTER)
            }
            btnEmailNext -> {
                val account = etEmail.text.toString().trim()
                val code = etEmailVfcode.text.toString().trim()
                if (!mPresenter.checkParams2(account, code)) {
                    return
                }
                vfEmail.showNext()
                isEmailFirstPage = false
            }
            btnEmailGetCode -> {
                val account = etEmail.text.toString().trim()
                if (!mPresenter.checkEmail(account)) {
                    return
                }
                mPresenter.getVfCode(account, MallConst.VF_TYPE_FINDPASSWORD, MallConst.REGISTER)
            }
            btnPhoneConform->{
                val areaCode = tvAreaCode.text.toString().replace("+", "")
                val account = etPhone.text.toString().trim()
                val code = etPhoneVfcode.text.toString().trim()
                val password = etPhonePassword.text.toString().trim()
                val rePassword = etRePhonePassword.text.toString().trim()
                if (!mPresenter.checkParams3(password, rePassword)) {
                    return
                }
                mPresenter.getBindAccount(areaCode + "_" + account, password, code, MallConst.BIND_ACCOUNT)
            }
            btnEmailConform->{
                val account = etEmail.text.toString().trim()
                val code = etEmailVfcode.text.toString().trim()
                val password = etEmailPassword.text.toString().trim()
                val rePassword = etReEmailPassword.text.toString().trim()
                if (!mPresenter.checkParams3(password, rePassword)) {
                    return
                }
                mPresenter.getBindAccount(account, password, code, MallConst.BIND_ACCOUNT)
            }
            ivClose->{
                finish()
            }

            tvAreaCode->{
                AreaSelectActivity.launchForResult(this, FOR_AREA_CODE)
            }
        }
    }


    override fun initListener() {
        btnPhoneNext.setOnClickListener(this)
        btnEmailNext.setOnClickListener(this)
        btnPhoneConform.setOnClickListener(this)
        btnEmailConform.setOnClickListener(this)
        cbPhoneEyes.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            etPhonePassword.inputType = if (isChecked) InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
        })
        cbEmailEyes.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            etEmailPassword.inputType = if (isChecked) InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
        })
        cbRePhoneEyes.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            etRePhonePassword.inputType = if (isChecked) InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
        })
        cbReEmailEyes.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            etReEmailPassword.inputType = if (isChecked) InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
        })
        radioGroup.check(R.id.rbPhoneBind)
        phoneBindUI()
        radioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
                if (p1 == R.id.rbPhoneBind) {
                    phoneBindUI()
                } else {
                    emailBindUI()
                }
                viewFlipper.showNext()
            }
        })

        etPhone.addTextChangedListener(textWatcher)
        etEmail.addTextChangedListener(textWatcher)
        etPhonePassword.addTextChangedListener(textWatcher)
        etRePhonePassword.addTextChangedListener(textWatcher)
        etEmailPassword.addTextChangedListener(textWatcher)
        etReEmailPassword.addTextChangedListener(textWatcher)
        etPhoneVfcode.addTextChangedListener(textWatcher)
        etEmailVfcode.addTextChangedListener(textWatcher)
        btnPhoneGetCode.setOnClickListener(this)
        btnEmailGetCode.setOnClickListener(this)
        ivClose.setOnClickListener(this)

        btnPhoneGetCode.setOnFinishListener {
            if (TextUtils.isEmpty(etPhone.text.toString())) {
                it.disableUI()
            }
        }
        btnEmailGetCode.setOnFinishListener {
            if (TextUtils.isEmpty(etEmail.text.toString())) {
                it.disableUI()
            }
        }
        tvAreaCode.setOnClickListener(this)
    }


    private fun phoneBindUI() {
        rbPhoneBind.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimensionPixelSize(R.dimen.navigationTextSize).toFloat())
        rbPhoneBind.setTextColor(this.resources.getColor(R.color.mainMatchColor))
        rbEmailBind.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimensionPixelSize(R.dimen.navigationTextSize).toFloat())
        rbEmailBind.setTextColor(this.resources.getColor(R.color.mainMatchColor_70))
        setCheckedPoninter(R.drawable.shape_logintab_indicator,rbPhoneBind)
        setCheckedPoninter(R.drawable.shape_logintab_indicator_tran,rbEmailBind)
    }

    private fun emailBindUI() {
        rbEmailBind.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimensionPixelSize(R.dimen.navigationTextSize).toFloat())
        rbEmailBind.setTextColor(this.resources.getColor(R.color.mainMatchColor))
        rbPhoneBind.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimensionPixelSize(R.dimen.navigationTextSize).toFloat())
        rbPhoneBind.setTextColor(this.resources.getColor(R.color.mainMatchColor_70))
        setCheckedPoninter(R.drawable.shape_logintab_indicator,rbEmailBind)
        setCheckedPoninter(R.drawable.shape_logintab_indicator_tran,rbPhoneBind)
    }

    private fun setCheckedPoninter(res:Int,radioButton: RadioButton){
        val drawable = resources?.getDrawable(res)
        drawable?.bounds = Rect(0, 0, drawable?.minimumWidth!!, drawable?.minimumHeight!!)
        radioButton.setCompoundDrawables(null, null, null, drawable)
    }

    override fun onDestroy() {
        super.onDestroy()
        etPhone.removeTextChangedListener(textWatcher)
        etEmail.removeTextChangedListener(textWatcher)
        etPhonePassword.removeTextChangedListener(textWatcher)
        etRePhonePassword.removeTextChangedListener(textWatcher)
        etEmailPassword.removeTextChangedListener(textWatcher)
        etReEmailPassword.removeTextChangedListener(textWatcher)
        etPhoneVfcode.removeTextChangedListener(textWatcher)
        etEmailVfcode.removeTextChangedListener(textWatcher)
    }


    override fun initStatusBar() {
        StatusBarUtil.immersive(this, resources.getColor(R.color.white), 0f)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(Activity.RESULT_OK==resultCode){
            when(requestCode){
                FOR_AREA_CODE->{
                    tvAreaCode.text="+"+data?.getStringExtra("area_code")
                }
            }
        }
    }

    override fun initView() {
        super.initView()
        getTitleBar().visibility = View.GONE
        etPhone.setText(AppBusManager.getUsername())
        cbPhoneEyes.isChecked = false
        cbEmailEyes.isChecked = false
        cbRePhoneEyes.isChecked = false
        cbReEmailEyes.isChecked = false
        btnEmailGetCode.isEnabled = false
        btnEmailGetCode.isEnabled = false
    }


    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            var flag = true
            when (radioGroup.checkedRadioButtonId) {
                R.id.rbPhoneBind -> {
                    flag = TextUtils.isEmpty(etPhone.text.toString())
                    if (!flag) btnPhoneGetCode.enableUI() else btnPhoneGetCode.disableUI()
                    if (isPhoneFirstPage) {
                        flag = TextUtils.isEmpty(etPhone.text.toString()) || TextUtils.isEmpty(etPhoneVfcode.text.toString())
                        btnPhoneNext.isEnabled = !flag
                        btnPhoneNext.setBackgroundResource(if (!flag) R.drawable.selector_bg_btn_login else R.drawable.shape_bg_btn_login_disable)
                    } else {
                        flag = TextUtils.isEmpty(etPhonePassword.text.toString()) || TextUtils.isEmpty(etRePhonePassword.text.toString())
                        btnPhoneConform.isEnabled = !flag
                        btnPhoneConform.setBackgroundResource(if (!flag) R.drawable.selector_bg_btn_login else R.drawable.shape_bg_btn_login_disable)
                    }
                }
                R.id.rbEmailBind -> {
                    flag = TextUtils.isEmpty(etEmail.text.toString())
                    if (!flag) btnEmailGetCode.enableUI() else btnEmailGetCode.disableUI()
                    if (isEmailFirstPage) {
                        flag = TextUtils.isEmpty(etEmail.text.toString()) || TextUtils.isEmpty(etEmailVfcode.text.toString())
                        btnEmailNext.isEnabled = !flag
                        btnEmailNext.setBackgroundResource(if (!flag) R.drawable.selector_bg_btn_login else R.drawable.shape_bg_btn_login_disable)
                    } else {
                        flag = TextUtils.isEmpty(etEmailPassword.text.toString()) || TextUtils.isEmpty(etReEmailPassword.text.toString())
                        btnEmailConform.isEnabled = !flag
                        btnEmailConform.setBackgroundResource(if (!flag) R.drawable.selector_bg_btn_login else R.drawable.shape_bg_btn_login_disable)
                    }
                }
            }

        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
    }



}


