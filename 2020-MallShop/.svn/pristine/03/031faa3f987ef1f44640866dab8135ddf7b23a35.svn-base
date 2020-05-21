package com.epro.mall.ui.login;

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
import com.epro.mall.mvp.contract.FindPasswordContract
import com.epro.mall.mvp.model.bean.GetVfBean
import com.epro.mall.mvp.presenter.FindPasswordPresenter
import com.epro.mall.ui.view.BottomPopup
import com.epro.mall.ui.view.CommonDialog
import com.epro.mall.utils.MallConst
import com.epro.mall.utils.MallUtils
import com.mike.baselib.activity.BaseTitleBarCustomActivity
import com.mike.baselib.utils.DisplayManager
import com.mike.baselib.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_find_password.*
import kotlinx.android.synthetic.main.layout_findpassword_email.*
import kotlinx.android.synthetic.main.layout_findpassword_phone.*
import org.jetbrains.anko.startActivity

/**
 * 找回密码页面
 */
class FindPasswordActivity : BaseTitleBarCustomActivity<FindPasswordContract.View, FindPasswordPresenter>(), FindPasswordContract.View, View.OnClickListener {
    override fun onGetVfCodeSuccess(result: GetVfBean.Result) {
        when(radioGroup.checkedRadioButtonId){
            R.id.rbPhoneFind->{
                btnPhoneGetCode.start()
            }
            R.id.rbEmailFind->{
                btnEmailGetCode.start()
            }
        }
    }

    companion object {
        const val TAG = "findPassword"
        const val FOR_AREA_CODE=1
        fun launch(context: Context) {
            launchWithStr(context, "")
        }

        fun launchWithStr(context: Context, str: String) {
            context.startActivity(Intent(context, FindPasswordActivity::class.java).putExtra(TAG, str))
        }
    }


    override fun getPresenter(): FindPasswordPresenter {
        return FindPasswordPresenter()
    }

    override fun onFindPasswordSuccess() {
        MallUtils.showProgressToast(getString(R.string.modify_success),this)
        finish()
    }


    override fun layoutContentId(): Int {
        return R.layout.activity_find_password
    }

    override fun initData() {

    }

    override fun onClick(p0: View?) {
        when (p0) {

            btnPhoneGetCode -> {
                val areaCode = tvAreaCode.text.toString().trim()
                val account = etPhone.text.toString().trim()
                if (!mPresenter.checkPhone(this,account)) {
                    return
                }
              //  btnPhoneGetCode.start()
                mPresenter.getVfCode(areaCode+"_"+account, MallConst.VF_TYPE_FINDPASSWORD, "phone")
            }

            btnEmailGetCode -> {
                val account = etEmail.text.toString().trim()
                if (!mPresenter.checkEmail(this,account)) {
                    return
                }
             //   btnEmailGetCode.start()
                mPresenter.getVfCode(account, MallConst.VF_TYPE_FINDPASSWORD, "email")
            }
            btnPhoneComplete->{
                val areaCode = tvAreaCode.text.toString().trim()
                val account = etPhone.text.toString().trim()
                val code = etPhoneVfcode.text.toString().trim()
                val password = etPhonePassword.text.toString().trim()
                val rePassword = etRePhonePassword.text.toString().trim()
                if (!mPresenter.checkParams3(this,password, rePassword)) {
                    return
                }
                mPresenter.findPassword(areaCode + "_" + account, password,rePassword, code,MallConst.LOGIN_TYPE_MP, "phone")
            }
            btnEmailComplete->{
                val account = etEmail.text.toString().trim()
                val code = etEmailVfcode.text.toString().trim()
                val password = etEmailPassword.text.toString().trim()
                val rePassword = etReEmailPassword.text.toString().trim()
                if (!mPresenter.checkParams3(this,password, rePassword)) {
                    return
                }
                mPresenter.findPassword(account, password, rePassword,code,MallConst.LOGIN_TYPE_EP, "email")
            }
            ivClose->{
                finish()
            }
            tvAreaCode->{
                //AreaSelectActivity.launchWithShopIdForResult(this, FOR_AREA_CODE)
                showBottomPopup()
            }
        }
    }

