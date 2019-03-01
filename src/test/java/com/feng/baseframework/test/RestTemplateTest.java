package com.feng.baseframework.test;

import org.junit.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

    @Resource(name = "sshRestTemplate")
    private RestTemplate restTemplate;

    @Test
    public void testRestTemplateGet(){
        String html = restTemplate.getForObject("http://www.baidu.com",String.class);
        System.out.println(html);
    }

    @Test
    public void testRestTemplateHttps() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String sessionId = "2462CE3D190389EF832184310C61E377";
        List<String> cookies = new ArrayList<>();
        cookies.add("JSESSIONID=" + sessionId);

        requestHeaders.put(HttpHeaders.COOKIE,cookies);

        HttpEntity<String> requestEntity = null;
        String targetUrl = "https://192.168.230.206/capaa/searchBiz/ruleFactor/0/9003139968276064000_1551344443850201";
        HttpMethod httpMethod = HttpMethod.GET;
        String body = "{}";
        switch (httpMethod){
            case GET:
                requestEntity = new HttpEntity<String>(requestHeaders);
                break;
            case POST:
                requestEntity = new HttpEntity<String>(body, requestHeaders);
                break;
        }

        ResponseEntity<String> response = restTemplate.exchange(targetUrl, httpMethod,requestEntity,String.class);
        System.out.println(response.getBody());
    }
}
