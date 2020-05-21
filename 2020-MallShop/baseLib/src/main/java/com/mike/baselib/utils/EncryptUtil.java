package com.mike.baselib.utils;


import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES CBC 模式的加密方式
 *
 * @Description
 * @Author hepeng
 * @Date 2020/3/18 9:44
 */
public class EncryptUtil {


    private static final String CIPHER_CBC_MODE = "AES/CBC/PKCS5Padding";
    private static final String CIPHER_ECB_MODE  ="AES/ECB/PKCS5Padding";

    private static final Integer IV_SIZE = 16;

    private static final String ENCRYPLTALG = "AES";

    private static final String ENCODE_UTF8 = "UTF-8";

    private static final int SECRETKEY_SIZE = 32;

    private static String KEY = "814d133c70ab4615b1edbcc8133930f7";

    /**
     * 创建密钥
     *
     * @return
     */
    private static SecretKeySpec createKey(String key) {
        StringBuilder sb = new StringBuilder(SECRETKEY_SIZE);
        sb.append(key);
        if (sb.length() > SECRETKEY_SIZE) {
            sb.setLength(SECRETKEY_SIZE);
        }
        if (sb.length() < SECRETKEY_SIZE) {
            while (sb.length() < SECRETKEY_SIZE) {
                sb.append(" ");
            }
        }
        try {
            byte[] data = sb.toString().getBytes(ENCODE_UTF8);
            System.out.println(sb.toString());
            System.out.println(new String(data,"UTF-8"));
            return new SecretKeySpec(data, ENCRYPLTALG);
        } catch (Exception e) {
        }
        return null;
    }

