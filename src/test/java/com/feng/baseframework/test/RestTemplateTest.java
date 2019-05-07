package com.feng.baseframework.test;

import com.feng.baseframework.util.FileUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.net.ssl.*;
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
public class RestTemplateTest extends BaseFrameworkApplicationTest {

    @Resource(name = "sshIgnoreVerificationRestTemplate2")
    private RestTemplate restTemplate;
    @Resource(name = "sshRestTemplate")
    private RestTemplate sshRestTemplate;

    @Test
    public void testRestTemplateGet(){
        String html = restTemplate.getForObject("http://www.baidu.com",String.class);
        System.out.println(html);
    }

    @Test
    public void testRestTemplateHttpsGet() {
        String html = sshRestTemplate.getForObject("https://127.0.0.1:8088/anonymous/redirectMethod/",String.class);
        System.out.println(html);
    }

    @Test
    public void testRestTemplateCapaa() throws Exception {
        RestTemplate restTemplate = getSslRestTemplate();
        String html = sshRestTemplate.getForObject("https://127.0.0.1:8088/anonymous/redirectMethod/",String.class);

        System.out.println(html);
    }

    public static RestTemplate getSslRestTemplate() throws Exception {
        SSLContext ctx = SSLContext.getInstance("SSL");
        String password = "hzmcadminefg";
        TrustManagerFactory trustFactory = TrustManagerFactory.getInstance("SunX509");
        KeyStore tsstore = KeyStore.getInstance("JKS");
        tsstore.load(new FileInputStream(FileUtils.getFileByRelativePath("ssl/trust_certs.keystore")), password.toCharArray());
        trustFactory.init(tsstore);
        TrustManager[] trustManagers = trustFactory.getTrustManagers();

        ctx.init(null, trustManagers, null);

        SSLConnectionSocketFactory sslConnectionSocketFactory =
                new SSLConnectionSocketFactory(ctx,
                        new HostnameVerifier() {
                            // 这里不校验hostname
                            @Override
                            public boolean verify(String urlHostName, SSLSession session) {
                                return true;
                            }
                        });

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(
                HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build()
        );

        RestTemplate restTemplate = new RestTemplate(factory);
        List<HttpMessageConverter<?>> converterList=restTemplate.getMessageConverters();

        FormHttpMessageConverter fc = new FormHttpMessageConverter();
        StringHttpMessageConverter s = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        List<HttpMessageConverter<?>> partConverters = new ArrayList<HttpMessageConverter<?>>();
        partConverters.add(s);
        partConverters.add(new ResourceHttpMessageConverter());
        fc.setPartConverters(partConverters);

        converterList.add(fc);

        HttpMessageConverter<?> converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        converterList.add(converter);
        return restTemplate;
    }
}
