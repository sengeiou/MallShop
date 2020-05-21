package com.mike.baselib.bean;


import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.util.UUID;


public class ResponseCache extends LitePalSupport implements java.io.Serializable {
    //  @PrimaryKey
    @Column(unique = true, defaultValue = "unknown")
    private String uuid = UUID.randomUUID().toString() + "";
    private String url;
    private String pramas;
    private String body;

    public ResponseCache(String url,String pramas,String body){
        this.url=url;
        this.pramas=pramas;
        this.body=body;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPramas() {
        return pramas;
    }

    public void setPramas(String pramas) {
        this.pramas = pramas;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }
}
