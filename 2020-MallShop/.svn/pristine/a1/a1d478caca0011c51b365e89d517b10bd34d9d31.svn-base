package com.epro.mall.ui.fragment

import android.os.Bundle
import android.view.View
import com.epro.mall.R
import com.epro.mall.mvp.model.bean.Location
import com.epro.mall.utils.MallMapUtils
import com.mike.baselib.fragment.BaseBottomPopup
import com.mike.baselib.utils.*
import kotlinx.android.synthetic.main.bottompopup_select_map.*

/**
 * 选择地图底部弹窗 高德 谷歌
 */
class SelectMapBottomPopup : BaseBottomPopup(), View.OnClickListener {
    override fun onClick(p0: View?) {
        when (p0) {
            tvGaode -> {
                val location = AppBusManager.fromJsonWithClassKey(arguments!!, Location::class.java)
                MallMapUtils.openGaoDeMap(context!!, location!!.latitude.toDouble(), location.longitude.toDouble(), location.address)
                dismiss()
            }
            tvBaidu -> {
                val location = AppBusManager.fromJsonWithClassKey(arguments!!, Location::class.java)
                MallMapUtils.openBaiduMap(context!!, location!!.latitude.toDouble(), location.longitude.toDouble(), location.address)
                dismiss()
            }
            tvCancel -> {
                dismiss()
            }
        }
    }

    companion object {
        fun newInstance(location: Location): SelectMapBottomPopup {
            val args = Bundle()
            args.putString(ext_createJsonKey(Location::class.java), AppBusManager.toJson(location))
            val fragment = SelectMapBottomPopup()
            fragment.setArguments(args)
            return fragment
        }
    }


    override fun initView() {
    }

    override fun initListener() {
        tvGaode.setOnClickListener(this)
        tvCancel.setOnClickListener(this)
        tvBaidu.setOnClickListener(this)
    }


    override fun getLayoutId(): Int {
        return R.layout.bottompopup_select_map
    }

    override fun initData() {
    }

}