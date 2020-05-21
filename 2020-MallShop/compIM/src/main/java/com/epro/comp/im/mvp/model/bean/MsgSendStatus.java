package com.epro.comp.im.mvp.model.bean;

public enum MsgSendStatus implements java.io.Serializable{
    DEFAULT,
    //发送中
    SENDING,
    //发送失败
    FAILED,
    //已发送
    SENT;
}
