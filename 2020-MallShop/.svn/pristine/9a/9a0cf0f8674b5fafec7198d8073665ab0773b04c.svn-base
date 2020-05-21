package com.mike.baselib.utils;

import java.util.ArrayList;
import java.util.List;


/**
 * Author: jack
 * Description:管理请求
 */
public class RequestCollector {

    public static List<String> requests= new ArrayList<>();

    public static void addRequest(String type) {
        if(!requests.contains(type)){
            requests.add(type);
        }

    }

    public static void removeRequest(String type) {
        if(requests.contains(type)){
            requests.remove(type);
        }
    }

    public static boolean isRequestExist(String type){
        return requests.contains(type);
    }
}