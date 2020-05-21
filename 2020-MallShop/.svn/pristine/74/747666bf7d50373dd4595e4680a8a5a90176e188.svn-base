package com.epro.mall.ui.view

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.TextView
import com.epro.mall.R
import com.mike.baselib.utils.AppContext

class DeleteDialog : Dialog {

    var tvInfo :TextView?=null
    var tvCancel:TextView?=null
    var tvDelete:TextView?=null
    

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, themeStyle: Int) : super(context, themeStyle) {
        initView()
    }

    private fun initView(){
        setContentView(R.layout.activity_delete_dialog)
        setCanceledOnTouchOutside(true)
        tvInfo=  findViewById(R.id.tvInfo)
        tvCancel = findViewById(R.id.tvCancel)
        tvDelete= findViewById(R.id.tvDelete)
    }

    class Builder(val context: Context){
        var confirmListener: DeleteDialog.OnConfirmListener? = null
        var cancelListener: DeleteDialog.OnCancelListener? = null
        var title: String? = null
        var content: String? = null
        var confirmText: String? = null
        var cancelText: String? = null
        var cancelIsVisibility: Boolean = true
        var canceledOnTouchOutside=true

        fun setOnConfirmListener(confirmListener: OnConfirmListener):Builder {
            this.confirmListener = confirmListener
            return this
        }

        fun setOnCancelListener(cancelListener: OnCancelListener):Builder {
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

        // 点击确定按钮的文字
        fun setConfirmText(confirmText: String):Builder {
            this.confirmText = confirmText
            return this
        }

        fun setCancelIsVisibility(cancelIsVisibility: Boolean):Builder {
            this.cancelIsVisibility = cancelIsVisibility
            return this
        }

        //取消按钮的文字
        fun setCancelText(cancelText: String):Builder {
            this.cancelText = cancelText
            return this
        }

        fun  setCanceledOnTouchOutside(canceledOnTouchOutside:Boolean): Builder {
            this.canceledOnTouchOutside=canceledOnTouchOutside
            return this
        }

        fun create(): DeleteDialog {
            val dialog = DeleteDialog(context, R.style.Theme_AudioDialog)

            dialog.tvInfo?.text = this.content

            dialog.tvDelete?.text = this.confirmText ?: AppContext.getInstance().getString(R.string.modify_password_push)
            if (this.cancelIsVisibility!!) {
                dialog.tvCancel?.text = this.cancelText ?: AppContext.getInstance().getString(R.string.delete_dialog_cancel)
            } else {
                dialog.tvCancel?.visibility = View.GONE
            }

            if (cancelListener != null) {
                dialog.tvCancel?.setOnClickListener { v -> cancelListener!!.onClick(dialog) }
            }
            if (confirmListener != null) {
                dialog.tvDelete?.setOnClickListener { v -> confirmListener!!.onClick(dialog) }
            }

            dialog.setCanceledOnTouchOutside(canceledOnTouchOutside)
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