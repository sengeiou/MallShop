package com.epro.mall.mvp.model.bean

import java.util.*


/*
	"content": "1.更新内容:",
    		"createTime": "2019-11-13 18:42:54",
    		"downloadUrl": "group1/M00/00/38/wKgBul3nfLyAd093AcYtLwO-nyA195.apk",
    		"id": 3,
    		"type": "3",
    		"typeName": "android_app",
    		"version": "V1.0.2"
*/
data class AboutBean(override val code: Int, override val message: String, override val result: Result):BaseBean<AboutBean.Result> {
    data class Result(val id:String,val type :String,val content:String,val version:String,val createTime:String,val typeName:String,val downloadUrl:String,
                      val isRequired:String,val requiredVersion:String)
    data class Send(val type:String)
}