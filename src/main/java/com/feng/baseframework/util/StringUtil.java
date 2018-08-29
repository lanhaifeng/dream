package com.feng.baseframework.util;

import java.util.UUID;

/**
 * baseframework
 * 2018/8/22 14:16
 * 字符串工具类
 *
 * @author lanhaifeng
 * @since
 **/
public class StringUtil {
    public static String generateUUID(){
        UUID key=UUID.randomUUID();
        String str = key.toString();
        return str.replace("-", "");
    }
}
