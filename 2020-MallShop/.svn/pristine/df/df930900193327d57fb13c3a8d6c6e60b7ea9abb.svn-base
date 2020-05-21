package com.mike.baselib.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.mike.baselib.R
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.security.MessageDigest
import java.util.ArrayList

/**
 * desc: APP 相关的工具类
 */

class AppUtils private constructor() {

    init {
        throw Error("Do not need instantiate!")
    }

    companion object {

        private val DEBUG = true
        private val TAG = "AppUtils"

        /**
         * 得到软件版本号
         *
         * @param context 上下文
         * @return 当前版本Code
         */
        fun getVerCode(context: Context): Int {
            var verCode = -1
            try {
                val packageName = context.packageName
                verCode = context.packageManager
                        .getPackageInfo(packageName, 0).versionCode
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return verCode
        }


        /**
         * 获取应用运行的最大内存
         *
         * @return 最大内存
         */
        val maxMemory: Long
            get() = Runtime.getRuntime().maxMemory() / 1024


        /**
         * 得到软件显示版本信息
         *
         * @param context 上下文
         * @return 当前版本信息
         */
        fun getVerName(context: Context): String {
            var verName = ""
            try {
                val packageName = context.packageName
                verName = context.packageManager
                        .getPackageInfo(packageName, 0).versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return verName
        }


        @SuppressLint("PackageManagerGetSignatures")
                /**
                 * 获取应用签名
                 *
                 * @param context 上下文
                 * @param pkgName 包名
                 * @return 返回应用的签名
                 */
        fun getSign(context: Context, pkgName: String): String? {
            return try {
                @SuppressLint("PackageManagerGetSignatures") val pis = context.packageManager
                        .getPackageInfo(pkgName,
                                PackageManager.GET_SIGNATURES)
                hexDigest(pis.signatures[0].toByteArray())
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                null
            }
        }

        /**
         * 将签名字符串转换成需要的32位签名
         *
         * @param paramArrayOfByte 签名byte数组
         * @return 32位签名字符串
         */
        private fun hexDigest(paramArrayOfByte: ByteArray): String {
            val hexDigits = charArrayOf(48.toChar(), 49.toChar(), 50.toChar(), 51.toChar(), 52.toChar(), 53.toChar(), 54.toChar(), 55.toChar(), 56.toChar(), 57.toChar(), 97.toChar(), 98.toChar(), 99.toChar(), 100.toChar(), 101.toChar(), 102.toChar())
            try {
                val localMessageDigest = MessageDigest.getInstance("MD5")
                localMessageDigest.update(paramArrayOfByte)
                val arrayOfByte = localMessageDigest.digest()
                val arrayOfChar = CharArray(32)
                var i = 0
                var j = 0
                while (true) {
                    if (i >= 16) {
                        return String(arrayOfChar)
                    }
                    val k = arrayOfByte[i].toInt()
                    arrayOfChar[j] = hexDigits[0xF and k.ushr(4)]
                    arrayOfChar[++j] = hexDigits[k and 0xF]
                    i++
                    j++
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return ""
        }


        /**
         * 获取设备的可用内存大小
         *
         * @param context 应用上下文对象context
         * @return 当前内存大小
         */
        fun getDeviceUsableMemory(context: Context): Int {
            val am = context.getSystemService(
                    Context.ACTIVITY_SERVICE) as ActivityManager
            val mi = ActivityManager.MemoryInfo()
            am.getMemoryInfo(mi)
            // 返回当前系统的可用内存
            return (mi.availMem / (1024 * 1024)).toInt()
        }


        fun getMobileModel(): String {
            var model: String? = Build.MODEL
            model = model?.trim { it <= ' ' } ?: ""
            return model
        }

        /**
         * 获取手机系统SDK版本
         *
         * @return 如API 17 则返回 17
         */
        val sdkVersion: Int
            get() = android.os.Build.VERSION.SDK_INT

        /**
         * 隐藏键盘
         */
        fun hideInput(context: Context, editText: EditText) {
            val imm: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager;
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0)
        }

        /**
         * 复制
         */
        fun copyText(context: Context, content: String) {
            val clip = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipdata = ClipData.newPlainText("jsq", content)
            clip.setPrimaryClip(clipdata)
        }


        fun <T> exitApp(context: Context, mainActivityClass: Class<T>) {
            exitApp(context, mainActivityClass, true)
        }

        fun <T> exitApp(context: Context, mainActivityClass: Class<T>, isFinishSelf: Boolean) {
            val intent = Intent(context, mainActivityClass)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("EXIT_TAG", "CLEAR_TOP")
            intent.putExtra("FINISH", isFinishSelf)
            context.startActivity(intent)
        }

        fun getNavigationBarHeight(activity: Activity): Int {
            val resources = activity.resources
            val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
            return resources.getDimensionPixelSize(resourceId)
        }

        //获取是否存在NavigationBar
        fun checkDeviceHasNavigationBar(context: Context): Boolean {
            var hasNavigationBar = false
            val rs = context.resources
            val id = rs.getIdentifier("config_showNavigationBar", "bool", "android")
            if (id > 0) {
                hasNavigationBar = rs.getBoolean(id)
            }
            try {
                val systemPropertiesClass = Class.forName("android.os.SystemProperties")
                val m = systemPropertiesClass.getMethod("get", String::class.java)
                val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
                if ("1" == navBarOverride) {
                    hasNavigationBar = false
                } else if ("0" == navBarOverride) {
                    hasNavigationBar = true
                }
            } catch (e: Exception) {

            }

            return hasNavigationBar
        }


        /**
         * 关闭activity中打开的键盘
         *
         * @param activity
         */
        fun closeKeyboard(activity: Activity) {
            val view = activity.window.peekDecorView()
            if (view != null) {
                val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        /**
         * 关闭dialog中打开的键盘
         *
         * @param dialog
         */
        fun closeKeyboard(dialog: Dialog) {
            val view = dialog.window!!.peekDecorView()
            if (view != null) {
                val inputMethodManager = dialog.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        /**
         * 打开输入法
         * (提供当前操作的标记，SHOW_FORCED表示强制显示)
         */
        fun showSoftInput(view: View, context: Context) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
        }

        /**
         * 用来判断服务是否运行.
         *
         * @param className 判断的服务名字
         * @return true 在运行 false 不在运行
         */
        private fun isServiceRunning(context: Context, className: String): Boolean {

            var isRunning = false
            val activityManager = context
                    .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val serviceList = activityManager
                    .getRunningServices(Integer.MAX_VALUE)
            if (serviceList.size <= 0) {
                return false
            }
            for (i in serviceList.indices) {
                if (serviceList[i].service.className == className == true) {
                    isRunning = true
                    break
                }
            }
            return isRunning
        }

        /**
         * 拨打电话（跳转到拨号界面，用户手动点击拨打）
         * @param phoneNum 电话号码
         */
        fun callPhone(activity: Activity, phoneNum: String) {
            val intent = Intent(Intent.ACTION_DIAL)
            val data = Uri.parse("tel:$phoneNum")
            intent.data = data
            activity.startActivity(intent)
        }

        fun sendEmail(activity: Activity, email: String) {
            //        Intent intent = new Intent(Intent.ACTION_SEND);
            //        // 附件
            //       // File file = new File(Environment.getExternalStorageDirectory().getPath()+ File.separator + "simplenote"+ File.separator+"note.xml");
            //        // 收件人
            //        intent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] {"pop1030123@163.com"});
            //        // 主题
            //        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "note.xml");
            //        // 正文
            //        intent.putExtra(android.content.Intent.EXTRA_TEXT,"this is test extra.");
            //        intent.setType("application/octet-stream");
            //        //当无法确认发送类型的时候使用如下语句
            //        //intent.setType(“*/*”);
            //        //当没有附件,纯文本发送时使用如下语句
            //        //intent.setType(“plain/text”);
            //        //intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            //        activity.startActivity(Intent.createChooser(intent, "Mail Chooser"));

            val uri = Uri.parse("mailto:$email")
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            //intent.putExtra(Intent.EXTRA_CC, email); // 抄送人
            // intent.putExtra(Intent.EXTRA_SUBJECT, "这是邮件的主题部分"); // 主题
            // intent.putExtra(Intent.EXTRA_TEXT, "这是邮件的正文部分"); // 正文
            activity.startActivity(Intent.createChooser(intent, AppContext.getInstance().getString(R.string.email_aplication)))
        }

        /* 检查手机上是否安装了指定的软件
    * @param context
    * @param packageName：应用包名
    * @return  */
        fun isAvilible(context: Context, packageName: String): Boolean {
            //获取packagemanager
            val packageManager = context.packageManager
            //获取所有已安装程序的包信息
            val packageInfos = packageManager.getInstalledPackages(0)
            //用于存储所有已安装程序的包名
            val packageNames = ArrayList<String>()
            //从pinfo中将包名字逐一取出，压入pName list中
            for (i in packageInfos.indices) {
                val packName = packageInfos[i].packageName
                packageNames.add(packName)
            }
            //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
            return packageNames.contains(packageName)
        }


        fun assetsFileToString(context: Context, fileName: String): String {
            val stringBuilder = StringBuilder()
            try {
                val assetManager = context.assets
                val bf = BufferedReader(InputStreamReader(
                        assetManager.open(fileName)))
                var line: String? = ""
                while (line != null) {
                    line = bf.readLine()
                    if (!TextUtils.isEmpty(line)) {
                        stringBuilder.append(line)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return stringBuilder.toString()
        }

        fun checkGPSIsOpen(context: Context): Boolean {
            val locationManager = context
                    .getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }

        fun openLocationService(activity: Activity, requestCode: Int) {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            activity.startActivityForResult(intent, requestCode)
        }
    }


}