package com.epro.mall.ui.view

import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.epro.mall.R

class PickupCodeDialog : Dialog {
    var tvDialogTitle: TextView? = null
    var tvDialogContent1: TextView? = null
    var tvDialogContent2: TextView? = null
    var tvDialogContent3: TextView? = null
    var tvDialogContent4: TextView? = null
    var ivDialogClose: ImageView? = null

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, themeStyle: Int) : super(context, themeStyle) {
        initView()
    }

    private fun initView() {
        setContentView(R.layout.dialog_picup_code)
        setCanceledOnTouchOutside(true)
        tvDialogTitle = findViewById(R.id.tvDialogTitle)
        tvDialogContent1 = findViewById(R.id.etDialogContent1)
        tvDialogContent2 = findViewById(R.id.etDialogContent2)
        tvDialogContent3 = findViewById(R.id.etDialogContent3)
        tvDialogContent4 = findViewById(R.id.etDialogContent4)
        ivDialogClose = findViewById(R.id.ivDialogClose)
    }

    class Builder(val context: Context) {
        var confirmListener: OnConfirmListener? = null
        var cancelListener: OnCancelListener? = null
        var title: String? = null
        var content: String? = null
        var canceledOnTouchOutside = true

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

        fun setCanceledOnTouchOutside(canceledOnTouchOutside: Boolean): Builder {
            this.canceledOnTouchOutside = canceledOnTouchOutside
            return this
        }

        fun create(): PickupCodeDialog {
            val dialog = PickupCodeDialog(context, R.style.Theme_AudioDialog)

            val code = this.content
            val codes = code?.toCharArray()
            dialog.tvDialogContent1?.text = codes?.get(0).toString()
            dialog.tvDialogContent2?.text = codes?.get(1).toString()
            dialog.tvDialogContent3?.text = codes?.get(2).toString()
            dialog.tvDialogContent4?.text = codes?.get(3).toString()
            dialog.ivDialogClose?.setOnClickListener(View.OnClickListener {
                dialog.dismiss()
            })
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