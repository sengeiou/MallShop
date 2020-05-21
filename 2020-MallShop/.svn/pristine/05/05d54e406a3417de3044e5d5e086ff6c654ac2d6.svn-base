package com.epro.mall.ui.view

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.epro.mall.R
import com.mike.baselib.utils.AppContext

class ConfirmAddressDialog: Dialog {
    var tvDialogContent: TextView? = null
    var tvDialogContent1: TextView? = null
    var tvDialogContent2: TextView? = null
    var tvDialogContent3: TextView? = null
    var tvDialogContent4: TextView? = null
    var btDialogConfirm: Button? = null //确定按钮可通过外部自定义按钮内容
    var btDialogCancel: Button? = null //取消


    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, themeStyle: Int) : super(context, themeStyle) {
        initView()
    }

    private fun initView() {
        setContentView(R.layout.dialog_comfirm_address)
        setCanceledOnTouchOutside(true)
        tvDialogContent = findViewById(R.id.etDialogContent)
        tvDialogContent1 = findViewById(R.id.tv_dialog_content1)
        tvDialogContent2 = findViewById(R.id.tv_dialog_content2)
        tvDialogContent3 = findViewById(R.id.tv_dialog_content3)
        tvDialogContent4= findViewById(R.id.tv_dialog_content4)
        btDialogConfirm = this.findViewById(R.id.btDialogConfirm)
        btDialogCancel = findViewById(R.id.bt_dialog_cancel)
    }

    class Builder(val context: Context) {
        var confirmListener: OnConfirmListener? = null
        var cancelListener: OnCancelListener? = null
        var content: String? = null
        var content1: String? = null
        var content2: String? = null
        var content3: String? = null
        var content4: String? = null
        var confirmText: String? = null
        var cancelText: String? = null
        var cancelIsVisibility:Boolean=true

        fun setOnConfirmListener(confirmListener: OnConfirmListener): Builder {
            this.confirmListener = confirmListener
            return this
        }

        fun setOnCancelListener(cancelListener: OnCancelListener): Builder {
            this.cancelListener = cancelListener
            return this
        }

        fun setContent(content: String): Builder {
            this.content = content
            return this
        }
        fun setContent1(content: String): Builder {
            this.content1 = content
            return this
        }
        fun setContent2(content: String): Builder {
            this.content2 = content
            return this
        }
        fun setContent3(content: String): Builder {
            this.content3 = content
            return this
        }
        fun setContent4(content: String): Builder {
            this.content4 = content
            return this
        }
        // 点击确定按钮的文字
        fun setConfirmText(confirmText: String): Builder {
            this.confirmText = confirmText
            return this
        }

        fun setCancelIsVisibility(cancelIsVisibility:Boolean):Builder{
            this.cancelIsVisibility=cancelIsVisibility
            return this
        }

        //取消按钮的文字
        fun setCancelText(cancelText: String): Builder {
            this.cancelText = cancelText
            return this
        }

        fun create(): ConfirmAddressDialog {
            val dialog = ConfirmAddressDialog(context,R.style.Theme_AudioDialog)
            dialog.tvDialogContent?.text = this.content
            dialog.tvDialogContent1?.text = this.content1
            dialog.tvDialogContent2?.text = this.content2
            dialog.tvDialogContent3?.text = this.content3
            dialog.tvDialogContent4?.text = this.content4

            dialog.btDialogConfirm?.text = this.confirmText ?: AppContext.getInstance().getString(R.string.modify_password_push)
            if (this.cancelIsVisibility!!) {
                dialog.btDialogCancel?.text = this.cancelText ?: AppContext.getInstance().getString(R.string.delete_dialog_cancel)
            } else {
                dialog.btDialogCancel?.visibility = View.GONE
            }

            if (cancelListener != null) {
                dialog.btDialogCancel?.setOnClickListener { v -> cancelListener!!.onClick(dialog) }
            }
            if (confirmListener != null) {
                dialog.btDialogConfirm?.setOnClickListener { v -> confirmListener!!.onClick(dialog) }
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
        fun onClick(dialog: Dialog)
    }

}