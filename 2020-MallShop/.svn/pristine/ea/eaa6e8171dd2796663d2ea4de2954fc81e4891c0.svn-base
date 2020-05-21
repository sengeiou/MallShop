package com.mike.baselib;

import com.mike.baselib.utils.EncryptUtil;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {

        String secretKey = "814d133c70ab4615b1edbcc8133930f7";

        String mobile = "+86_17788757163";
        String encryptText = EncryptUtil.encrypt(mobile, secretKey);
        System.out.println(encryptText);
        //assertEquals(4, 2 + 2);
    }
}