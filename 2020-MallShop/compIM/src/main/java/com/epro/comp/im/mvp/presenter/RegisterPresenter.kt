package com.epro.comp.im.mvp.presenter

import android.text.TextUtils
import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.mike.baselib.utils.ValidateUtils
import com.epro.comp.im.R
import com.epro.comp.im.mvp.contract.RegisterContract
import com.epro.comp.im.mvp.model.RegisterModel
import com.mike.baselib.net.exception.ErrorStatus

class RegisterPresenter:BasePresenter<RegisterContract.View>(),RegisterContract.Presenter {




    private val model by lazy { RegisterModel() }


    override fun getPhoneVerifyCode(dial:String,mobile:String,type:String) {
    }

    fun checkParam(mobile: String):Boolean{
        if(TextUtils.isEmpty(mobile)){
            toast(R.string.comp_im_phone_not_empty)
            return false
        }
        if(!ValidateUtils.validatePhoneNo(mobile)){
            toast(R.string.comp_im_phone_format_error)
            return false
        }
        return true
    }
    fun checkParam(loginname:String,name:String,details:String):Boolean{
        if(TextUtils.isEmpty(loginname)){
            toast(R.string.comp_im_phone_not_empty)
            return false
        }
        if(!ValidateUtils.validatePhoneNo(loginname)){
            toast(R.string.comp_im_phone_format_error)
            return false
        }
        if(TextUtils.isEmpty(name)){
            toast("姓名不能为空")
            return false
        }
        if(TextUtils.isEmpty(details)){
            toast("单位名称不能为空")
            return false
        }
        return true
    }


    override fun register(loginname:String,name:String,details:String,type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.register(loginname,name,details)
                .subscribe({
                    bean->
                    mRootView?.registerSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    fun checkParam(mobile:String,code:String,isGotVfcode:Boolean,password:String,repassword:String,agent_code:String,agree_terms :String):Boolean{

        if(TextUtils.isEmpty(mobile)){
            toast(R.string.comp_im_phone_not_empty)
            return false
        }
        if(!ValidateUtils.validatePhoneNo(mobile)){
            toast(R.string.comp_im_phone_format_error)
            return false
        }
        if(!isGotVfcode){
            toast(R.string.comp_im_get_vfcode_first)
            return false
        }
        if(TextUtils.isEmpty(code)){
            toast(R.string.comp_im_vfcode_not_empty)
            return false
        }
        if(!ValidateUtils.validateVfcode(code)){
            toast(R.string.comp_im_vfcode_format_error)
            return false
        }
        if(TextUtils.isEmpty(password)){
            toast(R.string.comp_im_password_not_empty)
            return false
        }
        if(!ValidateUtils.validatePassword(password)){
            toast(R.string.comp_im_password_format_error)
            return false
        }
        if(TextUtils.isEmpty(repassword)){
            toast(R.string.comp_im_repassword_not_empty)
            return false
        }
        if(!ValidateUtils.validatePassword(repassword)){
            toast(R.string.comp_im_repassword_format_error)
            return false
        }
        if(!(password.equals(repassword))){
            toast(R.string.comp_im_two_password_not_same)
            return false
        }
        if(TextUtils.isEmpty(agent_code)){
            toast(R.string.comp_im_proxy_id_not_empty)
            return false
        }
        if(!("1".equals(agree_terms))){
            toast(R.string.comp_im_agree_service_protocol)
            return false
        }
        return true
    }
}