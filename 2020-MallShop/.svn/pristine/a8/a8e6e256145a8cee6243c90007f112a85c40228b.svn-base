package com.epro.mall.ui.activity;

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.CompoundButton
import com.mike.baselib.activity.BaseTitleBarCustomActivity
import com.epro.mall.R
import com.epro.mall.mvp.contract.AccountBindContract
import com.epro.mall.mvp.model.bean.GetUserVfBean
import com.epro.mall.mvp.presenter.AccountBindPresenter
import com.epro.mall.utils.MallConst
import com.mike.baselib.utils.DisplayManager
import kotlinx.android.synthetic.main.activity_account_bind.*
import com.mike.baselib.utils.toast


class AccountBindActivity : BaseTitleBarCustomActivity<AccountBindContract.View, AccountBindPresenter>(), AccountBindContract.View, View.OnClickListener {

    override fun onClick(p0: View?) {

        when(p0){
            tvAreaCode->{
                AreaSelectActivity.launchForResult(this, FOR_AREA_CODE)
            }

            btnPhoneGetCode->{
                var phoneNum = etPhone.text.toString().trim()
                if (BIND_PHONE.equals(style)){
                    var area  = tvAreaCode.text.toString().trim()
                    if (!mPresenter.checkPhone(this,phoneNum)){
                        return
                    }
                    mPresenter.getVfCode(MallConst.LOGIN_TYPE_MP,area+"_"+phoneNum,5,MallConst.GET_USERVFCODE)
                }else {
                    if (!mPresenter.checkEmail(this,phoneNum)){
                        return
                    }
                    mPresenter.getVfCode(MallConst.LOGIN_TYPE_EP,phoneNum,5,MallConst.GET_USERVFCODE)
                }
            }

            btnConfirm->{
                var area  = tvAreaCode.text.toString().trim()
                var phoneNum = etPhone.text.toString().trim()
                var vfCode = etPhoneVfcode.text.toString().trim()
                var passWord = etPhonePassword.text.toString().trim()
                if (BIND_PHONE.equals(style)){
                    if (!mPresenter.checkPhone(this,phoneNum)){
                        return
                    }
                    if (!mPresenter.checkPassword(this,passWord)){
                        return
                    }
                    mPresenter.AccountBind(MallConst.ACCOUNT_BIND,area+"_"+phoneNum,vfCode,MallConst.LOGIN_TYPE_MP,passWord,1)
                }else{
                    if (!mPresenter.checkEmail(this,phoneNum)){
                        return
                    }
                    if (!mPresenter.checkPassword(this,passWord)){
                        return
                    }
                    mPresenter.AccountBind(MallConst.ACCOUNT_BIND,phoneNum,vfCode,MallConst.LOGIN_TYPE_EP,passWord,1)
                }
            }
        }
    }

    override fun onGetVfCodeSuccess(result: GetUserVfBean.Result) {
            btnPhoneGetCode.start()
           toast(getString(R.string.code_send_success))
    }

    companion object {
        const val TAG = "AccountBind"
        private const val FOR_AREA_CODE = 2
        const val BIND_PHONE = "1"
        const val BIND_EMAIL = "2"
        fun launch(context: Context) {
            launchWithStr(context, "")
        }

        fun launchWithStr(context: Context, str: String) {
            context.startActivity(Intent(context, AccountBindActivity::class.java).putExtra(TAG, str))
        }
    }

    override fun getPresenter(): AccountBindPresenter {
        return AccountBindPresenter()
    }

    override fun onAccountBindSuccess() {
        toast(getString(R.string.account_bind_success))
        finish()
    }


    override fun layoutContentId(): Int {
        return R.layout.activity_account_bind
    }

    override fun initData() {

    }

    var style:String?=null
    override fun initView() {
        super.initView()
        style = intent.getStringExtra(TAG)
        if (BIND_PHONE.equals(style)){
            getTitleView().text = getString(R.string.bind_account_phone)
        }else{
            tvAreaCode.visibility = View.GONE
            bindTips.visibility = View.GONE
            etPhone.hint = getString(R.string.pls_input_email)
            getTitleView().text = getString(R.string.bind_account_email)
        }

    }

    override fun initListener() {
        tvAreaCode.setOnClickListener(this)
        btnPhoneGetCode.setOnClickListener(this)
        btnConfirm.setOnClickListener(this)
        etPhone.addTextChangedListener(textWatcher)
        etPhoneVfcode.addTextChangedListener(textWatcher)
        etPhonePassword.addTextChangedListener(textWatcher)
        cbPhoneEyes.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener {
            buttonView,isChecked ->
            etPhonePassword.inputType = if (isChecked) InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
        })
    }

    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            val flag1 = TextUtils.isEmpty(etPhone.text.toString())
            if (!flag1) btnPhoneGetCode.enableUI() else btnPhoneGetCode.disableUI()
            val flag = TextUtils.isEmpty(etPhone.text.toString()) || TextUtils.isEmpty(etPhoneVfcode.text.toString())||TextUtils.isEmpty(etPhonePassword.text.toString())
            btnConfirm.isEnabled = !flag
            btnConfirm.setBackgroundResource(if(!flag)R.drawable.selector_bg_push_btn else R.drawable.shape_bg_push_btn)
        }
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK){
            when(requestCode){
                FOR_AREA_CODE->{
                    tvAreaCode.text="+"+data?.getStringExtra("area_code")
                }
            }
        }
    }
}


