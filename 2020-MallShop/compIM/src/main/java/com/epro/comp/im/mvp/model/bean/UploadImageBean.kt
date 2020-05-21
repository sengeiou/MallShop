package com.epro.comp.im.mvp.model.bean

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part
import retrofit2.http.PartMap

data class UploadImageBean(val code: Int, val message: String, val result: ArrayList<String>){
       data class Send(@PartMap var map:Map<String, RequestBody>, @Part var image: MultipartBody.Part)
}