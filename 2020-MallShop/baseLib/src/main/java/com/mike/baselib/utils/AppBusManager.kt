package com.mike.baselib.utils

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mike.baselib.bean.*
import com.mike.baselib.net.exception.ErrorStatus
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import org.litepal.LitePal
import java.lang.reflect.Type


/**
 * 应用业务管理工具类
 */

open class AppBusManager {

    companion object {

        fun setToken(token: String) {
            if (TextUtils.isEmpty(token)) {
                setRandomKey("")
            }
            SPUtils.put(AppContext.getInstance().context, SPConstant.TOKEN, token)
        }

        fun getToken(): String {
            return SPUtils.get(AppContext.getInstance().context, SPConstant.TOKEN, "") as String
        }

        fun setRandomKey(randomKey: String) {
            SPUtils.put(AppContext.getInstance().context, SPConstant.RANDOM_KEY, randomKey)
        }

        fun getRandomKey(): String {
            return SPUtils.get(AppContext.getInstance().context, SPConstant.RANDOM_KEY, "") as String
        }

        fun setHasNeedPerms(isHas: Boolean) {
            SPUtils.put(AppContext.getInstance().context, SPConstant.KEY_NEED_PERMS, isHas)
        }

        fun isHasNeedPerms(): Boolean {
            return SPUtils.get(AppContext.getInstance().context, SPConstant.KEY_NEED_PERMS, false) as Boolean
        }

        fun getUuid(): String {
            return SPUtils.get(AppContext.getInstance().context, SPConstant.KEY_UUID, "") as String
        }

        fun setUuid(token: String) {
            SPUtils.put(AppContext.getInstance().context, SPConstant.KEY_UUID, token)
        }

        fun setUsername(name: String) {
            SPUtils.put(AppContext.getInstance().context, SPConstant.USER_NAME, name)
        }

        fun getUsername(): String {
            return SPUtils.get(AppContext.getInstance().context, SPConstant.USER_NAME, "") as String
        }

        fun setPassword(name: String) {
            SPUtils.put(AppContext.getInstance().context, SPConstant.PASSWORD, name)
        }

        fun getPassword(): String {
            return SPUtils.get(AppContext.getInstance().context, SPConstant.PASSWORD, "") as String
        }

        fun setDeviceName() {
            val myDevice = BluetoothAdapter.getDefaultAdapter()
            var deviceName = "unkown"
            if (myDevice != null) {
                deviceName = myDevice.name
            }
            SPUtils.put(AppContext.getInstance().context, SPConstant.KEY_DEVICE_NAME, deviceName)
        }

        fun getDeviceName(): String {
            return SPUtils.get(AppContext.getInstance().context, SPConstant.KEY_DEVICE_NAME, "unkown") as String
        }

        fun setUserPhone(phone: String) {
            SPUtils.put(AppContext.getInstance().context, SPConstant.PHONE, phone)
        }

        fun getUserPhone(): String {
            return SPUtils.get(AppContext.getInstance().context, SPConstant.PHONE, "") as String
        }

        fun setUserAvatar(avatar: String) {
            SPUtils.put(AppContext.getInstance().context, SPConstant.AVATAR, avatar)
        }

        fun getUserAvatar(): String {
            return SPUtils.get(AppContext.getInstance().context, SPConstant.AVATAR, "") as String
        }

        fun isLogin(): Boolean {
            return !TextUtils.isEmpty(getToken())
        }

        fun setDev(dev: Int) {
            SPUtils.put(AppContext.getInstance().context, SPConstant.DEV, dev)
        }

        fun getDev(): Int {
            return SPUtils.get(AppContext.getInstance().context, SPConstant.DEV, Constants.DV_TEST) as Int
        }

        fun setAppLanguage(language: String) {
            SPUtils.put(AppContext.getInstance().context, SPConstant.APP_LANGUAGE, language)
        }

        fun getAppLanguage(): String {
            return SPUtils.get(AppContext.getInstance().context, SPConstant.APP_LANGUAGE, Constants.SIMPLIFIED_CHINESE) as String
        }

        fun toJson(any: Any?): String? {
            if (any == null) {
                return null
            }
            return Gson().toJson(any)
        }

        fun <T> fromJson(json: String?, clazz: Class<T>): T? {
            if (TextUtils.isEmpty(json)) {
                return null
            }
            return Gson().fromJson(json, clazz)
        }

        // val type = object : TypeToken<T>() {}.type
        fun <T> fromJson(json: String?, type: Type): T? {
            if (TextUtils.isEmpty(json)) {
                return null
            }
            return Gson().fromJson(json, type)
        }

        fun <T> fromJsonWithClassKey(bundle: Bundle, clazz: Class<T>): T? {
            return fromJson(bundle.getString(ext_createJsonKey(clazz)), clazz)
        }

        fun <T> fromJsonWithClassKey(intent: Intent, clazz: Class<T>): T? {
            return fromJson(intent.getStringExtra(ext_createJsonKey(clazz)), clazz)
        }


        fun getDevName(): String {
            var devName = ""
            val dev = getDev()
            when (dev) {
                Constants.DV_TEST -> devName = "测试"
                Constants.DV_PRE_RELEASE -> devName = "预发布"
                Constants.DV_RELEASE -> devName = "发布"
            }
            return devName
        }

        fun encryptPassword(password: String): String {
            return AESUtils.encrypt(password)
        }

        fun encryptJson(url: String, json: String): String {
            var jsonStr = json
            val gb = GsonBuilder()
            gb.disableHtmlEscaping()
            if (url.contains(Constants.LOGIN)) {
                val loginSend = fromJson(json, LoginSend::class.java)!!
                loginSend.account = AESUtils.encrypt(loginSend.account)
                if (loginSend.type == Constants.LOGIN_TYPE_EP || loginSend.type == Constants.LOGIN_TYPE_MP) {
                    loginSend.password = AESUtils.encrypt(loginSend.password)
                }
                return gb.create().toJson(loginSend)
            }

            if (url.contains(Constants.REGISTER)) {
                val send = fromJson(json, RegisterSend::class.java)!!
                send.account = AESUtils.encrypt(send.account)
                send.password = AESUtils.encrypt(send.password)
                return gb.create().toJson(send)
            }

            if (url.contains(Constants.FIND_PASSWORD)) {
                val find = fromJson(json, FindPasswordSend::class.java)!!
                find.account = AESUtils.encrypt(find.account)
                find.password = AESUtils.encrypt(find.password)
                find.passwordConfirm = AESUtils.encrypt(find.passwordConfirm)
                return gb.create().toJson(find)
            }
            if (url.contains(Constants.MODIFY_PASSWORD)) {
                val modify = fromJson(json, ModifyPasswordSend::class.java)!!
                modify.account = AESUtils.encrypt(modify.account)
                modify.password = AESUtils.encrypt(modify.password)
                jsonStr = gb.create().toJson(modify)
            }
            val base64 = android.util.Base64.encodeToString(jsonStr.toByteArray(), android.util.Base64.NO_WRAP)
            val sign = Md5.digest32(base64 + getRandomKey())
            return gb.create().toJson(SignSend(base64, sign))
        }

        fun decryptJson(url: String, json: String): String {
            if (url.contains(Constants.LOGIN)) {//目前只有登录
                val loginCipherResponse = fromJson(json, LoginCipherResponse::class.java)
                if (loginCipherResponse!!.code != ErrorStatus.SUCCESS) {
                    return json
                }
                val bytes = AESUtils.decrypt(loginCipherResponse.result as String)!!
                val realResult = fromJson(String(bytes), LoginResponse.Result::class.java)!!
                return toJson(LoginResponse(loginCipherResponse.code, loginCipherResponse.message, realResult))!!
            }
            return json
        }

        fun getErea(): Int {
            return 0
        }

        fun getAmountUnit(): String {
            val ev = getErea()
            when (ev) {
                0 -> {
                    return "¥"
                }
            }
            return "¥"
        }

        fun setFirstBoot(first: Boolean) {
            SPUtils.put(AppContext.getInstance().context, SPConstant.IS_FRIST_BOOT, first)
        }

        fun getFirstBoot(): Boolean {
            return SPUtils.get(AppContext.getInstance().context, SPConstant.IS_FRIST_BOOT, true) as Boolean
        }

        fun setFirstChat(first: Boolean) {
            SPUtils.put(AppContext.getInstance().context, SPConstant.IS_FRIST_BOOT_CHAT, first)
        }

        fun getFirstChat(): Boolean {
            return SPUtils.get(AppContext.getInstance().context, SPConstant.IS_FRIST_BOOT_CHAT, true) as Boolean
        }

        fun setAppId(appId: String) {
            SPUtils.put(AppContext.getInstance().context, SPConstant.KEY_APP_ID, appId)
        }

        fun getAppId(): String {
            return SPUtils.get(AppContext.getInstance().context, SPConstant.KEY_APP_ID, "") as String
        }

        fun <T> createJsonKey(clazz: Class<T>): String {
            return ext_createJsonKey(clazz)
        }

        fun initRefreshUI(refreshView: View) {
            initSmartRefreshUI(refreshView as SmartRefreshLayout)
        }

        private fun initSmartRefreshUI(smartRefreshLayout: SmartRefreshLayout) {
            smartRefreshLayout.setEnableLoadMore(false)
            //设置全局的Header构建器
            smartRefreshLayout.setRefreshHeader(ClassicsHeader(AppContext.getInstance().context)
                    .setPrimaryColorId(com.mike.baselib.R.color.bottomColor)
                    // .setProgressResource(R.mipmap.gif_refresh)
                    .setArrowDrawable(null)
                    .setEnableLastTime(true)
                    .setDrawableProgressSize(25F))
            val progressView = (smartRefreshLayout.refreshHeader as ClassicsHeader).findViewById<ImageView>(com.scwang.smartrefresh.layout.R.id.srl_classics_progress)
            progressView.ext_loadGif(com.mike.baselib.R.mipmap.gif_refresh)
            smartRefreshLayout.setRefreshFooter(ClassicsFooter(AppContext.getInstance().context).setPrimaryColorId(com.mike.baselib.R.color.bottomColor).setDrawableProgressSize(25F)
                    .setProgressResource(com.mike.baselib.R.mipmap.gif_refresh))
            val progressView1 = (smartRefreshLayout.refreshFooter as ClassicsFooter).findViewById<ImageView>(com.scwang.smartrefresh.layout.R.id.srl_classics_progress)
            progressView1.ext_loadGif(com.mike.baselib.R.mipmap.gif_refresh)
        }

        fun insertResponseCache(url: String, params: String, body: String) {
            val cache = ResponseCache(url, params, body)
            cache.save()
        }

        fun queryResponseCache(url: String, params: String): ResponseCache? {
//            val whereClause = "url = ? and pramas = ?"
//            val whereArgs = arrayOf<String>(url, params)
//            val list = LitePal.where(whereClause, whereArgs[0], whereArgs[1]).find(ResponseCache::class.java)
            val whereClause = "url = ?"
            val whereArgs = arrayOf<String>(url)
            val list = LitePal.where(whereClause, whereArgs[0]).find(ResponseCache::class.java)
            if (list.isNotEmpty()) {
                return list[0]
            }
            return null
        }

    }
}