package com.mike.baselib.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {
    private static String key = "814d133c70ab4615b1edbcc8133930f7";
    private static String iv = "ABCDEF0123456789";//偏移量字符串必须是16位 当模式是CBC的时候必须设置偏移量
    private static String Algorithm = "AES";
    //private static String AlgorithmProvider = "AES/CBC/PKCS5Padding"; // 算法/模式/补码方式
    private static String AlgorithmProvider = "AES/ECB/PKCS5Padding"; // 算法/模式/补码方式

    public static byte[] generatorKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(Algorithm);
        keyGenerator.init(128);//默认128，获得无政策权限后可为192或256
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }

    public static IvParameterSpec getIv() throws UnsupportedEncodingException {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes("utf-8"));
        System.out.println("偏移量：" + byteToHexString(ivParameterSpec.getIV()));
        return ivParameterSpec;
    }

    //    public static byte[] encrypt(String src, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException,
//            InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException {
//        SecretKey secretKey = new SecretKeySpec(key, Algorithm);
//        IvParameterSpec ivParameterSpec = getIv();
//        Cipher cipher = Cipher.getInstance(AlgorithmProvider);
//        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
//        byte[] cipherBytes = cipher.doFinal(src.getBytes(Charset.forName("utf-8")));
//        return cipherBytes;
//    }
    public static byte[] encrypt(String src, String key) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException {
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), Algorithm);
        Cipher cipher = Cipher.getInstance(AlgorithmProvider);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        System.out.println(new String(cipher.doFinal(src.getBytes(Charset.forName("utf-8"))), "UTF-8"));
        return cipher.doFinal(src.getBytes(Charset.forName("utf-8")));
    }

    /**
     * 创建密钥
     *
     * @return
     */
    private static SecretKeySpec createKey(String key) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance(Algorithm);
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes());
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            return new SecretKeySpec(enCodeFormat, Algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 创建密钥
     *
     * @return
     */
    private static final String CIPHER_CBC_MODE = "AES/CBC/PKCS5Padding";

    private static final String CIPHER_ECB_MODE = "AES/ECB/PKCS7Padding";

    private static final Integer IV_SIZE = 16;

    private static final String ENCRYPLTALG = "AES";

    private static final String ENCODE_UTF8 = "UTF-8";

    private static final int SECRETKEY_SIZE = 32;

//    private static SecretKeySpec createKey(String key) {
//        StringBuilder sb = new StringBuilder(SECRETKEY_SIZE);
//        sb.append(key);
//        if (sb.length() > SECRETKEY_SIZE) {
//            sb.setLength(SECRETKEY_SIZE);
//        }
//        if (sb.length() < SECRETKEY_SIZE) {
//            while (sb.length() < SECRETKEY_SIZE) {
//                sb.append(" ");
//            }
//        }
//        try {
//            byte[] data = sb.toString().getBytes(ENCODE_UTF8);
//            KeyGenerator kgen = KeyGenerator.getInstance(ENCRYPLTALG);
//            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
//            secureRandom.setSeed(data);
//            kgen.init(128, secureRandom);
//            SecretKey secretKey = kgen.generateKey();
//            byte[] enCodeFormat = secretKey.getEncoded();
//            return new SecretKeySpec(enCodeFormat, ENCRYPLTALG);
//        } catch (Exception e) {
//           e.printStackTrace();
//        }
//        return null;
//    }

    //    public static String encrypt(String src) {
//        try {
//            return byteToHexString(encrypt(src, key.getBytes())).toUpperCase();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (NoSuchPaddingException e) {
//            e.printStackTrace();
//        } catch (InvalidKeyException e) {
//            e.printStackTrace();
//        } catch (IllegalBlockSizeException e) {
//            e.printStackTrace();
//        } catch (BadPaddingException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (InvalidAlgorithmParameterException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
    public static String encrypt(String src) {
        try {
            return android.util.Base64.encodeToString(encrypt(src, key), android.util.Base64.NO_WRAP);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decrypt(String src, String key) throws Exception {
        SecretKey secretKey = createKey(key);
        Cipher cipher = Cipher.getInstance(AlgorithmProvider);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] bytes = android.util.Base64.decode(src, android.util.Base64.DEFAULT);
        return cipher.doFinal(bytes);
    }

    public static byte[] decrypt(String src) throws Exception {
        byte[] bytes = android.util.Base64.decode(src, android.util.Base64.DEFAULT);
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), Algorithm);
        Cipher cipher = Cipher.getInstance(AlgorithmProvider);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(bytes);
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

    public static void main(String[] args) {
        try {
            // byte key[] = generatorKey();
            // 密钥必须是16的倍数
            //byte key[] = "1234567890ABCDEF1234567890ABCDEf".getBytes("utf-8");//hexStringToBytes("0123456789ABCDEF");
            String key = "1234567890ABCDEF1234567890ABCDEf";//hexStringToBytes("0123456789ABCDEF");
            String src = "+86_17788757163";
            //System.out.println("密钥:" + byteToHexString(key));
            System.out.println("原字符串:" + src);

            //System.out.println(encrypt("{\"randomKey\":\"6hasbs\",\"token\":\"eyJhbGciOiJIUzUxMiJ9.eyJyYW5kb21LZXkiOiI2aGFzYnMiLCJzdWIiOiIxMDAwMDEyIiwiZXhwIjoxNTg2Mzk5NDM0LCJpYXQiOjE1ODU3OTQ2MzR9.NIoR4i31q6phWVLDfFNh2ETq1mfucOfcCuW8NqhQ7swRF9ylz5ToKy8Wz1kAi12FdJvZGS2CYWn3PoMvEgtvYg\"}"));


            String enc = byteToHexString(encrypt(src, key));
            System.out.println("加密：" + enc);
            //System.out.println("解密：" + new String(decrypt(enc, key), "utf-8"));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}