package com.epro.mall.ui.fragment

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.Item
import com.epro.mall.ui.adapter.PayModeAdapter
import com.mike.baselib.fragment.BaseBottomPopup
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.ext_createJsonKey
import kotlinx.android.synthetic.main.bottompopup_select_paymode.*

/**
 * 选择支付方式弹窗
 */
class SelectPayModeBottomPopup : BaseBottomPopup(), View.OnClickListener {
    override fun onClick(p0: View?) {
        when (p0) {
            ivClose -> {
                dismiss()
            }
        }
    }

    override fun initView() {
    }

    override fun initListener() {
        ivClose.setOnClickListener(this)
    }

    companion object {
        const val PAY_MODE = "pay_mode"
        fun newInstance(pay_mode: Int): SelectPayModeBottomPopup {
            val args = Bundle()
            args.putInt(PAY_MODE, pay_mode)
            val fragment = SelectPayModeBottomPopup()
            fragment.setArguments(args)
            return fragment
        }

        fun newInstance(): SelectPayModeBottomPopup {
            return newInstance(0)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.bottompopup_select_paymode
    }

    override fun initData() {
        rvPayModes.layoutManager = LinearLayoutManager(activity)
        val pay_mode = arguments?.getInt(PAY_MODE)
        rvPayModes.adapter = PayModeAdapter(activity!!, ArrayList(), pay_mode!!)
        (rvPayModes.adapter as PayModeAdapter).onItemClickListener = object : PayModeAdapter.OnItemClickListener {
            override fun onClick(item: Item) {
                arguments?.putString(ext_createJsonKey(Item::class.java), AppBusManager.toJson(item))
                dismiss()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        onPopupDismissListener?.onPopupDismiss(arguments)
    }

}