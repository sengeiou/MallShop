package com.epro.mall.ui

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import com.mike.baselib.activity.BaseSimpleActivity
import com.epro.mall.R
import com.epro.mall.ui.view.CommonDialog
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.beta.download.DownloadListener
import com.tencent.bugly.beta.download.DownloadTask
import kotlinx.android.synthetic.main.activity_upgrade.*

/**
 * 升级页面
 */
class UpgradeActivity :BaseSimpleActivity() {
    companion object {
        const val TAG = "UpgradeActivity"
        fun launch(context: Context) {
            launchWithStr(context, "")
        }

        fun launchWithStr(context: Context, str: String) {
            context.startActivity(Intent(context, UpgradeActivity::class.java).putExtra(TAG, str))
        }
    }


    override fun layoutContentId(): Int {
        return R.layout.activity_upgrade
    }

    override fun initData() {
    }

    override fun initListener() {

        /*为下载按钮设置监听*/
        start!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val task = Beta.startDownload()
                updateBtn(task)
                if (task.getStatus() == DownloadTask.DOWNLOADING) {
                    finish()
                }
            }
        })

        /*为取消按钮设置监听*/
        cancel!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                Beta.cancelDownload()
                finish()
            }
        })

        /*注册下载监听，监听下载事件*/
        Beta.registerDownloadListener(object : DownloadListener {
            override fun onReceive(task: DownloadTask) {
                updateBtn(task)
                tv!!.text = task.savedLength.toString() + ""
            }

            override fun onCompleted(task: DownloadTask) {
                updateBtn(task)
                tv!!.text = task.savedLength.toString() + ""
            }

            override fun onFailed(task: DownloadTask, code: Int, extMsg: String) {
                updateBtn(task)
                tv!!.text = "failed"

            }
        })
    }

    override fun initView() {
        super.initView()

        /*获取下载任务，初始化界面信息*/
        updateBtn(Beta.getStrategyTask())
        tv!!.text = tv!!.text.toString() + Beta.getStrategyTask().getSavedLength() + ""
        /*获取策略信息，初始化界面信息*/
        tvTitle!!.text = tvTitle!!.text.toString() + Beta.getUpgradeInfo().title
        version!!.text = version!!.text.toString() + Beta.getUpgradeInfo().versionName
        size!!.text = size!!.text.toString() + Beta.getUpgradeInfo().fileSize + ""
        time!!.text = time!!.text.toString() + Beta.getUpgradeInfo().publishTime + ""
        content!!.setText(Beta.getUpgradeInfo().newFeature)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
    }

    override fun onResume() {
        super.onResume()
        showUpdateDialog(Beta.getUpgradeInfo().newFeature)
    }

    override fun onDestroy() {
        super.onDestroy()

        /*注销下载监听*/
        Beta.unregisterDownloadListener()
    }


    fun updateBtn(task: DownloadTask) {
        /*根据下载任务状态设置按钮*/
        when (task.status) {
            DownloadTask.INIT, DownloadTask.DELETED, DownloadTask.FAILED -> {
                start!!.setText(getString(R.string.start_download))
            }
            DownloadTask.COMPLETE -> {
                start!!.setText(getString(R.string.installation))
            }
            DownloadTask.DOWNLOADING -> {
                start!!.setText(getString(R.string.stop))
            }
            DownloadTask.PAUSED -> {
                start!!.setText(getString(R.string.continue_downloading))
            }
        }
    }

    fun getTaskStatusText(task: DownloadTask): String {
        var statusText = ""
        /*根据下载任务状态设置按钮*/
        when (task.status) {
            DownloadTask.INIT, DownloadTask.DELETED, DownloadTask.FAILED -> {
                statusText = getString(R.string.start_download)
            }
            DownloadTask.COMPLETE -> {
                statusText = getString(R.string.installation)
            }
            DownloadTask.DOWNLOADING -> {
                statusText = getString(R.string.stop)
            }
            DownloadTask.PAUSED -> {
                statusText = getString(R.string.continue_downloading)
            }
        }

        return statusText
    }

    fun showUpdateDialog(remarks: String) {
        CommonDialog.Builder(this)
                .setTitle(getString(R.string.version_update_upgrade))
                .setContent(remarks)
                .setConfirmText(getTaskStatusText(Beta.getStrategyTask()))
                .setCanceledOnTouchOutside(false)
                .setOnConfirmListener(object : CommonDialog.OnConfirmListener {
                    override fun onClick(dialog: Dialog) {
                        // dialog.dismiss()
                        val task = Beta.startDownload()
                        updateBtn(task)
                        if (task.getStatus() == DownloadTask.DOWNLOADING) {
                            finish()
                        }
                    }
                })
                .setOnCancelListener(object : CommonDialog.OnCancelListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                        finish()
                    }
                })
                .create()
                .show()
    }
}