package com.feng.baseframework.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

/**
 * @ProjectName: baseframework
 * @Description: restTemplate单元测试类
 * @Author: lanhaifeng
 * @CreateDate: 2018/5/1 23:47
 * @UpdateUser:
 * @UpdateDate: 2018/5/1 23:47
 * @UpdateRemark:
 * @Version: 1.0
 */
public class RestTemplateTest extends BaseFrameworkApplicationTest {
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testRestTemplateGet(){
        String html = restTemplate.getForObject("http://www.baidu.com",String.class);
        System.out.println(html);
    }
}
