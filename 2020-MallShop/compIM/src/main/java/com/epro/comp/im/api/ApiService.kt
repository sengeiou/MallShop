package com.epro.comp.im.api

import com.epro.comp.im.mvp.model.bean.GetCustomerServiceListBean
import com.epro.comp.im.mvp.model.bean.RegisterBean
import com.epro.comp.im.mvp.model.bean.UploadImageBean
import com.epro.comp.im.utils.IMConst
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * Api 接口
 */

interface ApiService{

    /**
     * 注册
     */
     //loginname string (query)  登录名
    // truename string (query)  姓名
    // details string (query)  详情
    @POST(IMConst.REGISTER)
    @FormUrlEncoded
    fun register(@Field("loginname") loginname:String,
                 @Field("truename") truename:String,
                 @Field("details") details:String):Observable<RegisterBean>


    /**
     * 上传图片
     */
    @Multipart
    @POST(IMConst.UPLOAD_IMAGE)
    fun uploadImage(@PartMap map: HashMap<String, RequestBody>, @Part image: MultipartBody.Part): Observable<UploadImageBean>


    @GET(IMConst.GET_CUSTOMER_SERVICE_LIST)
    fun getCustomerServiceList(@Path("strId") shopId: String): Observable<GetCustomerServiceListBean>

    @GET(IMConst.GET_CUSTOMER_SERVICE)
    fun getCustomerService(@Path("strId") account: String): Observable<GetCustomerServiceListBean>

}