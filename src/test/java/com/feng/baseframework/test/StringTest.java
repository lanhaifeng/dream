package com.feng.baseframework.test;

import com.feng.baseframework.util.IPUtil;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        String startIp = "192.168.60.1";
        String endIp = "192.168.60.255";
        String ipStr = "192.168.60.14";
        System.out.println(IPUtil.ipExistsInRange(ipStr,startIp,endIp));
    }

    @Test
    public void PatternTest(){
        String reg = "([2]\\d{3}(((0[13578]|1[02])([0-2]\\d|3[01]))|((0[469]|11)([0-2]\\d|30))|(02([01]\\d|2[0-8])))([01][0-9]|2[0-3])([0-5]\\d)([0-5]\\d))";
        String str = "9003268435225620181029090237606509";
        Pattern pattern = Pattern.compile (reg);
        Matcher matcher = pattern.matcher (str);
        while (matcher.find ())
        {
            System.out.println (matcher.group ());
        }

        String auditId = "90013168773888_1540992369138806";
        int index = auditId.indexOf("_");
        if(index != -1) {
            Long time = Long.valueOf(auditId.substring(index+1)) / 1000;
            System.out.println(time);
        }

    }

}
