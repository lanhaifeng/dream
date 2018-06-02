package com.feng.baseframework.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ProjectName: baseframework
 * @Description: 正则表达式测试类
 * @Author: lanhaifeng
 * @CreateDate: 2018/6/2 11:01
 * @UpdateUser:
 * @UpdateDate: 2018/6/2 11:01
 * @UpdateRemark:
 * @Version: 1.0
 */
@RunWith(JUnit4.class)
public class RegularExpressionTest {

    @Test
    public void testPattern(){
        String input = "AND   \"$应用程序名\"   = 'MYSQL TOMCAT' AND    \"$应用程序名\" in ('tomcat','tmp') OR     \"$应用程序名\" not  in ('tomcat','tmp')";
        String regex = "(\\s*(AND|OR)\\s*)(\"[$]\\S+\\s*)(=\\s+|in\\s+|not\\s+in\\s+)((['\"\\(])(\\S*)([']|\\)))";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()){
            System.out.println(matcher.groupCount());
            System.out.println(matcher.group(0));//AND   "$应用程序名"   = '
            System.out.println(matcher.group(1));//AND  有空格
            System.out.println(matcher.group(2));//AND无空格
            System.out.println(matcher.group(3));//"$应用程序名"
            System.out.println(matcher.group(4));//=
            System.out.println(matcher.group(5));//'
            System.out.println(matcher.group(6));//
            System.out.println(matcher.group(7));//'
        }
    }
}
