package com.feng.baseframework.autoconfig;

import com.feng.baseframework.interceptor.SimpleHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * baseframework
 * 2018/9/13 17:27
 * 拦截器配置
 *
 * @author lanhaifeng
 * @since
 **/
@Configuration
public class WebInterceptorConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加自定义拦截器和拦截路径，此处对所有请求进行拦截，除了登录界面和登录接口
        registry.addInterceptor(new SimpleHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/user/login");
    }
}
