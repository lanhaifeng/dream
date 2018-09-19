package com.feng.baseframework.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ProjectName: baseframework
 * @description: 拦截器处理跨域
 * 单例，由容器负责初始化、销毁
 *
 * @author: lanhaifeng
 * @create: 2018-05-16 18:49
 * @UpdateUser:
 * @UpdateDate: 2018/5/16 18:49
 * @UpdateRemark:
 **/
@WebFilter(urlPatterns = { "/*" }, filterName = "simpleCORSFilter")
public class SimpleCORSFilter implements Filter {

    private Logger logger = Logger.getLogger(getClass());

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        logger.info("我是过滤器simpleCORSFilter");
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With, cache-control, if-modified-since, pragma");
        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {logger.info("过滤器simpleCORSFilter初始化");}

    public void destroy() {}


}
