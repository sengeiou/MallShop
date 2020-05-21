package com.epro.mall.ui.activity;

import android.content.Context
import android.content.Intent
import android.view.View
import com.epro.mall.R
import com.epro.mall.mvp.contract.PhoneServiceTermsContract
import com.epro.mall.mvp.presenter.PhoneServiceTermsPresenter
import com.epro.mall.ui.fragment.CommonWebFragment
import com.mike.baselib.activity.BaseTitleBarCustomActivity
import com.mike.baselib.utils.AppConfig


class PhoneServiceTermsActivity : BaseTitleBarCustomActivity<PhoneServiceTermsContract.View, PhoneServiceTermsPresenter>(), PhoneServiceTermsContract.View {

    companion object{

        const val TAG = "PhoneService"
        const val USER_REGISTRATION_AGREEMENT = "0"
        const val TERMS_OF_SERVICE = "1"
        const val PRIVACY_POLICY = "2"

        fun launch(context: Context) {
            launchWithStr(context, "")
        }

        fun launchWithStr(context: Context, str: String) {
            context.startActivity(Intent(context, PhoneServiceTermsActivity::class.java).putExtra(TAG, str))
        }
    }
    override fun layoutContentId(): Int {
        return R.layout.activity_phone_server_terms
    }

    override fun getPresenter(): PhoneServiceTermsPresenter {
        return PhoneServiceTermsPresenter()
    }

    override fun onPhoneServiceTermsSuccess() {
    }

    override fun initData() {

    }


    override fun initView() {
        super.initView()
        val status =  intent.getStringExtra(TAG)
        initTitleView(status)
    }


  /*  https://shop.epro.com.hk/privacyPolicy
    https://shop.epro.com.hk/registrationAgreement
    https://shop.epro.com.hk/termsService*/

    private fun initTitleView(str: String) {
        if ("0".equals(str)){
            getTitleView().text = getString(R.string.user_agreement)
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContent, CommonWebFragment.newInstance("file:///android_asset/registration.html")).commitAllowingStateLoss()
            //supportFragmentManager.beginTransaction().replace(R.id.fragmentContent, CommonWebFragment.newInstance(AppConfig.getBaseurl()+"/registrationAgreement")).commitAllowingStateLoss()
        }else if ("1".equals(str)){
            getTitleView().text = getString(R.string.terms_of_service)
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContent, CommonWebFragment.newInstance("file:///android_asset/termsService.html")).commitAllowingStateLoss()
            // supportFragmentManager.beginTransaction().replace(R.id.fragmentContent, CommonWebFragment.newInstance(AppConfig.getBaseurl()+"/termsService")).commitAllowingStateLoss()
        }else if ("2".equals(str)){
            getTitleView().text = getString(R.string.privacy_policy)
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContent, CommonWebFragment.newInstance("file:///android_asset/privacyPolicy.html")).commitAllowingStateLoss()
            //supportFragmentManager.beginTransaction().replace(R.id.fragmentContent, CommonWebFragment.newInstance(AppConfig.getBaseurl()+"/privacyPolicy")).commitAllowingStateLoss()
        }
        getLeftBackView().visibility = View.VISIBLE
    }

    override fun initListener() {
    }
}


