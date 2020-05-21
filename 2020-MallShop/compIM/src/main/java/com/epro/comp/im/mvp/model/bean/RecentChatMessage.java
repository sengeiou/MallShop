package com.epro.comp.im.mvp.model.bean;

public class RecentChatMessage implements java.io.Serializable {
    private String msgText;
    private String msgType;
    private String csId;//客服Id
    private String customerId;//客户Id
    private boolean isRead=false;
    private long sentTime;

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    public String getMsgText() {
        return msgText;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgType() {
        return msgType;
    }

    public String getCsId() {
        return csId;
    }

    public void setCsId(String csId) {
        this.csId = csId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public void setSentTime(long sentTime) {
        this.sentTime = sentTime;
    }

    public long getSentTime() {
        return sentTime;
    }
}
