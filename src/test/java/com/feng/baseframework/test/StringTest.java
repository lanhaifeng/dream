package com.feng.baseframework.test;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * baseframework
 * 2018/9/5 11:15
 * 测试类
 *
 * @author lanhaifeng
 * @since
 **/
public class StringTest {

    @Test
    public void arraysTest(){
        List<String> list = new ArrayList<>();
        list.add("abc");
        list.add("hjk");
        list.add("1231");

        System.out.println(StringUtils.join(list,","));

        System.out.println("ROLE_ADMIN,ROLE_AUDIT,ROLE_SECURITY,ROLE_SUPER_ADMIN".length());
        String ip = "192.168.230.23";
        String[] ips = ip.split("\\.");
        System.out.println(ips.length);
        System.out.println(ips[3]);
        StringBuffer jsonStr = new StringBuffer("[qweqw],");
        jsonStr.substring(0,jsonStr.length()-1);
        System.out.println(jsonStr.toString());//[qweqw],
        System.out.println(jsonStr.substring(0,jsonStr.length()-1));//[qweqw]
    }
}
