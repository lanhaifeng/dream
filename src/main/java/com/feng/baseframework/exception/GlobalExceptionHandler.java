package com.feng.baseframework.exception;


import com.feng.baseframework.model.DataResult;
import com.feng.baseframework.util.DataResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

/**
 * @ProjectName: svc-search-biz
 * @description: 全局异常处理类
 * @author: lanhaifeng
 * @create: 2018-05-02 15:42
 * @UpdateUser:
 * @UpdateDate: 2018/5/2 15:42
 * @UpdateRemark:
 **/
@ControllerAdvice
public class GlobalExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public DataResult handler(Exception e){
        if( e instanceof RedisException){
            RedisException redisException = (RedisException) e;
            logger.info("[" + redisException.getMessage() + "] {}" , getStackTrace(e));
            return DataResultUtil.error( redisException.getCode(), redisException.getMessage());
        }else {
            logger.info("[系统异常] {}",getStackTrace(e));
            return DataResultUtil.error( -1, "未知错误");
        }
    }

    private String getStackTrace(Throwable t) {
        return Optional.ofNullable(t).map((e)->{
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw, true);
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
            return sw.toString();
        }).orElse("");
    }
}
