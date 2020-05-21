package com.epro.mall.ui.login;

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.CompoundButton
import com.mike.baselib.activity.BaseTitleBarCustomActivity
import com.epro.mall.R
import com.epro.mall.mvp.contract.ModifyPasswordContract
import com.epro.mall.mvp.model.bean.GetUserVfBean
import com.epro.mall.mvp.model.bean.GetVfBean
import com.epro.mall.mvp.model.bean.ModifyPasswordBean
import com.epro.mall.mvp.model.bean.MyInfoBean
import com.epro.mall.mvp.presenter.ModifyPasswordPresenter
import com.epro.mall.utils.MallConst
import com.epro.mall.utils.MallUtils
import com.mike.baselib.utils.ValidateUtils
import com.mike.baselib.utils.ext_doubleClick
import kotlinx.android.synthetic.main.activity_modify_password.*
import kotlinx.android.synthetic.main.activity_modify_password.btnGetCode
import kotlinx.android.synthetic.main.activity_modify_password.cbPhoneEyes
import kotlinx.android.synthetic.main.activity_modify_password.etPhonePassword
import kotlinx.android.synthetic.main.activity_modify_password.etVfcode
import kotlinx.android.synthetic.main.activity_modify_password.viewFlipper
import org.jetbrains.anko.textColor
import com.mike.baselib.utils.toast

/**
 * 修改密码
 */
class ModifyPasswordActivity : BaseTitleBarCustomActivity<ModifyPasswordContract.View, ModifyPasswordPresenter>(), ModifyPasswordContract.View, View.OnClickListener {

    override fun onGetVfCodeSuccess(result: GetUserVfBean.Result) {
        btnGetCode.start()
    }

    var phoneNum:String?=null
    var emailNum:String?=null
    override fun onMyInfoSuccess(result: MyInfoBean.Result) {
        if (!"".equals(result.mobile)){
            tvByPhone.visibility = View.VISIBLE
            tvPhone.text = getStringAccount(result.mobile,"phone")
        }else{
            tvByPhone.visibility = View.GONE
        }

        if (!"".equals(result.email)){
            tvByEmail.visibility = View.VISIBLE
            tvEmail.text = getStringAccount(result.email,"email")
        }else{
            tvByEmail.visibility = View.GONE
        }
        if (TextUtils.isEmpty(result.mobile)&&TextUtils.isEmpty(result.email)){
            tvModify.visibility = View.GONE
            toast(getString(R.string.unbind_phone_and_email))
        }
        phoneNum = result.mobile
        emailNum = result.email
    }

    override fun onClick(p0: View?) {
       when(p0){
           tvByPhone->{
               tvAlert.text= getString(R.string.modify_password_now_title)+getStringAccount(phoneNum!!,"phone")+getString(R.string.modify_password_now_title_2)
               isByPhone=true
               initNextUI()
           }
           tvByEmail->{
               isByPhone=false
               tvAlert.text=getString(R.string.modify_password_now_email_title)+getStringAccount(emailNum!!,"email")+getString(R.string.modify_password_now_email_title_2)
               initNextUI()
           }

           getLeftBackView()->{
              showPrevious()
           }

           btnGetCode->{
               btnGetCode.textColor = R.color.mainMatchColor_60
               //修改密码传4
               if (isByPhone){
                   var account:String?
                   if ( phoneNum!!.startsWith("+86",false)){
                       account = "+86_"+phoneNum!!.substring(3,phoneNum!!.length)
                   }else{
                       account = "+852_"+phoneNum!!.substring(4,phoneNum!!.length)
                   }
                    mPresenter.getVfCode(MallConst.LOGIN_TYPE_MP,account!!,4,MallConst.GET_VFCODE)
               }else{
                   mPresenter.getVfCode(MallConst.LOGIN_TYPE_EP,emailNum!!,4,MallConst.GET_VFCODE)
               }
           }

           btnConfirm->{
               val code = etVfcode.text.toString().trim()
               val newPassword = etPhonePassword.text.toString().trim()
               val againPassword  = etPhonePassword2.text.toString().trim()

               if (newPassword !=againPassword){
                   MallUtils.showToast(getString(R.string.two_password_not_same),this)
                   return
               }

               if(!ValidateUtils.validatePassword(againPassword)){
                   MallUtils.showToast(getString(R.string.password_format_error),this)
                   return
               }

              if(isByPhone){
                  var account:String?
                  if ( phoneNum!!.startsWith("+86",false)){
                      account = "+86_"+phoneNum!!.substring(3,phoneNum!!.length)
                  }else{
                      account = "+852_"+phoneNum!!.substring(4,phoneNum!!.length)
                  }
                     mPresenter.modifyPassword(MallConst.LOGIN_TYPE_MP,account!!,code,againPassword)
              }else{
                    mPresenter.modifyPassword(MallConst.LOGIN_TYPE_EP,emailNum!!,code,againPassword)
              }
           }
       }
    }

