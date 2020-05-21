package com.epro.mall.ui.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import cn.sharesdk.facebook.Facebook
import cn.sharesdk.framework.Platform
import cn.sharesdk.framework.PlatformActionListener
import cn.sharesdk.framework.ShareSDK
import cn.sharesdk.twitter.Twitter
import com.epro.mall.R
import com.epro.mall.mvp.contract.AccountAssociationContract
import com.epro.mall.mvp.model.bean.AccountAssociationBean
import com.epro.mall.mvp.model.bean.AssociationAccountListBean
import com.epro.mall.mvp.model.bean.UnbindAccountBean
import com.epro.mall.mvp.presenter.AccountAssociationPresenter
import com.epro.mall.ui.view.CommonDialog
import com.epro.mall.utils.MallConst
import com.mike.baselib.activity.BaseTitleBarCustomActivity
import com.mike.baselib.utils.ext_loadGif
import kotlinx.android.synthetic.main.activity_account_association.*
import org.jetbrains.anko.textColor
import com.mike.baselib.utils.toast
import java.util.HashMap

class AccountAssociationActivity :BaseTitleBarCustomActivity<AccountAssociationContract.View,AccountAssociationPresenter>(),AccountAssociationContract.View, View.OnClickListener {

    var providerIdFb:String?=null
    var providerIdTw:String?=null
    override fun associationAccountListSuccess(result: List<AssociationAccountListBean.Result>) {
        tvBook.text = getString(R.string.association_name_2_ok)
        tvBook.textColor = resources.getColor(R.color.mainColor)
        tvAssociation2.text = getString(R.string.association_name_2_ok)
        tvAssociation2.textColor = resources.getColor(R.color.mainColor)
        for (i in result.indices){
            if (MallConst.LOGIN_TYPE_FB.equals(result[i].providerType)){
                providerIdFb = result[i].providerId
                tvBook.text = result[i].name
                tvBook.textColor = resources.getColor(R.color.secondaryTextColor)
            }else if (MallConst.LOGIN_TYPE_TW.equals(result[i].providerType)){
                providerIdTw = result[i].providerId
                tvAssociation2.text = result[i].name
                tvAssociation2.textColor = resources.getColor(R.color.secondaryTextColor)
            }
        }
    }

    override fun onClick(p0: View?) {
       when(p0){
           faceAssociation ->{
               //未绑定成功跳转绑定
               var tvbook = tvBook.text.toString().trim()
               if (getString(R.string.association_name_2_ok).equals(tvbook)){
                   getMultipleStatusView().showLoading()
                   getMultipleStatusView().findViewById<ImageView>(R.id.ivLoading).ext_loadGif(R.mipmap.gif_loading)
                   thirdLogin(Facebook.NAME, false)
               }else{
                   //绑定成功弹框解绑
                   showDeleteDailog(providerIdFb!!)
               }
           }

           twitterAssociation ->{
               var tvAssociation2 = tvAssociation2.text.toString().trim()
               if (getString(R.string.association_name_2_ok).equals(tvAssociation2)){
                   getMultipleStatusView().showLoading()
                   getMultipleStatusView().findViewById<ImageView>(R.id.ivLoading).ext_loadGif(R.mipmap.gif_loading)
                   thirdLogin(Twitter.NAME, true)
               }else{
                   //绑定成功弹框解绑
                   showDeleteDailog(providerIdTw!!)
               }
           }
       }
    }



    companion object{
        const val TAG = "Association"
        fun launch(context: Context) {
            launchWithStr(context, "")
        }

        fun launchWithStr(context: Context, str: String) {
            context.startActivity(Intent(context, AccountAssociationActivity::class.java).putExtra(TAG, str))
        }
    }

    override fun layoutContentId(): Int {
        return R.layout.activity_account_association
    }

    override fun initData() {
        getTitleView().text =getString(R.string.account_association)
    }

    override fun initView() {
        super.initView()
        mPresenter.associationAccountList(MallConst.ASSOCIATION_ACCOUNT_LIST)
    }
    override fun initListener() {
        faceAssociation.setOnClickListener(this)
        twitterAssociation.setOnClickListener(this)
        sinaAssociation.setOnClickListener(this)
    }

    override fun getPresenter(): AccountAssociationPresenter {
       return AccountAssociationPresenter()
    }

    private fun showDeleteDailog(providerId:String) {
        CommonDialog.Builder(this)
                .setContent(getString(R.string.unbind_association))
                .setCancelText(getString(R.string.delete_dialog_cancel))
                .setConfirmText(getString(R.string.delete_dialog_delete))
                .setOnConfirmListener(object : CommonDialog.OnConfirmListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                         mPresenter.unBindAccount(MallConst.UNBIND_ACCOUNT,providerId)
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

    override fun onAssociationSuccess(result:AccountAssociationBean.Result) {
        mPresenter.associationAccountList(MallConst.ASSOCIATION_ACCOUNT_LIST)
        toast(getString(R.string.bind_account_success))
    }

    override fun unBindAssociation(result: UnbindAccountBean) {
        mPresenter.associationAccountList(MallConst.ASSOCIATION_ACCOUNT_LIST)
        toast(getString(R.string.unbind_account_success))
    }

    private fun thirdLogin(platformName: String, isSupportWeb: Boolean) {
        val platform = ShareSDK.getPlatform(platformName)
        if (platform == null) {
            toast("Platform is null")
            return
        }
        Toast.makeText(this, platformName +getString(R.string.start_login), Toast.LENGTH_SHORT).show()
        if (!isSupportWeb) {
            if (!platform.isClientValid) {
                //客户端不可用
                toast(getString(R.string.pls_download)+"$platformName")
                return
            }
        }
        if (platform.isAuthValid) {
            platform.removeAccount(true)
        }
        platform.platformActionListener = object : PlatformActionListener {
            override fun onComplete(p0: Platform?, p1: Int, p2: HashMap<String, Any>?) {
                logTools.d("onComplete")
                logTools.toJson(p2)
                logTools.d(p0?.db?.token)
                logTools.d(p0?.db?.tokenSecret)
                logTools.d(p0?.db?.userId)
                val userId = p0?.db?.userId
                val userName = p0?.db?.userName
                when (platformName) {
                    Facebook.NAME -> {
                         mPresenter.associationAccount(MallConst.ASSOCIATION_ACCOUNT,userId!!,MallConst.LOGIN_TYPE_FB,userName!!)
                    }
                    Twitter.NAME -> {
                        mPresenter.associationAccount(MallConst.ASSOCIATION_ACCOUNT,userId!!,MallConst.LOGIN_TYPE_TW,userName!!)
                    }
                }
            }

            override fun onCancel(p0: Platform?, p1: Int) {
                logTools.d("onCancel")
                logTools.d(p0?.name)
                toast(getString(R.string.cancellation_of_certification))
                getMultipleStatusView().showContent()
            }

            override fun onError(p0: Platform?, p1: Int, p2: Throwable?) {
                logTools.d("onError")
                logTools.d(p2?.message)
                logTools.d(p0?.name)
                toast(getString(R.string.failed_of_certification))
                getMultipleStatusView().showContent()
            }
        }
        platform.SSOSetting(false)
        platform.showUser(null)
    }

}