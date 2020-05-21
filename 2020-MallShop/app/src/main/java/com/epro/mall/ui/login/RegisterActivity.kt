package com.epro.mall.ui.login

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.*
import com.epro.mall.R
import com.epro.mall.mvp.contract.RegisterContract
import com.epro.mall.mvp.model.bean.GetVfBean
import com.epro.mall.mvp.model.bean.RegisterBean
import com.epro.mall.mvp.presenter.RegisterPresenter
import com.epro.mall.ui.activity.PhoneServiceTermsActivity
import com.epro.mall.ui.view.BottomPopup
import com.epro.mall.ui.view.CommonDialog
import com.epro.mall.ui.view.PhoneServicePopup
import com.epro.mall.utils.MallConst
import com.epro.mall.utils.MallUtils
import com.mike.baselib.activity.BaseTitleBarCustomActivity
import com.mike.baselib.utils.DisplayManager
import com.mike.baselib.utils.StatusBarUtil
import com.mike.baselib.utils.toast
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.layout_register_email.*
import kotlinx.android.synthetic.main.layout_register_phone.*
import org.jetbrains.anko.startActivity

/**
 * 注册页面
 */
class RegisterActivity : BaseTitleBarCustomActivity<RegisterContract.View, RegisterPresenter>(), RegisterContract.View, View.OnClickListener{

    override fun onGetVfCodeSuccess(result: GetVfBean.Result) {
           when(radioGroup.checkedRadioButtonId){
               R.id.rbPhoneRegister->{
                   toast(getString(R.string.code_send_success))
                   btnPhoneGetCode.start()
               }
               R.id.rbEmailRegister->{
                   toast(getString(R.string.code_send_success))
                   btnEmailGetCode.start()
               }
           }
    }


    companion object {
        const val TAG = "Register"
        const val FOR_AREA_CODE = 1
        fun launch(context: Context) {
            launchWithStr(context, "")
        }

        fun launchWithStr(context: Context, str: String) {
            context.startActivity(Intent(context, RegisterActivity::class.java).putExtra(TAG, str))
        }
    }

    override fun onClick(p0: View?) {
        when (p0) {
            btnPhoneRegister -> {
                //注册
                val areaCode = tvAreaCode.text.toString().trim()
                val account = etPhone.text.toString().trim()
                val code = etPhoneVfcode.text.toString().trim()
                val password = etPhonePassword.text.toString().trim()
                val rePassword = etRePhonePassword.text.toString().trim()
                if (!mPresenter.checkParams3(this,password, rePassword)) {
                    return
                }
                mPresenter.register(areaCode + "_" + account, password, code,MallConst.LOGIN_TYPE_MP ,"phone")
            }
            btnEmailRegister -> {
                val account = etEmail.text.toString().trim()
                val code = etEmailVfcode.text.toString().trim()
                val password = etEmailPassword.text.toString().trim()
                val rePassword = etReEmailPassword.text.toString().trim()
                if (!mPresenter.checkParams3(this,password, rePassword)) {
                    return
                }
                mPresenter.register(account, password, code,MallConst.LOGIN_TYPE_EP, "email")
            }
            btnPhoneGetCode -> {
                val areaCode = tvAreaCode.text.toString().trim()
                val account = etPhone.text.toString().trim()
                if (!mPresenter.checkPhone(this,account)) {
                    return
                }
                btnPhoneGetCode.start()
                mPresenter.getVfCode(areaCode+"_"+account, MallConst.VF_TYPE_REGISTER, "phone")
            }
            btnEmailGetCode -> {
                val account = etEmail.text.toString().trim()
                if (!mPresenter.checkEmail(this,account)) {
                    return
                }
                btnEmailGetCode.start()
                mPresenter.getVfCode(account, MallConst.VF_TYPE_REGISTER, "email")
            }

            ivClose -> {
                finish()
            }
            tvAreaCode -> {
                //AreaSelectActivity.launchWithShopIdForResult(this, FOR_AREA_CODE)
                showBottomPopup()
            }

        }
    }


