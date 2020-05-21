package com.epro.mall.ui.fragment

import android.Manifest
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import com.epro.mall.R
import com.epro.mall.ui.MainActivity
import com.epro.mall.ui.view.CommonDialog
import com.mike.baselib.fragment.BaseSimpleFragment
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.DeviceUidGenerator
import com.mike.baselib.utils.Md5
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

/**
 * 飞屏
 */
class SplashFragment : BaseSimpleFragment() {
    companion object {
        const val TAG = "SplashFragment"
        const val RC_LOCATION = 108
        const val RC_PHONE_STATE = 109
        fun newInstance(str: String): SplashFragment {
            val args = Bundle()
            args.putString(TAG, str)
            val fragment = SplashFragment()
            fragment.setArguments(args)
            return fragment
        }

        fun newInstance(): SplashFragment {
            return newInstance("")
        }
    }

    override fun layoutContentId(): Int {
        return R.layout.fragment_splash
    }

    override fun lazyLoad() {
    }

    override fun initView() {
        super.initView()
        if (!AppBusManager.isHasNeedPerms()) {
            requestLocation()
        }
        // MallMapUtils.test()
    }

    private fun goMainPage() {
        logTools.d(activity)
        activity?.supportFragmentManager!!.beginTransaction().remove(this).commitAllowingStateLoss()
        requestPhoneState()
        logTools.d("do here1")
        if (activity is MainActivity) {
            (activity as MainActivity).initTab()
            (activity as MainActivity).switchFragment()
            logTools.d("do here2")
        }
        logTools.d("do here3")
        activity?.window!!.setBackgroundDrawableResource(android.R.color.white)
    }


    @AfterPermissionGranted(RC_LOCATION)
    private fun requestLocation() {
        if (EasyPermissions.hasPermissions(activity!!, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE)) {
            goMainPage()
            AppBusManager.setHasNeedPerms(true)
            // Already have permission, do the thing
        } else {
            // Do not have permissions, request them now
            AppBusManager.setHasNeedPerms(false)
            EasyPermissions.requestPermissions(this, getString(R.string.pls_location_parms),
                    RC_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE)
        }
    }


    @AfterPermissionGranted(RC_PHONE_STATE)
    private fun requestPhoneState() {
        if (EasyPermissions.hasPermissions(activity!!, Manifest.permission.READ_PHONE_STATE)) {
            createUuid()
        } else {
            // Do not have permissions, request them now
            AppBusManager.setHasNeedPerms(false)
            EasyPermissions.requestPermissions(this, "请求电话权限",
                    RC_PHONE_STATE,Manifest.permission.READ_PHONE_STATE)
        }
    }

    private fun createUuid() {
        var uuid = AppBusManager.getUuid()
        uuid = if (TextUtils.isEmpty(uuid)) Md5.digest32(DeviceUidGenerator.generate(context)) else uuid
        AppBusManager.setUuid(uuid)
    }


    override fun initData() {
    }

    override fun initListener() {
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        super.onPermissionsDenied(requestCode, perms)
        AppBusManager.setHasNeedPerms(false)
        showPermsDialog()
    }

    private fun showPermsDialog() {
        CommonDialog.Builder(activity!!)
                .setContent(getString(R.string.need_more_parms))
                .setOnConfirmListener(object : CommonDialog.OnConfirmListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                        requestLocation()
                    }
                })
                .setOnCancelListener(object : CommonDialog.OnCancelListener {
                    override fun onClick(dialog: Dialog) {
                        activity?.finish()
                        AppBusManager.setHasNeedPerms(false)
                    }
                })
                .create()
                .show()
    }

}