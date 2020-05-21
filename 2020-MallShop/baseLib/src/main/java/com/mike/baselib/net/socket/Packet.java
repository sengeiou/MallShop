package com.mike.baselib.net.socket;

public class Packet {

//    消息头： 	固定两个字符“@@”，HEX表示为40h 40h；
//    项目ID： 	u8 数据类型，每个项目不一样，0~255
//    消息长度： Word数据类型，从头到校验，整个数据包的长度；
//    消息流水号：Word数据类型，标识本数据包的流水号；
//    功能ID：  	Word数据类型，标识本数据包的功能；
//    设备ID长度: u8 数据类型，标识设备ID字段的长度
//    设备ID： 	长度根据上一个字节确定，通常使用ASCII
//    用户账号：admin，即0x61,0x64,0x6D,0x69,0x6E; ；
//            13481111401，即0x01,0x03, 0x04,0x08, 0x01,0x01, 0x01,0x01, 0x04,0x00, 0x01;
//    手机号(6byte)：13481111401，即0x01,0x34,0x81,0x11,0x14,0x01；(单数，高位补0)
//    校验字段：	从消息头开始到校验字段前所有字节和，算法如下：
//    //从offset 开始效验len长度个字节
//    int jf_get_sum(byte[] pdata, int offset, int len) {
//        int sum = 0;
//        int i;
//        for (i = 0; i < len; i++)
//        {
//            sum = (sum << 1) + pdata[i + offset];
//        }
//        return sum & 0x0000FFFF;
//    }
    
    
    //全部接收十六进制字符串

    private String header;
    private String projectId;
    private String length;
    private String serialNumber;
    private String funtionId;
    private String deviceIdLength;
    private String deviceId;
    private String data;
    private String checkData;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getFuntionId() {
        return funtionId;
    }

    public void setFuntionId(String funtionId) {
        this.funtionId = funtionId;
    }

    public String getDeviceIdLength() {
        return deviceIdLength;
    }

    public void setDeviceIdLength(String deviceIdLength) {
        this.deviceIdLength = deviceIdLength;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCheckData() {
        return checkData;
    }

    public void setCheckData(String checkData) {
        this.checkData = checkData;
    }

}
