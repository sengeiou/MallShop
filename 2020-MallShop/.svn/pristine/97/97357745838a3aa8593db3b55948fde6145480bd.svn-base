package com.epro.mall.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Vibrator
import android.util.Log
import android.view.View
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil
import cn.bingoogolapple.qrcode.core.BarcodeType
import cn.bingoogolapple.qrcode.core.QRCodeView
import cn.bingoogolapple.qrcode.zbar.ZBarView
import com.epro.mall.R
import com.epro.mall.listener.ScanResultEvent
import com.epro.mall.listener.SearchResultEvent
import com.mike.baselib.activity.BaseSimpleActivity
import com.mike.baselib.utils.AppContext
import com.mike.baselib.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_scan.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import com.mike.baselib.utils.toast
import com.epro.mall.utils.Beep
import android.R.attr.data
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.R.attr.data

import java.nio.ByteBuffer


/**
 * 扫码页面
 */
class ScanActivity : BaseSimpleActivity(), QRCodeView.Delegate, View.OnClickListener {
    companion object {
        const val SCAN_TYPE = "scan_type"
        const val SCAN_FOR_GOODS = 1
        const val SCAN_FOR_PAY = 2

        fun launchWithScanType(activity: Activity, requestCode: Int, scanType: Int = SCAN_FOR_GOODS) {
            activity.startActivityForResult(Intent(activity, ScanActivity::class.java).putExtra(SCAN_TYPE, scanType), requestCode)
        }
    }

    override fun onClick(p0: View?) {
        when (p0) {
            ivBack -> {
                finish()
            }
        }
    }

    var scanType = SCAN_FOR_GOODS

    override fun layoutContentId(): Int {
        return R.layout.activity_scan
    }
    var beep:Beep?=null
    override fun initData() {
        // 1、初始化Beep声音管理器
        beep = Beep(this)
    }

    override fun initListener() {
        ivBack.setOnClickListener(this)

    }

    override fun initStatusBar() {
        StatusBarUtil.immersive(this, resources.getColor(com.epro.mall.R.color.white), 0f)
    }


    private val TAG = ScanActivity::class.java!!.getSimpleName() + "_"

    override fun initView() {
        super.initView()
        //setSupportActionBar(toolbar)
        //BGAQRCodeUtil.setDebug(true)
        zScanview.setDelegate(this)
        scanType = intent.getIntExtra(SCAN_TYPE, SCAN_FOR_PAY)
        when (scanType) {
            SCAN_FOR_GOODS -> {

            }
            SCAN_FOR_PAY -> {

            }
        }
        zScanview.setType(BarcodeType.ALL, null) // 识别所有类型的码

        val scanboxView= zScanview.scanBoxView
        logTools.d(scanboxView.rectHeight)
        logTools.d(scanboxView.rectWidth)
//        scanboxView.rectHeight=scanboxView.rectHeight+500
//        scanboxView.rectWidth=scanboxView.rectWidth+500

    }

    override fun onStart() {
        super.onStart()
        zScanview.startCamera() // 打开后置摄像头开始预览，但是并未开始识别
        //mzxingview.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT); // 打开前置摄像头开始预览，但是并未开始识别
        zScanview.startSpotAndShowRect() // 显示扫描框，并开始识别
    }

    override fun onStop() {
        zScanview.stopCamera() // 关闭摄像头预览，并且隐藏扫描框
        super.onStop()
    }

    override fun onDestroy() {
        zScanview.onDestroy() // 销毁二维码扫描控件
        AppContext.getInstance().mainHandler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

    private fun vibrateAndDing() {
//        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
//        vibrator.vibrate(200)
        beep!!.playAndVibrate()
    }

    override fun onScanQRCodeSuccess(result: String?) {
        Log.i(TAG, "result:$result")
        vibrateAndDing()
        if (result.toString().isNotEmpty()) {
            val event = ScanResultEvent()
            event.result = result!!
            event.scanType = scanType
            EventBus.getDefault().post(event)
        } else {
            toast(getString(R.string.scan_empty))
        }
        if (scanType == SCAN_FOR_PAY) {
            finish()
        }

       // parseResult(result)
    }

    fun parseResult(result:String?){
        if (result.toString().isNotEmpty()) {
            val results=result?.split("_*_")!!
            val event = ScanResultEvent()
            event.result = results[0]
            event.scanType = scanType
            EventBus.getDefault().post(event)
            logTools.d(results[1])
            val originBitmap = BitmapFactory.decodeByteArray(results[1].toByteArray(), 0, results[1].toByteArray().size)

            val stitchBmp = Bitmap.createBitmap(108, 192, Bitmap.Config.RGB_565)
            stitchBmp . copyPixelsFromBuffer(ByteBuffer.wrap(results[1].toByteArray()))
            logTools.d(stitchBmp)
            ivResult.setImageBitmap(stitchBmp)
        } else {
            toast(getString(R.string.scan_empty))
        }
        if (scanType == SCAN_FOR_PAY) {
            finish()
        }

    }

    override fun onCameraAmbientBrightnessChanged(isDark: Boolean) {
        // 这里是通过修改提示文案来展示环境是否过暗的状态，接入方也可以根据 isDark 的值来实现其他交互效果
        var tipText = zScanview.scanBoxView.tipText
        val ambientBrightnessTip = "\n"+getString(R.string.turn_on_the_flash)
        if (isDark) {
            if (!tipText.contains(ambientBrightnessTip)) {
                zScanview.scanBoxView.tipText = tipText + ambientBrightnessTip
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip))
                zScanview.scanBoxView.tipText = tipText
            }
        }
    }

    override fun onScanQRCodeOpenCameraError() {
        Log.e(TAG, getString(R.string.error_opening_camera))
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSearchResultEvent(event: SearchResultEvent) {
        when (scanType) {
            SCAN_FOR_GOODS -> {
                if (event.isEmpty) {
                    setResult(Activity.RESULT_OK)
                    finish()
                    //toast("商品条码无法识别")
                } else {
                    //toast("添加商品成功")
                    setResult(Activity.RESULT_OK, Intent())
                    finish()
                }
//                AppContext.getInstance().mainHandler.postDelayed(Runnable {
//                    //连续扫码
//                    // setupReader()
//                    zScanview.startSpot()
//                }, 2000)
            }
        }
    }

    private fun setupReader() {
        val clazz = ZBarView::class.java
        // 获得指定类的属性
        try {
            val method = clazz.getDeclaredMethod("setupReader")
            method.isAccessible = true
            method.invoke(zScanview)
        } catch (e: NoSuchMethodError) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

}