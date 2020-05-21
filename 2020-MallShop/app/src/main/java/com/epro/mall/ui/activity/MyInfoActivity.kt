package com.epro.mall.ui.activity;

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.epro.mall.R
import com.epro.mall.listener.MyInfoChangeEvent
import com.epro.mall.mvp.contract.MyInfoContract
import com.epro.mall.mvp.model.bean.MyInfoBean
import com.epro.mall.mvp.model.bean.UpdateImageBean
import com.epro.mall.mvp.presenter.MyInfoPresenter
import com.epro.mall.ui.activity.EditBindAccountActivity.Companion.TYPE_EMAIL
import com.epro.mall.ui.activity.EditBindAccountActivity.Companion.TYPE_PHONE
import com.epro.mall.utils.MallConst
import com.mike.baselib.activity.BaseTitleBarCustomActivity
import com.mike.baselib.utils.ext_loadCircleImage
import com.mike.baselib.utils.ext_loadImageWithDomain
import kotlinx.android.synthetic.main.activity_my_info.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.textColor
import java.io.File

/**
 * 我的个人信息
 */
class MyInfoActivity : BaseTitleBarCustomActivity<MyInfoContract.View, MyInfoPresenter>(), MyInfoContract.View, View.OnClickListener {

    override fun onModifyImageSuccess() {
        image = avatar
        var msg = MyInfoChangeEvent()
        msg.image = image
        EventBus.getDefault().postSticky(msg)
    }

    var avatar:String?=null
    override fun onUpdateImageSucess(result: UpdateImageBean) {
          avatar = result.result[0]
          mPresenter.modifyImage(MallConst.MODIFY_IMAGE,avatar!!)
    }

    override fun onClick(p0: View?) {
        when(p0){

            ivAvatar->{
                SelectImageActivity.launchWithWidth_Height(this,300,300, FOR_IMAGE)
            }
            rlPhone->{
                // if (TextUtils.isEmpty(phone)){
                //     AccountBindActivity.launchWithLatLng(this,BIND_PHONE)
                // }else{
                    EditBindAccountActivity.launchWithBindAccount_AccountType(this,phone,array,TYPE_PHONE)
               // }
            }
            rlEmail->{
               // if (TextUtils.isEmpty(email)){
               //     AccountBindActivity.launchWithLatLng(this,BIND_EMAIL)
               // }else{
                    EditBindAccountActivity.launchWithBindAccount_AccountType(this,email,array,TYPE_EMAIL)
              //  }
            }
            //左边返回键
            getLeftView()->{

                finish()
            }
        }
    }

    companion object {
        const val TAG = "MyInfo"
        val FOR_IMAGE=5
        fun launch(context: Context) {
            launchWithStr(context, "")
        }

        fun launchWithStr(context: Context, str: String) {
            context.startActivity(Intent(context, MyInfoActivity::class.java).putExtra(TAG, str))
        }
    }

    override fun getPresenter(): MyInfoPresenter {
        return MyInfoPresenter()
    }

    var phone :String?=null
    var email:String?=null
    var array :Array<String?>?=null
    var image:String?=null
    var userName:String?=null
    override fun onMyInfoSuccess(data: MyInfoBean.Result) {
        tvAccount.text = data.username+getString(R.string.not_changeable)
        if ("".equals(data.mobile)){
            tvPhone.text = getString(R.string.unbind_account)
            tvPhone.textColor = resources.getColor(R.color.mainColor)
        }else{
            tvPhone.text = getStringAccount(data.mobile,"phone")
        }
       if ("".equals(data.email)){
           tvEmail.text = getString(R.string.unbind_email)
           tvEmail.textColor = resources.getColor(R.color.mainColor)
       }else{
           tvEmail.text = getStringAccount(data.email,"email")
       }

        phone = data.mobile
        email = data.email
        array = arrayOf(phone,email)
        userName = data.username
        if ( !"".equals(data.avatar) ){
            ivAvatar.ext_loadImageWithDomain(data.avatar)
        }else{
            ivAvatar.setImageResource(R.mipmap.default_image)
        }
    }

    override fun layoutContentId(): Int {
        return R.layout.activity_my_info
    }

    override fun initData() {

    }

    override fun initView() {
        super.initView()
        getTitleView().text = getString(R.string.my_info_personal_information)
        mPresenter.MyInfo(MallConst.GET_MY_INFO)
    }

    override fun initListener() {
        ivAvatar.setOnClickListener(this)
        rlPhone.setOnClickListener(this)
        rlEmail.setOnClickListener(this)
        getLeftView().setOnClickListener(this)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                FOR_IMAGE->{
                    val file = data!!.getSerializableExtra("image") as File
                    ivAvatar.ext_loadCircleImage(file.path)
                    mPresenter.updateImage(MallConst.UPDATE_IMAGE,file!!,0)
                }
            }
        }
    }

    private fun getStringAccount(mAccount:String,mType:String): String? {
        var account:String?=null
        if (TextUtils.isEmpty(mAccount)){
            return ""
        }
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
        return account
    }

}