    //区号选择
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


    override fun initListener() {
        btnPhoneComplete.setOnClickListener(this)
        btnEmailComplete.setOnClickListener(this)
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
        radioGroup.check(R.id.rbPhoneFind)
        phoneFindUI()
        radioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
                if (p1 == R.id.rbPhoneFind) {
                    phoneFindUI()
                } else {
                    emailFindUI()
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


    private fun phoneFindUI() {
        rbPhoneFind.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimensionPixelSize(R.dimen.navigationTextSize).toFloat())
        rbPhoneFind.setTextColor(this.resources.getColor(R.color.mainColor))
        rbEmailFind.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimensionPixelSize(R.dimen.navigationTextSize).toFloat())
        rbEmailFind.setTextColor(this.resources.getColor(R.color.thirdTextColor))
        setCheckedPoninter(R.drawable.shape_register_indicator,rbPhoneFind)
        setCheckedPoninter(R.drawable.shape_logintab_indicator_tran,rbEmailFind)
    }

    private fun emailFindUI() {
        rbEmailFind.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimensionPixelSize(R.dimen.navigationTextSize).toFloat())
        rbEmailFind.setTextColor(this.resources.getColor(R.color.mainColor))
        rbPhoneFind.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimensionPixelSize(R.dimen.navigationTextSize).toFloat())
        rbPhoneFind.setTextColor(this.resources.getColor(R.color.thirdTextColor))
        setCheckedPoninter(R.drawable.shape_register_indicator,rbEmailFind)
        setCheckedPoninter(R.drawable.shape_logintab_indicator_tran,rbPhoneFind)
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

    override fun initView() {
        super.initView()
        getTitleBar().visibility = View.GONE
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
                R.id.rbPhoneFind -> {
                       flag = TextUtils.isEmpty(etPhone.text.toString())
                       if (!flag) btnPhoneGetCode.enableUI() else btnPhoneGetCode.disableUI()
                        flag = TextUtils.isEmpty(etPhonePassword.text.toString()) || TextUtils.isEmpty(etRePhonePassword.text.toString())||TextUtils.isEmpty(etPhone.text.toString())||TextUtils.isEmpty(etPhoneVfcode.text.toString())
                        btnPhoneComplete.isEnabled = !flag
                        btnPhoneComplete.setBackgroundResource(if (!flag) R.drawable.selector_bg_btn_register else R.drawable.register_btn_bg)
                }
                R.id.rbEmailFind -> {
                        flag = TextUtils.isEmpty(etEmail.text.toString())
                        if (!flag) btnEmailGetCode.enableUI() else btnEmailGetCode.disableUI()
                        flag = TextUtils.isEmpty(etEmailPassword.text.toString()) || TextUtils.isEmpty(etReEmailPassword.text.toString())||TextUtils.isEmpty(etEmail.text.toString())||TextUtils.isEmpty(etEmailVfcode.text.toString())
                        btnEmailComplete.isEnabled = !flag
                        btnEmailComplete.setBackgroundResource(if (!flag) R.drawable.selector_bg_btn_register else R.drawable.register_btn_bg)
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
        if(getString(R.string.ueser_not_in).equals(errorMsg)){
            if ("phone".equals(type)){
                showRegisterDailog(getString(R.string.phone_not_register_1),etPhone)
            }else{
                showRegisterDailog(getString(R.string.email_not_register_1),etEmail)
            }
        }
    }

    private fun showRegisterDailog(str: String ,text:EditText) {
        CommonDialog.Builder(this)
                .setContent(str+text.text.toString().trim()+getString(R.string.phone_not_register_2))
                .setConfirmText(getString(R.string.phone_not_register_confirm))
                .setOnConfirmListener(object : CommonDialog.OnConfirmListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                        finish()
                        startActivity<RegisterActivity>()
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

}


