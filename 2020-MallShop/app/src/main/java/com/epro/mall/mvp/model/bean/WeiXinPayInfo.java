/**
  * Copyright 2019 bejson.com 
  */
package com.epro.mall.mvp.model.bean;

import com.google.gson.annotations.SerializedName;
public class WeiXinPayInfo {
    @SerializedName("package")
    private String package1;
    private String sign;
    private String noncestr;
    private String returnCode;
    private String appid;
    private String partnerid;
    private String prepayid;
    private String timestamp;
    public void setPackage1(String package1) {
         this.package1 = package1;
     }
     public String getPackage1() {
         return package1;
     }

    public void setSign(String sign) {
         this.sign = sign;
     }
     public String getSign() {
         return sign;
     }

    public void setNoncestr(String noncestr) {
         this.noncestr = noncestr;
     }
     public String getNoncestr() {
         return noncestr;
     }

    public void setReturnCode(String returnCode) {
         this.returnCode = returnCode;
     }
     public String getReturnCode() {
         return returnCode;
     }

    public void setAppid(String appid) {
         this.appid = appid;
     }
     public String getAppid() {
         return appid;
     }

    public void setPartnerid(String partnerid) {
         this.partnerid = partnerid;
     }
     public String getPartnerid() {
         return partnerid;
     }

    public void setPrepayid(String prepayid) {
         this.prepayid = prepayid;
     }
     public String getPrepayid() {
         return prepayid;
     }

    public void setTimestamp(String timestamp) {
         this.timestamp = timestamp;
     }
     public String getTimestamp() {
         return timestamp;
     }

}