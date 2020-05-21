package com.epro.mall.ui.view

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import com.epro.mall.R
import com.mike.baselib.utils.AppContext
import kotlinx.android.synthetic.main.dialog_modify_num.*


class ModifyNumDialog: Dialog {

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, themeStyle: Int) : super(context, themeStyle) {
        initView()
    }

    @SuppressLint("NewApi")
    private fun initView() {
        setContentView(R.layout.dialog_modify_num)
        setCanceledOnTouchOutside(false)
        //        ivDialogCancel = findViewById(R.goodsId.iv_dialog_cancel);

        ivDialogMinus?.setOnClickListener(View.OnClickListener {
            var num=etDialogContent?.text.toString()
            if(!TextUtils.isEmpty(num)&&!("0".equals(num))){
                var buyNum=Integer.valueOf(etDialogContent?.text.toString())
                buyNum--
                etDialogContent?.setText(""+buyNum)
            }
        })

        ivDialogPlus?.setOnClickListener(View.OnClickListener {
            var num=etDialogContent?.text.toString()
            if(TextUtils.isEmpty(num)){
                var buyNum=1
                etDialogContent?.setText(""+buyNum)
            }else{
                var buyNum=Integer.valueOf(etDialogContent?.text.toString())
                buyNum++
                etDialogContent?.setText(""+buyNum)
            }
        })

        etDialogContent.focusable = View.FOCUSABLE
        etDialogContent.showSoftInputOnFocus = true
        etDialogContent.requestFocus()
        getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    class Builder(val context: Context) {
        var confirmListener: OnConfirmListener? = null
        var cancelListener: OnCancelListener? = null
        var title: String? = null
        var icon: Int? = 0
        var btConfirmText: String? = null
        var tvCancelText: String? = null
        var cancelIsVisibility: Boolean? = true

        fun setOnConfirmListener(confirmListener: OnConfirmListener): Builder {
            this.confirmListener = confirmListener
            return this
        }

        fun setOnCancelListener(cancelListener: OnCancelListener): Builder {
            this.cancelListener = cancelListener
            return this
        }

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setIcon(icon: Int): Builder {
            this.icon = icon
            return this
        }

        // 点击确定按钮的文字
        fun setConfirmText(btConfirmText: String): Builder {
            this.btConfirmText = btConfirmText
            return this
        }

        //取消按钮的文字
        fun setCancelText(tvCancelText: String): Builder {
            this.tvCancelText = tvCancelText
            return this
        }

        fun setCancelIconIsVisibility(cancelIsVisibility: Boolean): Builder {
            this.cancelIsVisibility = cancelIsVisibility
            return this
        }

        fun create(): ModifyNumDialog {
            val dialog = ModifyNumDialog(context,R.style.Theme_AudioDialog)
            if (!TextUtils.isEmpty(title)) {
                dialog.tvDialogTitle?.text = this.title
            } else {
                dialog.tvDialogTitle?.visibility = View.GONE
            }
            if (icon != 0) {
                dialog.ivDialogIcon?.setImageResource(this.icon!!)
            }

            dialog.btDialogConfirm?.text = this.btConfirmText ?: AppContext.getInstance().getString(R.string.modify_password_push)
            if (this.cancelIsVisibility!!) {
                dialog.tvDialogCancel?.text = this.tvCancelText ?: AppContext.getInstance().getString(R.string.delete_dialog_cancel)
            } else {
                dialog.tvDialogCancel?.visibility = View.GONE
            }

            if (cancelListener != null) {
                dialog.tvDialogCancel?.setOnClickListener { v -> cancelListener!!.onClick(dialog) }
            }
            if (confirmListener != null) {
                dialog.btDialogConfirm?.setOnClickListener { v -> confirmListener!!.onClick(dialog,dialog.etDialogContent?.text.toString()) }
            }
            return dialog
        }

    }

    // 点击弹窗取消按钮回调
    interface OnCancelListener {
        fun onClick(dialog: Dialog)
    }

    // 点击弹窗跳转回调
    interface OnConfirmListener {
        fun onClick(dialog: Dialog,buyNum:String)
    }

}