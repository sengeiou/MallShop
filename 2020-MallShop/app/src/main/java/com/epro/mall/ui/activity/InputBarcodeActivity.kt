package com.epro.mall.ui.activity;

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.WindowManager
import com.epro.mall.R
import com.epro.mall.utils.MallUtils
import com.epro.mall.listener.InputResultEvent
import com.epro.mall.listener.SearchResultEvent
import com.mike.baselib.activity.BaseTitleBarSimpleActivity
import kotlinx.android.synthetic.main.activity_input_barcode.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class InputBarcodeActivity : BaseTitleBarSimpleActivity(), View.OnClickListener {
    override fun onClick(p0: View?) {
        when (p0) {
            btnAdd -> {
                if (etBarcode.text.toString().trim().isEmpty()) {
                    MallUtils.showToast(getString(R.string.pls_input_product_bar),this)
                    return
                }
                val event = InputResultEvent()
                event.result = etBarcode.text.toString().trim()
                EventBus.getDefault().post(event)
            }
        }

    }

    companion object {
        const val TAG = "InputBarcode"
        fun launchForResult(activity: Activity, requestCode: Int) {
            activity.startActivityForResult(Intent(activity, InputBarcodeActivity::class.java), requestCode)
        }
    }


    override fun layoutContentId(): Int {
        return R.layout.activity_input_barcode
    }

    override fun initData() {
    }

    override fun initView() {
        super.initView()
        getTitleView().text = getString(R.string.enter_barcode)
        etBarcode.requestFocus()
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    override fun initListener() {
        btnAdd.setOnClickListener(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSearchResultEvent(event: SearchResultEvent) {
        if (event.isEmpty) {
            //MallUtils.showToast("商品条码无法识别",this)
        } else {
            //MallUtils.showToast("添加商品成功",this)
            finish()
        }
    }
}


