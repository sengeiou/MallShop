package com.mike.baselib.api

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Streaming
import retrofit2.http.Url

interface ApiService{

    // https://file.zigsom.com/garden/apk/d0644e3a9e8a42fa9fc9054c574764ba.apk
    @Streaming
    @GET
    fun executeDownload(@Header("Range") range: String, @Url url: String): Observable<ResponseBody>

}