    override fun initListener() {
        btnPhoneRegister.setOnClickListener(this)
        btnEmailRegister.setOnClickListener(this)
        cbPhoneEyes.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            etPhonePassword.inputType = if (isChecked) InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
        })
        cbRePhoneEyes.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            etRePhonePassword.inputType = if (isChecked) InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
        })
        cbEmailEyes.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            etEmailPassword.inputType = if (isChecked) InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
        })
        cbReEmailEyes.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            etReEmailPassword.inputType = if (isChecked) InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
        })
        radioGroup.check(R.id.rbPhoneRegister)
        phoneRegisterUI()
        radioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
                if (p1 == R.id.rbPhoneRegister) {
                    phoneRegisterUI()
                } else {
                    emailRegisterUI()
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
        etPhonePassword.setOnFocusChangeListener { p0, p1 ->
            if (!etPhonePassword.hasFocus()){
                mPresenter.checkParams4(this@RegisterActivity, etPhonePassword.text.toString().trim())
            }
        }

        etEmailPassword.setOnFocusChangeListener { p0, p1 ->
            if (!etEmailPassword.hasFocus()){
                mPresenter.checkParams4(this@RegisterActivity, etEmailPassword.text.toString().trim())
            }
        }

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
        ivClose.setOnClickListener(this)
        tvAreaCode.setOnClickListener(this)
    }

    private fun phoneRegisterUI() {
        rbPhoneRegister.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimensionPixelSize(R.dimen.navigationTextSize).toFloat())
        rbPhoneRegister.setTextColor(this.resources.getColor(R.color.mainColor))
        rbEmailRegister.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimensionPixelSize(R.dimen.navigationTextSize).toFloat())
        rbEmailRegister.setTextColor(this.resources.getColor(R.color.thirdTextColor))
        setCheckedPoninter(R.drawable.shape_register_indicator, rbPhoneRegister)
        setCheckedPoninter(R.drawable.shape_logintab_indicator_tran, rbEmailRegister)
    }

    private fun emailRegisterUI() {
        rbEmailRegister.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimensionPixelSize(R.dimen.navigationTextSize).toFloat())
        rbEmailRegister.setTextColor(this.resources.getColor(R.color.mainColor))
        rbPhoneRegister.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimensionPixelSize(R.dimen.navigationTextSize).toFloat())
        rbPhoneRegister.setTextColor(this.resources.getColor(R.color.thirdTextColor))
        setCheckedPoninter(R.drawable.shape_register_indicator, rbEmailRegister)
        setCheckedPoninter(R.drawable.shape_logintab_indicator_tran, rbPhoneRegister)
    }

    private fun setCheckedPoninter(res: Int, radioButton: RadioButton) {
        val drawable = resources?.getDrawable(res)
        drawable?.bounds = Rect(0, 0, drawable?.minimumWidth!!, drawable?.minimumHeight!!)
        radioButton.setCompoundDrawables(null, null, null, drawable)
    }

    override fun getPresenter(): RegisterPresenter {
        return RegisterPresenter()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Activity.RESULT_OK == resultCode) {
            when (requestCode) {
                FOR_AREA_CODE -> {
                    tvAreaCode.text = "+" + data?.getStringExtra("area_code")
                }
            }
        }
    }

    override fun layoutContentId(): Int {
        return R.layout.activity_register
    }

    override fun registerSuccess(result: RegisterBean.Result) {
        MallUtils.showProgressToast(getString(R.string.register_success),this)
        finish()
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

    override fun initData() {
    }

    override fun initStatusBar() {
        StatusBarUtil.immersive(this, resources.getColor(R.color.white), 0f)
    }

    override fun initView() {
        super.initView()
        getTitleBar().visibility = View.GONE
        cbPhoneEyes.isChecked = false
        cbEmailEyes.isChecked = false
        btnPhoneGetCode.isEnabled = false
        btnEmailGetCode.isEnabled = false
        showPhoneServicePopup()
    }


    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            var flag = true
            var flagCode = true
            when (radioGroup.checkedRadioButtonId) {
                R.id.rbPhoneRegister -> {
                    flag = TextUtils.isEmpty(etPhonePassword.text.toString()) || TextUtils.isEmpty(etRePhonePassword.text.toString()) || TextUtils.isEmpty(etPhoneVfcode.text.toString())
                            || TextUtils.isEmpty(etPhone.text.toString())
                    btnPhoneRegister.isEnabled = !flag
                    btnPhoneRegister.setBackgroundResource(if (!flag) R.drawable.selector_bg_btn_register else R.drawable.register_btn_bg)
                    flagCode = TextUtils.isEmpty(etPhone.text.toString())
                    if (!flagCode) btnPhoneGetCode.enableUI() else btnPhoneGetCode.disableUI()
                }
                R.id.rbEmailRegister -> {
                    flag = TextUtils.isEmpty(etEmailPassword.text.toString()) || TextUtils.isEmpty(etReEmailPassword.text.toString()) || TextUtils.isEmpty(etEmail.text.toString()) ||
                            TextUtils.isEmpty(etEmailVfcode.text.toString())
                    btnEmailRegister.isEnabled = !flag
                    btnEmailRegister.setBackgroundResource(if (!flag) R.drawable.selector_bg_btn_register else R.drawable.register_btn_bg)
                    flagCode = TextUtils.isEmpty(etEmail.text.toString())
                    if (!flagCode) btnEmailGetCode.enableUI() else btnEmailGetCode.disableUI()
                }
            }

        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
    }

    override fun showError(errorMsg: String, errorCode: Int, type: String) {
        super.showError(errorMsg, errorCode, type)
        if (getString(R.string.this_account_already_exists).equals(errorMsg)){
            if ("phone".equals(type)){
                showLoginDailog(getString(R.string.phone_not_register_1),etPhone)
            }else{
                showLoginDailog(getString(R.string.email_not_register_1),etEmail)
            }
        }else if (1000001 == errorCode){
            if ("phone".equals(type)){
                showLoginDailog(getString(R.string.phone_not_register_1),etPhone)
            }else{
                showLoginDailog(getString(R.string.email_not_register_1),etEmail)
            }
        }
    }

    private fun showLoginDailog(str: String ,text:EditText) {
        CommonDialog.Builder(this)
                .setContent(str+text.text.toString().trim()+getString(R.string.phone_ready_register_1))
                .setConfirmText(getString(R.string.phone_ready_register_confirm))
                .setOnConfirmListener(object : CommonDialog.OnConfirmListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                        finish()
                        startActivity<LoginActivity>()
                    }
                })
                .setOnCancelListener(object : CommonDialog.OnCancelListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                    }
                })
                .create()
                .show()
    }

    var flag:Boolean=false
    private fun showBottomPopup() {
        val pop = BottomPopup(this)
        pop.width = DisplayManager.getScreenWidth()!!*100/100
        pop.height = DisplayManager.getScreenHeight()!!*35/100
        pop.popupGravity = Gravity.BOTTOM
        val delImage = pop.contentView.findViewById<ImageView>(R.id.delImg)
        val imgChina = pop.contentView.findViewById<ImageView>(R.id.imgChina)
        val imgHK = pop.contentView.findViewById<ImageView>(R.id.imgHK)
        val rlChina = pop.contentView.findViewById<RelativeLayout>(R.id.rlChina)
        val rlHK = pop.contentView.findViewById<RelativeLayout>(R.id.rlHK)
        if (!flag){
            imgChina.visibility = View.VISIBLE
            imgHK.visibility = View.GONE
            tvAreaCode.text = "+86"
        }else{
            imgChina.visibility = View.GONE
            imgHK.visibility = View.VISIBLE
            tvAreaCode.text = "+852"
        }
        delImage.setOnClickListener {
            pop.dismiss()
        }
        rlChina.setOnClickListener {
            imgChina.visibility = View.VISIBLE
            imgHK.visibility = View.GONE
            tvAreaCode.text = "+86"
            flag = false
            pop.dismiss()
        }
        rlHK.setOnClickListener {
            imgChina.visibility = View.GONE
            imgHK.visibility = View.VISIBLE
            tvAreaCode.text = "+852"
            flag = true
            pop.dismiss()
        }
        pop.showPopupWindow()
    }

    private fun showPhoneServicePopup() {
        val pop = PhoneServicePopup(this)
        val screenHeight = DisplayManager.px2dip(DisplayManager.getScreenHeight()!!)
        logTools.t("YB").d("screenHeight: "+screenHeight)
        pop.width = DisplayManager.getScreenWidth()!!*83/100
        //禁止返回键dismiss
        pop.setBackPressEnable(false)
        if (screenHeight<=667) { // lower api
            pop.height = DisplayManager.getScreenHeight()!!*70/100
        } else{
            pop.height = DisplayManager.getScreenHeight()!!*65/100
        }
        pop.popupGravity = Gravity.CENTER
        pop.isAllowDismissWhenTouchOutside = false
        pop.showPopupWindow()
        val textStart = pop.contentView.findViewById<TextView>(R.id.textStart)
        val registrationAgreement = pop.contentView.findViewById<TextView>(R.id.registrationAgreement)
        val registrationAgreement2 = pop.contentView.findViewById<TextView>(R.id.registrationAgreement2)
        val termsOfService = pop.contentView.findViewById<TextView>(R.id.termsOfService)
        val privacyPolicy = pop.contentView.findViewById<TextView>(R.id.privacyPolicy)
        textStart.text = "       "+getString(R.string.popup_service_terms)
        val btnNotAgree = pop.contentView.findViewById<TextView>(R.id.btnNotAgree)
        val btnAgree = pop.contentView.findViewById<TextView>(R.id.btnAgree)
        btnNotAgree.setOnClickListener {
            this.finish()
        }
        btnAgree.setOnClickListener {
            pop.dismiss()
        }
        //用户注册协议
        registrationAgreement.setOnClickListener {
            PhoneServiceTermsActivity.launchWithStr(this,PhoneServiceTermsActivity.USER_REGISTRATION_AGREEMENT)
        }
        //用户注册协议
        registrationAgreement2.setOnClickListener {
            PhoneServiceTermsActivity.launchWithStr(this,PhoneServiceTermsActivity.USER_REGISTRATION_AGREEMENT)
        }
        //服务条款
        termsOfService.setOnClickListener {
            PhoneServiceTermsActivity.launchWithStr(this,PhoneServiceTermsActivity.TERMS_OF_SERVICE)
        }
        //隐私政策
        privacyPolicy.setOnClickListener {
            PhoneServiceTermsActivity.launchWithStr(this,PhoneServiceTermsActivity.PRIVACY_POLICY)
        }
    }
}