    private static SecretKeySpec deriveKeyInsecurely(String password) {
        try {
            System.out.println(password);
            byte[]  passwordBytes = password.getBytes(ENCODE_UTF8);
            System.out.println(new String(passwordBytes,"UTF-8"));
            byte[] enCodeFormat=InsecureSHA1PRNGKeyDerivator.deriveInsecureKey(passwordBytes, 16);
            System.out.println(enCodeFormat.length);
            System.out.println(new String(enCodeFormat,"UTF-8"));
            return new SecretKeySpec(enCodeFormat, "AES");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
       return null;
    }


    private static SecretKeySpec createKey2(String key) {
        // 给出字符串的密码
        String password = key;

        // 密钥的比特位数，注意这里是比特位数
        // AES 支持 128、192 和 256 比特长度的密钥
        int keyLength = 128;
        // 盐值的字节数组长度，注意这里是字节数组的长度
        // 其长度值需要和最终输出的密钥字节数组长度一致
        // 由于这里密钥的长度是 256 比特，则最终密钥将以 256/8 = 32 位长度的字节数组存在
        // 所以盐值的字节数组长度也应该是 32
        int saltLength = 16;
        //byte[] salt;

        // 先获取一个随机的盐值
        // 你需要将此次生成的盐值保存到磁盘上下次再从字符串换算密钥时传入
        // 如果盐值不一致将导致换算的密钥值不同
        // 保存密钥的逻辑官方并没写，需要自行实现
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[saltLength];
        random.nextBytes(salt);

        // 将密码明文、盐值等使用新的方法换算密钥
        int iterationCount = 1000;
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt,
                iterationCount, keyLength);
        SecretKeyFactory keyFactory = null;
        try {
            keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 到这里你就能拿到一个安全的密钥了
        byte[] keyBytes = new byte[0];
        try {
            assert keyFactory != null;
            keyBytes = keyFactory.generateSecret(keySpec).getEncoded();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return new SecretKeySpec(keyBytes, "AES");
    }

    /**
     * 创建16位向量: 不够则用0填充
     *
     * @return
     */
    private static IvParameterSpec createIV(String ivKey) {
        StringBuffer sb = new StringBuffer(IV_SIZE);
        sb.append(ivKey);
        if (sb.length() > IV_SIZE) {
            sb.setLength(IV_SIZE);
        }
        if (sb.length() < IV_SIZE) {
            while (sb.length() < IV_SIZE) {
                sb.append("0");
            }
        }
        byte[] data = null;
        try {
            data = sb.toString().getBytes(ENCODE_UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new IvParameterSpec(data);
    }

    public static String encrypt(String context, String secretKey) {
        return encrypt(context, secretKey,null);
    }
    public static String encrypt(String context) {
        return encrypt(context, KEY);
    }

        /**
         * 加密：有向量16位，结果转base64
         *
         * @param context
         * @return
         */
    public static String encrypt(String context, String secretKey, String ivkey) {
        try {
            byte[] content = context.getBytes(ENCODE_UTF8);
           // byte[] content = "+86_17788757163".getBytes(ENCODE_UTF8);
            //SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(),ENCRYPLTALG);
            SecretKeySpec key = createKey(secretKey);
            Cipher cipher = null;
            if(ivkey!=null){
                cipher = Cipher.getInstance(CIPHER_CBC_MODE);
                cipher.init(Cipher.ENCRYPT_MODE, key, createIV(ivkey));
            }else{
                cipher = Cipher.getInstance(CIPHER_ECB_MODE);
                cipher.init(Cipher.ENCRYPT_MODE, key);
            }
            byte[] data = cipher.doFinal(content);
            //LogTools.debug("AESUtil",new String(key.getEncoded(),"UTF-8"));
            System.out.println("加密结果**:"+new String(data,"UTF-8"));
            LogTools.debug("AESUtil",new String(data,"UTF-8"));
            String result = Base64.encodeToString(data, android.util.Base64.NO_WRAP);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }
    public static String encrypt2(String context, String secretKey, String ivkey) {
        try {
          //  byte[] content = context.getBytes(ENCODE_UTF8);
            byte[] content = "+86_17788757163".getBytes(ENCODE_UTF8);
            //SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(),ENCRYPLTALG);
            SecretKeySpec key = createKey2(secretKey);
            Cipher cipher = null;
            if(ivkey!=null){
                cipher = Cipher.getInstance(CIPHER_CBC_MODE);
                cipher.init(Cipher.ENCRYPT_MODE, key, createIV(ivkey));
            }else{
                cipher = Cipher.getInstance(CIPHER_ECB_MODE);
                cipher.init(Cipher.ENCRYPT_MODE, key);
            }
            byte[] data = cipher.doFinal(content);
            //LogTools.debug("AESUtil",new String(key.getEncoded(),"UTF-8"));
            System.out.println("加密结果**:"+new String(data,"UTF-8"));
            LogTools.debug("AESUtil",new String(data,"UTF-8"));
            String result = Base64.encodeToString(data, android.util.Base64.NO_WRAP);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

//    private static SecretKeySpec deriveKeyInsecurely(String password) {
//        byte[] passwordBytes = password.getBytes(StandardCharsets.US_ASCII);
//        return new SecretKeySpec(InsecureSHA1PRNGKeyDerivator.deriveInsecureKey(passwordBytes, AESUtils.KEY_SIZE), "AES");
//    }

//    public static String decrypt(String context, String secretKey) {
//        return decrypt(context,secretKey, null);
//    }
//    public static byte[] decryptToByte(String context, String secretKey) {
//        return decryptToByte(context, secretKey,null);
//    }

        /**
         * 解密
         * @return
         */
//    public static String decrypt(String context, String secretKey, String ivkey) {
//        try {
//            byte[] content = decryptToByte(context, secretKey, ivkey);
//            String result = new String(content, ENCODE_UTF8);
//            return result;
//        } catch (Exception e) {
//        }
//        return null;
//    }

//    public static byte[] decryptToByte(String context, String secretKey, String ivkey) {
//        try {
//           // byte[] data = Base64.decodeBase64(context);
//            SecretKeySpec key = createKey(secretKey);
//            Cipher cipher = null;
//            if(!TextUtils.isEmpty(ivkey)){
//                cipher = Cipher.getInstance(CIPHER_CBC_MODE);
//                cipher.init(Cipher.DECRYPT_MODE, key, createIV(ivkey));
//            }else{
//                cipher = Cipher.getInstance(CIPHER_ECB_MODE);
//                cipher.init(Cipher.DECRYPT_MODE, key);
//            }
//            return cipher.doFinal(data);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

        public static void main(String[] args) throws Exception {
            //密钥 加密内容(对象序列化后的内容-json格式字符串)
            String content = "aa111111";
            String secretKey = "814d133c70ab4615b1edbcc8133930f7";

            String mobile = "+86_17788757163";
            String encryptText = encrypt(mobile, secretKey);
            String encryptText1 = encrypt2(mobile, secretKey,null);
           // System.out.println(encryptText);

        }


}
