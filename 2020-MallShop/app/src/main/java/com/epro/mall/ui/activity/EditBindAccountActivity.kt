package com.epro.mall.ui.activity;

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import com.epro.mall.R
import com.epro.mall.mvp.contract.EditBindAccountContract
import com.epro.mall.mvp.model.bean.CheckBindAccountBean
import com.epro.mall.mvp.model.bean.GetUserVfBean
import com.epro.mall.mvp.model.bean.GetVfBean
import com.epro.mall.mvp.presenter.EditBindAccountPresenter
import com.epro.mall.utils.MallConst
import com.epro.mall.utils.MallUtils
import com.mike.baselib.activity.BaseTitleBarCustomActivity
import com.mike.baselib.utils.AppUtils
import com.mike.baselib.utils.ValidateUtils
import com.mike.baselib.utils.ext_doubleClick
import kotlinx.android.synthetic.main.activity_edit_bind_account.*
import org.jetbrains.anko.textColor
import com.mike.baselib.utils.toast

/**
 * 绑定或修改绑定手机号和邮箱
 */
class EditBindAccountActivity : BaseTitleBarCustomActivity<EditBindAccountContract.View, EditBindAccountPresenter>(), EditBindAccountContract.View, View.OnClickListener {
    override fun onGetUserCodeSuccess(result: GetUserVfBean.Result,type: String) {
        if (type.equals(BIND_ACCOUNT)){  //绑定账号
            btnBindGetCode.start()
            toast(getString(R.string.code_send_success))
        }else{
            toast(getString(R.string.code_send_success))
        }
    }

    override fun onCheckSuccess(result: CheckBindAccountBean.Result) {
            if (isPhone) {
                getTitleView().text = getString(R.string.bind_telephone_num)
            } else {
                getTitleView().text = getString(R.string.bind_new_email)
                tvAreaCode.visibility = View.GONE
                ivPhone.visibility = View.GONE
                etPhone.hint = getString(R.string.pls_input_email_address)
            }
            viewFlipper.showNext()
            page++
            toast(getString(R.string.confirm_success))
    }

    override fun onGetVfCodeSuccess(result: GetVfBean.Result) {
           toast(getString(R.string.code_send_success))
    }

    var checkClick = 1
    var phone:String?=null
    override fun onClick(p0: View?) {
        when (p0) {
            tvByPhone -> {
                var phoneAccount = getStringAccount(phoneAndEmail!![0],"phone")
                if (TextUtils.isEmpty(phoneAccount)){
                    toast(getString(R.string.unbind_account))
                    return
                }
                tvVfAlert.text = getString(R.string.edit_phone_change_title_1)+ getStringAccount(phoneAndEmail!![0],"phone") +getString(R.string.edit_phone_change_title_2)
                checkClick = 1
                initNextUI()
            }
            tvByEmail -> {
                var emailAddress = getStringAccount(phoneAndEmail!![1],"email")
                if (TextUtils.isEmpty(emailAddress)){
                    toast(getString(R.string.unbind_email))
                    return
                }
                tvVfAlert.text = getString(R.string.edit_phone_change_title_3) + getStringAccount(phoneAndEmail!![1],"email") + getString(R.string.edit_phone_change_title_4)
                checkClick =2
                initNextUI()
            }

            //通过什么方式验证获取验证码
            btnGetCode -> {
                //    btnGetCode.textColor = R.color.mainMatchColor_60
                btnGetCode.start()
                phone= phoneAndEmail!![0]!!
                if (!TextUtils.isEmpty(phone)) {
                    if (phone!!.startsWith("+86")) {
                        phone = "+86_" + phone!!.substring(3)
                    } else if (phone!!.startsWith("+852")) {
                        phone = "+852_" + phone!!.substring(4)
                    }
                }
                if (1 == checkClick){
                    mPresenter.getUserVfCode(MallConst.LOGIN_TYPE_MP,phone!!,6,MallConst.GET_VFCODE)
                }else{
                    mPresenter.getUserVfCode(MallConst.LOGIN_TYPE_EP,phoneAndEmail!![1]!!,6,MallConst.GET_VFCODE)
                }
            }

            //绑定新账号获取验证码
            btnBindGetCode -> {
                val account = etPhone.text.toString().trim()
                val areaCode = tvAreaCode.text.toString().trim()
                if (isPhone){
                    mPresenter.getUserVfCode(MallConst.LOGIN_TYPE_MP,areaCode+"_"+account!!,5, BIND_ACCOUNT)
                }else {
                    mPresenter.getUserVfCode(MallConst.LOGIN_TYPE_EP,account!!,5,BIND_ACCOUNT)
                }
            }

            btnNext -> {
                val code = etVfcode.text.toString().trim()
                if (1 == checkClick){
                    mPresenter.checkBindAccount(MallConst.CHECK_ACCOUNT_BIND,phoneAndEmail!![0]!!,code,MallConst.LOGIN_TYPE_MP)
                }else{
                    mPresenter.checkBindAccount(MallConst.CHECK_ACCOUNT_BIND,phoneAndEmail!![1]!!,code,MallConst.LOGIN_TYPE_EP)
                }
                //后面放到验证通过回调处理
            }
            btnConfirm -> {
                if (isPhone) {
                    val code = etBindVfcode.text.toString().trim()
                    val account =etPhone.text.toString().trim()
                    var areaCode = tvAreaCode.text.toString().trim()
                    if(!ValidateUtils.validatePhoneNo(account)){
                        MallUtils.showToast(getString(R.string.phone_format_error),this)
                        return
                    }
                    mPresenter.getEditBindAccount(MallConst.CHANGE_ACCOUNT_EAMIL,areaCode+"_"+account,code,MallConst.LOGIN_TYPE_MP)
                } else {
                        val code = etBindVfcode.text.toString().trim()
                        val account =etPhone.text.toString().trim()
                        if(!ValidateUtils.validateEmail(account)){
                            MallUtils.showToast(getString(R.string.email_format_error),this)
                            return
                        }
                        mPresenter.getEditBindAccount(MallConst.CHANGE_ACCOUNT_EAMIL,account,code,MallConst.LOGIN_TYPE_EP)
                }
            }
            tvAreaCode->{
                AreaSelectActivity.launchForResult(this, FOR_AREA_CODE)
            }
            getLeftBackView() -> {
                showPrevious()
            }
        }
    }

