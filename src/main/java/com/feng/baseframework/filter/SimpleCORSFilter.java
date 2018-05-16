package com.feng.baseframework.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

/**
 * @ProjectName: baseframework
 * @description: 拦截器处理跨域
 * @author: lanhaifeng
 * @create: 2018-05-16 18:49
 * @UpdateUser:
 * @UpdateDate: 2018/5/16 18:49
 * @UpdateRemark:
 **/
@Component
public class SimpleCORSFilter implements Filter {

    @Override
    public boolean isLoggable(LogRecord record) {
        return false;
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With, cache-control, if-modified-since, pragma");
        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {}

    public void destroy() {}

}
