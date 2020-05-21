package com.epro.mall.ui.view

import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.epro.mall.R
import com.mike.baselib.utils.AppContext

class SetNumDialog: Dialog {
    var tvTitle: TextView? = null
    var etDialogContent: EditText? = null
    var btDialogConfirm: Button? = null //确定按钮可通过外部自定义按钮内容
    var btDialogCancel: Button? = null //取消


    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, themeStyle: Int) : super(context, themeStyle) {
        initView()
    }

    private fun initView() {
        setContentView(R.layout.dialog_set_num)
        setCanceledOnTouchOutside(true)
        tvTitle = findViewById(R.id.tvDialogTitle)
        etDialogContent = findViewById(R.id.et_dialog_content)
        btDialogConfirm = findViewById(R.id.btDialogConfirm)
        btDialogCancel = findViewById(R.id.bt_dialog_cancel)
    }

    class Builder(val context: Context) {
        var confirmListener: OnConfirmListener? = null
        var cancelListener: OnCancelListener? = null
        var title: String? = null
        var content: String? = null
        var hint:String?=null
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

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setContent(content: String): Builder {
            this.content = content
            return this
        }
        fun setHint(hint: String): Builder {
            this.hint = hint
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

        fun create(): SetNumDialog {
            val dialog = SetNumDialog(context,R.style.Theme_AudioDialog)
            if (!TextUtils.isEmpty(title)) {
                dialog.tvTitle?.text = this.title
            } else {
                dialog.tvTitle?.visibility = View.GONE
            }

            dialog.etDialogContent?.setText(this.content)
            dialog.etDialogContent?.setHint(this.hint)

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
        fun onClick(dialog: Dialog,content:String)
    }

}