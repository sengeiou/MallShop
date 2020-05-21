package com.epro.mall.mvp.model.cn;

import java.io.Serializable;


/**
 * Created by you on 2017/9/7.
 */

public class CNPinyin<T extends CN> implements Serializable, Comparable<CNPinyin<T>> {

    public static final char DEF_CHAR = '#';

    /**
     * 对应首字首拼音字母
     */
    public char firstChar;
    /**
     * 所有字符中的拼音首字母
     */
    String firstChars;
    /**
     * 对应的所有字母拼音
     */
    String[] pinyins;

    /**
     * 拼音总长度
     */
    int pinyinsTotalLength;

    public final T data;

    public CNPinyin(T data) {
        this.data = data;
    }

    public char getFirstChar() {
        return firstChar;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder().append("--firstChar--").append(firstChar).append("--pinyins:");
        for (String str : pinyins) {
            sb.append(str);
        }
        return sb.toString();
    }

    private int compareValue() {
        if (firstChar == DEF_CHAR) {
            return 'Z' + 1;
        }
        return firstChar;
    }

    public String pinyinStr() {
        StringBuilder sb = new StringBuilder();
//        for (String str : pinyins) {
//            sb.append(str);
//        }
        for (int i = 0; i <pinyins.length ; i++) {
            if(i==0){
                sb.append(pinyins[i]);
            }else{
                sb.append("&").append(pinyins[i]);
            }
        }
        return sb.toString();
    }

//    @Override
//    public int compareTo(CNPinyin<T> tcnPinyin) {
//        int compare = compareValue() - tcnPinyin.compareValue();
//        if (compare == 0) {
//            String chinese1 = data.chinese();
//            String chinese2 = tcnPinyin.data.chinese();
//            return chinese1.compareTo(chinese2);
//        }
//        return compare;
//    }
    @Override
    public int compareTo(CNPinyin<T> tcnPinyin) {
        int compare = compareValue() - tcnPinyin.compareValue();
        if (compare == 0) {
            return pinyinStr().compareTo(tcnPinyin.pinyinStr());
        }
        return compare;
    }

}
