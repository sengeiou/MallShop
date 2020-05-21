package com.epro.mall.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.epro.mall.R
import com.epro.mall.ui.fragment.SelectPhotoBottomPopup
import com.mike.baselib.activity.BaseSimpleActivity
import com.mike.baselib.fragment.BaseBottomPopup
import com.mike.baselib.utils.ext_choosePhoto
import com.mike.baselib.utils.ext_startPhotoCrop
import com.mike.baselib.utils.ext_takePhoto
import com.mike.baselib.utils.toast
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File

class SelectImageActivity : BaseSimpleActivity(), View.OnClickListener {
    override fun onClick(p0: View?) {
        when (p0) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        intent.putExtra("cannotRequestedOrientation", true)
        super.onCreate(savedInstanceState)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    cropUri = ext_startPhotoCrop(imageUri!!, imageWidth, imageHeight, CROP_REQUEST_CODE)
                }
                GALLERY_REQUEST_CODE -> {
                    cropUri = ext_startPhotoCrop(data?.data!!, imageWidth, imageHeight, CROP_REQUEST_CODE)
                }
                CROP_REQUEST_CODE -> {
                    Luban.with(this).load(cropUri?.path).ignoreBy(100).setTargetDir("").setCompressListener(object : OnCompressListener {
                        override fun onSuccess(file: File?) {
                            if (file != null) {
                                setResult(Activity.RESULT_OK, Intent().putExtra("image", file))
                            } else {
                                toast(getString(R.string.image_compression_failed))
                            }
                            finish()
                        }

                        override fun onError(e: Throwable?) {
                            toast(getString(R.string.image_compression_failed))
                            finish()
                        }

                        override fun onStart() {
                        }
                    }).launch()
                }
            }
        } else {
            finish()
        }
    }

    private fun showSelectPicDialog() {
        BaseBottomPopup.Builder(SelectPhotoBottomPopup.newInstance())
                .setOnPopupDismissListener(object : BaseBottomPopup.OnPopupDismissListener {
                    override fun onPopupDismiss(bundle: Bundle?) {
                        if (bundle != null) {
                            var mode = bundle.getString("MODE")
                            if (mode != null) {
                                if ("0".equals(mode)) {
                                    takePhoto()
                                } else if ("1".equals(mode)) {
                                    choosePhto()
                                }
                            } else {
                                finish()
                            }
                        }
                    }
                })
                .create()
                .show(supportFragmentManager, "select_mode")
    }

    companion object {

        // 拍照回传码
        val CAMERA_REQUEST_CODE = 100
        // 相册选择回传吗
        val GALLERY_REQUEST_CODE = 101
        //裁剪
        val CROP_REQUEST_CODE = 102
        private const val RC_READ_AND_WRITE_EXTERNAL_STORAGE = 10
        private const val RC_CAMERA = 11

        //相片路径
        var imageUri: Uri? = null
        //裁剪后路径
        var cropUri: Uri? = null
        const val IMAGE_WIDTH = "width"
        const val IMAGE_HEIGHT = "height"
        fun launchWithWidth_Height(activity: Activity, width: Int, height: Int, requestCode: Int) {
            activity.startActivityForResult(Intent(activity, SelectImageActivity::class.java)
                    .putExtra(IMAGE_WIDTH, width)
                    .putExtra(IMAGE_HEIGHT, height), requestCode)
        }
    }

    override fun layoutContentId(): Int {
        return R.layout.activity_select_image
    }

    override fun initData() {

    }

    override fun initListener() {

    }

    var imageWidth = 100
    var imageHeight = 100
    override fun initView() {
        super.initView()
        imageWidth = intent.getIntExtra(IMAGE_WIDTH, 100)
        imageHeight = intent.getIntExtra(IMAGE_HEIGHT, 100)
        showSelectPicDialog()
    }

    @AfterPermissionGranted(RC_CAMERA)
    private fun takePhoto() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // Already have permission, do the thing
            imageUri = ext_takePhoto(CAMERA_REQUEST_CODE)
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.need_open_camera_permission),
                    RC_CAMERA, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    @AfterPermissionGranted(RC_READ_AND_WRITE_EXTERNAL_STORAGE)
    private fun choosePhto() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // Already have permission, do the thing
            ext_choosePhoto(GALLERY_REQUEST_CODE)
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.need_read_and_write_file_permissions),
                    RC_READ_AND_WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

}