    private fun initNextUI() {
        viewFlipper.showNext()
        page++
        etVfcode.setText("")
        etBindVfcode.setText("")
        btnGetCode.cancel()
        btnGetCode.enableUI()
        btnBindGetCode.cancel()
        btnBindGetCode.disableUI()
        etPhone.setText("")
        btnNext.isEnabled = false
        btnConfirm.isEnabled = false
        if (isPhone) {
            getTitleView().text = getString(R.string.my_info_change_phone)
        } else {
            getTitleView().text = getString(R.string.my_info_change_email)
        }
    }

    private fun showPrevious() {
        if (page == 1 || !isModify) {
            finish()
        } else {
            viewFlipper.showPrevious()
            page--
            if (page == 1) {
                AppUtils.closeKeyboard(this)
                getTitleView().text = getString(R.string.confirm_style)
            } else if (page == 2) {
                if (isPhone) {
                    getTitleView().text = getString(R.string.my_info_change_phone)
                } else {
                    getTitleView().text = getString(R.string.my_info_change_email)
                }
            }
        }
    }

    companion object {
        const val BIND_ACCOUNT = "bind_account"//绑定的账号,邮箱或者手机
        const val ACCOUNT_TYPE = "account_type"
        const val PHONE_EMAIL = "phone_email"
        private const val REQUEST_CODE_REGISTER = 1
        private const val FOR_AREA_CODE = 2
        private const val FOR_AREA_CODE_VF = 3
        const val TYPE_PHONE = 0
        const val TYPE_EMAIL = 1
        fun launch(context: Context) {
            launchWithBindAccount_AccountType(context, "", arrayOf(),0)
        }

        fun launchWithBindAccount_AccountType(context: Context, bindAccount: String?,array:Array<String?>?, type: Int) {
            context.startActivity(Intent(context, EditBindAccountActivity::class.java).putExtra(BIND_ACCOUNT, bindAccount).putExtra(PHONE_EMAIL,array).putExtra(ACCOUNT_TYPE, type))
        }
    }

    override fun getPresenter(): EditBindAccountPresenter {
        return EditBindAccountPresenter()
    }

    override fun onEditBindAccountSuccess() {
        finish()
    }


    override fun layoutContentId(): Int {
        return R.layout.activity_edit_bind_account
    }

    override fun initData() {

    }

