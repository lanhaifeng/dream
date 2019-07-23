package com.feng.baseframework.test;

import com.feng.baseframework.util.FileUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
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
@ActiveProfiles("pro")
public class RestTemplateTest extends BaseFrameworkApplicationTest {

    @Resource(name = "restTemplate")
    private RestTemplate simpleRestTemplate;
	@Resource(name = "sshIgnoreVerificationRestTemplate")
	private RestTemplate ignoreVerificationrestTemplate;
	@Resource(name = "sshIgnoreVerificationRestTemplate2")
	private RestTemplate ignoreVerificationrestTemplate2;
    @Resource(name = "sshRestTemplate")
    private RestTemplate sshRestTemplate;

    @Test
    public void testRestTemplateGet1(){
		String html = simpleRestTemplate.getForObject("https://127.0.0.1:1443/anonymous/redirectMethod/",String.class);
        System.out.println(html);
    }

    @Test
    public void testRestTemplateGet2(){
		String html = ignoreVerificationrestTemplate.getForObject("https://127.0.0.1:1443/anonymous/redirectMethod/",String.class);
		System.out.println(html);
    }

    @Test
    public void testRestTemplateGet3(){
		String html = ignoreVerificationrestTemplate2.getForObject("https://127.0.0.1:1443/anonymous/redirectMethod/",String.class);
		System.out.println(html);
    }

    @Test
    public void testRestTemplateHttpsGet() {
        String html = sshRestTemplate.getForObject("https://127.0.0.1:1443/anonymous/redirectMethod/",String.class);
        System.out.println(html);
    }
}
