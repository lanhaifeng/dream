package com.feng.baseframework.controller;

import com.feng.baseframework.util.JacksonUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * baseframework
 * 2018/9/13 9:24
 * 基础控制器，接口测试用
 *
 * @author lanhaifeng
 * @since
 **/
@RestController
public class BaseController {

    @RequestMapping(value = "/baseManage/getInfo",method= RequestMethod.GET)
    public String baseMethod(){
        Map<String,String> data = new HashMap<>();
        data.put("name","tom");
        data.put("age","12");

        return JacksonUtil.mapToJson(data);
    }
}