    private fun showPrevious(){
        if(isFirstPage){
            finish()
        }else{
            viewFlipper.showNext()
            isFirstPage=true
            getTitleView().text=getString(R.string.modify_password)
        }
    }

    private fun initNextUI(){
        viewFlipper.showNext()
        isFirstPage=false
        etVfcode.setText("")
        etPhonePassword.setText("")
        btnGetCode.cancel()
        btnGetCode.enableUI()
        btnConfirm.isEnabled=false
        getTitleView().text=getString(R.string.pls_set_new_password)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
               showPrevious()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    companion object {
        const val TAG = "ModifyPasswordActivity"
        fun launch(context: Context) {
            launchWithStr(context, "")
        }

        fun launchWithStr(context: Context, str: String) {
            context.startActivity(Intent(context, ModifyPasswordActivity::class.java).putExtra(TAG, str))
        }
    }

    override fun getPresenter(): ModifyPasswordPresenter {
        return ModifyPasswordPresenter()
    }

    override fun modifyPasswordSuccess(data: ModifyPasswordBean) {
         MallUtils.showToast(getString(R.string.new_password_set_success),this)
         finish()
    }


    override fun layoutContentId(): Int {
        return R.layout.activity_modify_password
    }

    override fun initData() {

    }
    var isFirstPage = true
    var isByPhone = true
    override fun initView() {
        super.initView()
        getTitleView().text=getString(R.string.modify_password)
        btnConfirm.isEnabled=false
        btnGetCode.enableUI()
        mPresenter.MyInfo(MallConst.GET_MY_INFO)
    }

    override fun initListener() {
        tvByPhone.setOnClickListener(this)
        tvByEmail.setOnClickListener(this)
        getLeftBackView().ext_doubleClick(this)
        etVfcode.addTextChangedListener(textWatcher)
        etPhonePassword.addTextChangedListener(textWatcher)
        etPhonePassword2.addTextChangedListener(textWatcher)
        btnConfirm.setOnClickListener(this)
        btnGetCode.setOnClickListener(this)
        cbPhoneEyes.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener {
            buttonView,isChecked ->
            etPhonePassword.inputType = if (isChecked) InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
        })
        cbPhoneEyes2.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener {
            buttonView,isChecked ->
            etPhonePassword2.inputType = if (isChecked) InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
        })
    }

    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            val flag = TextUtils.isEmpty(etPhonePassword.text.toString()) || TextUtils.isEmpty(etVfcode.text.toString())||TextUtils.isEmpty(etPhonePassword2.text.toString())
            btnConfirm.isEnabled = !flag
            btnConfirm.setBackgroundResource(if(!flag)R.drawable.selector_bg_push_btn else R.drawable.shape_bg_push_btn)
        }
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        etVfcode.removeTextChangedListener(textWatcher)
        etPhonePassword.removeTextChangedListener(textWatcher)
    }

    private fun getStringAccount(mAccount:String?,mType:String): String? {
        var account:String?=null
        if (mAccount !=null){
            if ("email" .equals(mType)){
                val indexOf = mAccount!!.indexOf("@")
                if (indexOf>=5){
                    account = mAccount!!.substring(0,5)+"****"+mAccount!!.substring(indexOf)
                }else if (indexOf >=4){
                    account = mAccount!!.substring(0,4)+"****"+mAccount!!.substring(indexOf)
                }else if (indexOf >=3){
                    account = mAccount!!.substring(0,3)+"****"+mAccount!!.substring(indexOf)
                }else if (indexOf >=2){
                    account = mAccount!!.substring(0,2)+"****"+mAccount!!.substring(indexOf)
                }
                return  account
            }else{
                if ( mAccount!!.startsWith("+86",false)){
                    account = "+86 "+mAccount!!.substring(3,6)+"****"+mAccount!!.substring(mAccount!!.length-4,mAccount!!.length)
                }else{
                    account = "+852 "+mAccount!!.substring(4,6)+"****"+mAccount!!.substring(mAccount!!.length-2,mAccount!!.length)
                }
                return  account
            }
        }else{
            return  ""
        }
        return account
    }

    override fun showError(errorMsg: String, errorCode: Int, type: String) {
        super.showError(errorMsg, errorCode, type)
        toast(errorMsg)
    }
}

