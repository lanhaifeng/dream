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

    @Test
    public void testRestTemplateCapaa() throws Exception {
    	//capaa https走nginx会报错，直接访问https能够正常访问，怀疑nginx证书有问题
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		String sessionId = "8D15933104694293AFE5D0E3704E9BD7";
		List<String> cookies = new ArrayList<>();
		cookies.add("JSESSIONID=" + sessionId);

		requestHeaders.put(HttpHeaders.COOKIE,cookies);

		HttpEntity<String> requestEntity = null;
		String targetUrl = "https://192.168.230.206:18443/capaa/appconfigs/getConfig.public";
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

		RestTemplate restTemplate = getSslRestTemplate();

		ResponseEntity<String> response = restTemplate.exchange(targetUrl, httpMethod,requestEntity,String.class);
		System.out.println(response.getBody());

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
						NoopHostnameVerifier.INSTANCE);

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