    var isPhone = true//是否手机
    var isModify = true//是否修改
    var page = 1//默认处于第一页
    var account=""
    var phoneAndEmail:Array<String?>?=null
    override fun initView() {
        super.initView()
        account = intent.getStringExtra(BIND_ACCOUNT)!!
        phoneAndEmail= intent.getStringArrayExtra(PHONE_EMAIL)!!
        //tvBindAccount.text = account
        isPhone = intent.getIntExtra(ACCOUNT_TYPE, 0) == TYPE_PHONE
       /* if (TextUtils.isEmpty(account)) {//绑定账号
            isModify = false
            viewFlipper.showNext()
            page++
            viewFlipper.showNext()
            page++
            if (isPhone) {//手机
                getTitleView().text = getString(R.string.bind_telephone)
                tvBindTitle.text = getString(R.string.now_bind_telephone)
                tvAreaCode.visibility = View.VISIBLE
            } else {//邮箱
                getTitleView().text = getString(R.string.bind_email)
                tvBindTitle.text = getString(R.string.now_bind_email)
                tvAreaCode.visibility = View.GONE
                ivPhone.visibility = View.GONE
                etPhone.hint = getString(R.string.pls_input_email_address)
            }
        } else {//修改绑定账号*/
            getTitleView().text = getString(R.string.confirm_style)
            if (isPhone) {//手机
                var phoneAccount = getStringAccount(phoneAndEmail!![0],"phone")
                var emailAddress = getStringAccount(phoneAndEmail!![1],"email")
                tvBindAlert.text = getString(R.string.my_info_current_phone)
                tvVfTitle.text = getString(R.string.change_phone_num)
                tvBindTitle.text = getString(R.string.bind_new_phone_num)
                tvAreaCode.visibility = View.VISIBLE
                tvBindAccount.text = phoneAccount
                if(!TextUtils.isEmpty(phoneAccount)){
                    tvPhone.text = phoneAccount
                }else {
                    tvPhone.text = getString(R.string.unbind_account)
                    tvPhone.textColor = resources.getColor(R.color.mainColor)
                }
                if (!TextUtils.isEmpty(emailAddress)){
                    tvEmail.text = emailAddress
                }else{
                    tvEmail.text = getString(R.string.unbind_email)
                    tvEmail.textColor = resources.getColor(R.color.mainColor)
                }
            } else {//邮箱
                var phoneAccount = getStringAccount(phoneAndEmail!![0],"phone")
                var emailAddress = getStringAccount(phoneAndEmail!![1],"email")
                tvBindAlert.text = getString(R.string.current_bind_email)
                tvVfTitle.text = getString(R.string.change_you_email)
                tvBindTitle.text = getString(R.string.now_bind_new_email)
                tvAreaCode.visibility = View.GONE
                tvBindAccount.text = emailAddress
                if(!TextUtils.isEmpty(phoneAccount)){
                    tvPhone.text = phoneAccount
                }else{
                    tvPhone.text = getString(R.string.unbind_account)
                    tvPhone.textColor = resources.getColor(R.color.mainColor)
                }
                if (!TextUtils.isEmpty(emailAddress)){
                    tvEmail.text = emailAddress
                }else{
                    tvEmail.text = getString(R.string.unbind_email)
                    tvEmail.textColor = resources.getColor(R.color.mainColor)
                }
            }
        //}
        btnGetCode.enableUI()
        btnBindGetCode.disableUI()
    }

    override fun initListener() {
        tvByPhone.setOnClickListener(this)
        tvByEmail.setOnClickListener(this)
        btnGetCode.setOnClickListener(this)
        btnBindGetCode.setOnClickListener(this)
        btnNext.setOnClickListener(this)
        btnConfirm.setOnClickListener(this)
        getLeftBackView().ext_doubleClick(this)
        etVfcode.addTextChangedListener(textWatcher)
        etBindVfcode.addTextChangedListener(textWatcher)
        etPhone.addTextChangedListener(textWatcher)
        tvAreaCode.setOnClickListener(this)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showPrevious()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        etVfcode.removeTextChangedListener(textWatcher)
        etBindVfcode.removeTextChangedListener(textWatcher)
        etPhone.removeTextChangedListener(textWatcher)
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            var flag: Boolean
            when (page) {
                2 -> {
                    flag = TextUtils.isEmpty(etVfcode.text.toString())
                    btnNext.isEnabled = !flag
                    btnNext.setBackgroundResource(if(!flag)R.drawable.shape_bg_push_btn_red else R.drawable.shape_bg_push_btn)
                }
                3 -> {
                    flag = TextUtils.isEmpty(etPhone.text.toString())
                    if (!flag) btnBindGetCode.enableUI() else btnBindGetCode.disableUI()
                    flag = TextUtils.isEmpty(etBindVfcode.text.toString()) || TextUtils.isEmpty(etPhone.text.toString())
                    btnConfirm.isEnabled = !flag
                    btnConfirm.setBackgroundResource(if(!flag)R.drawable.shape_bg_push_btn_red else R.drawable.shape_bg_push_btn)
                }
            }

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
                REQUEST_CODE_REGISTER ->{

                }

                FOR_AREA_CODE->{
                    tvAreaCode.text="+"+data?.getStringExtra("area_code")
                }

                FOR_AREA_CODE_VF->{
                    //tvVfAreaCode.text="+"+data?.getStringExtra("area_code")
                }
            }
        }
    }

    override fun showError(errorMsg: String, errorCode: Int, type: String) {
        super.showError(errorMsg, errorCode, type)
        toast(errorMsg)
    }

    private fun getStringAccount(mAccount:String?,mType:String): String? {
        var account:String?=null
        if (!TextUtils.isEmpty(mAccount)){
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

}


