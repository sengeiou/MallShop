package com.epro.mall.ui.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.epro.mall.R
import com.epro.mall.listener.ClosePopGps
import com.epro.mall.mvp.model.bean.Location
import com.epro.mall.listener.PopupDismissEvent
import com.mike.baselib.fragment.BaseBottomPopup
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.AppUtils
import com.mike.baselib.utils.ext_createJsonKey
import kotlinx.android.synthetic.main.bottompopup_open_location_service.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class OpenLocationServiceBottomPopup : BaseBottomPopup(), View.OnClickListener {

    override fun onClick(p0: View?) {
        when (p0) {
            tvCancel -> {
                dismiss()
            }
            tvOpen -> {
                AppUtils.openLocationService(activity!!, FOR_OPEN_RESULT)
               // dismiss()
            }
        }
    }

    companion object {
        const val FOR_OPEN_RESULT=15
        fun newInstance(location: Location): OpenLocationServiceBottomPopup {
            val args = Bundle()
            args.putString(ext_createJsonKey(Location::class.java), AppBusManager.toJson(location))
            val fragment = OpenLocationServiceBottomPopup()
            fragment.setArguments(args)
            return fragment
        }
    }


    override fun initView() {
    }

    override fun initListener() {
        tvCancel.setOnClickListener(this)
        tvOpen.setOnClickListener(this)
    }


    override fun getLayoutId(): Int {
        return R.layout.bottompopup_open_location_service
    }

    override fun initData() {
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        EventBus.getDefault().post(PopupDismissEvent())
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onClosePopGps(event: ClosePopGps) {
        dismiss()
    }

}