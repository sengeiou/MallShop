package com.epro.mall.mvp.model

import android.util.Log
import com.amap.api.mapcore.util.it
import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.mike.baselib.listener.RetryWithDelay
import com.epro.mall.mvp.model.bean.ModifyImageBean
import com.epro.mall.mvp.model.bean.MyInfoBean
import com.epro.mall.mvp.model.bean.UpdateImageBean
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class MyInfoModel : BaseModel() {
    fun MyInfo(): Observable<MyInfoBean> {
        return getApiSevice().getMyInfo()
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<MyInfoBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun updateImage(image: File, isCreateThumb:Int): Observable<UpdateImageBean> {
        val bodyMap = HashMap<String, RequestBody>()
        bodyMap!!.put("isCreateThumb", RequestBody.create(MediaType.parse("text/plain"),isCreateThumb.toString()))
        val requestBody = RequestBody.create(MediaType.parse("image/png"), image)
        val body = MultipartBody.Part.createFormData("image",image.name,requestBody)
        return getApiSevice().updateImage(bodyMap,body)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<UpdateImageBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun modifyImage(avatar:String): Observable<ModifyImageBean> {
        return getApiSevice().modifyImage(ModifyImageBean.Send(avatar))
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<ModifyImageBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }
}