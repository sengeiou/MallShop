package com.mike.baselib.net.socket;

import com.mike.baselib.utils.LogTools;

public class PacketHelper {

    static LogTools logTools = new LogTools("PacketHelper");

    /**
     * 发送消息
     *
     * @param packet
     * @return
     */
    public static byte[] sendPacket(Packet packet) {

        packet.setHeader("4040");
        packet.setDeviceIdLength(fillZero(2, Integer.toHexString(packet.getDeviceId().length() / 2)));
        int length = 12 + (packet.getDeviceId().length() + packet.getData().length()) / 2;
        packet.setLength(fillZero(4, Integer.toHexString(length)));

        String msg = packet.getHeader() ;

        msg = msg + packet.getProjectId();

        msg = msg + packet.getLength();

        msg = msg + packet.getSerialNumber();

        msg = msg + packet.getFuntionId();

        msg = msg + packet.getDeviceIdLength();

        msg = msg + packet.getDeviceId() + packet.getData();

        //校验
        int check = jf_get_sum(hexStringToBytes(msg.toUpperCase()), 0, length - 2);
        String checkData = Integer.toHexString(check);
        packet.setCheckData(fillZero(4, checkData));
        msg = msg + packet.getCheckData();

        logTools.d(msg.toUpperCase());
        return hexStringToBytes(msg.toUpperCase());
    }

    /**
     * 补零
     *
     * @param total 总长度
     * @param src   需要补零的字符串
     * @return
     */

    public static String fillZero(int total, String src) {
        String s = src;
        for (int i = 0; i < total - src.length(); i++) {
            s = "0" + s;
        }
        return s;
    }

    private static int jf_get_sum(byte[] pdata, int offset, int len) {
        int sum = 0;
        int i;
        for (i = 0; i < len; i++) {
            sum = (sum << 1) + pdata[i + offset];
        }
        return sum & 0x0000FFFF;
    }

    public static Packet parsePacket(byte[] bytesMsg) {
        Packet packet = new Packet();

        byte[] bytes = new byte[2];
        bytes[0] = bytesMsg[0];
        bytes[1] = bytesMsg[1];
        packet.setHeader(byteToHexString(bytes));

        packet.setProjectId(byteToHexString(bytesMsg[2]));


        bytes = new byte[2];
        bytes[0] = bytesMsg[3];
        bytes[1] = bytesMsg[4];
        packet.setLength(byteToHexString(bytes));
        int length = Integer.parseInt(packet.getLength(), 16);//十六进制转十进制

        bytes = new byte[2];
        bytes[0] = bytesMsg[5];
        bytes[1] = bytesMsg[6];
        packet.setSerialNumber(byteToHexString(bytes));

        bytes = new byte[2];
        bytes[0] = bytesMsg[7];
        bytes[1] = bytesMsg[8];
        packet.setFuntionId(byteToHexString(bytes));

        packet.setDeviceIdLength(byteToHexString(bytesMsg[9]));
        int deviceLength = Integer.parseInt(packet.getDeviceIdLength(), 16);//十六进制转十进制

        bytes = new byte[deviceLength];
        System.arraycopy(bytesMsg, 10, bytes, 0, deviceLength);
        packet.setDeviceId(byteToHexString(bytes));

        int dataLength = length - deviceLength - 12;
        bytes = new byte[dataLength];
        System.arraycopy(bytesMsg, 10 + deviceLength, bytes, 0, dataLength);
        packet.setData(byteToHexString(bytes));

        bytes = new byte[2];
        bytes[0] = bytesMsg[length - 2];
        bytes[1] = bytesMsg[length - 1];
        packet.setCheckData(byteToHexString(bytes));

        return packet;
    }


    /**
     * 16进制直接转换成为字符串(无需Unicode解码)
     *
     * @param hexStr
     * @return
     */
    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    /**
     * 将byte转换为16进制字符串
     *
     * @param src
     * @return
     */
    public static String byteToHexString(byte[] src) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xff;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                sb.append("0");
            }
            sb.append(hv);
        }
        return sb.toString();
    }

    /**
     * 将byte转换为16进制字符串
     *
     * @param b
     * @return
     */
    public static String byteToHexString(byte b) {
        byte[] src = new byte[1];
        src[0] = b;
        return byteToHexString(src);
    }

    /**
     * 将16进制字符串装换为byte数组
     *
     * @param hexString
     * @return
     */
    public static byte[] hexStringToBytes(String hexString) {
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] b = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            b[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return b;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}


