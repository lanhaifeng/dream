package com.feng.baseframework.controller;

import com.feng.baseframework.model.User;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * baseframework
 * 2018/9/13 9:24
 * 基础控制器，接口测试用
 *
 * @author lanhaifeng
 * @since
 **/
@RestController
@Validated
public class BaseController {

    private Logger logger = Logger.getLogger(getClass());

	@RequestMapping(value = "/baseManage/getWebRootPath",method=RequestMethod.GET)
    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
	public String getWebRootPath() {
    	return System.getProperty("projectRootPath") == null ? "" : System.getProperty("projectRootPath");
	}

    //BindingResult只能用于@RequestPart @RequestBody，并和@Validated成对出现
    @RequestMapping(value = "/baseManage/validate1",method=RequestMethod.POST)
    public String validateTest1(@RequestBody @Validated User user, BindingResult result){
        String response = "true";
        if(result.hasErrors()){
            List<ObjectError> allErrors = result.getAllErrors();
            for (ObjectError allError : allErrors) {
                System.out.println(allError.getDefaultMessage());
            }
        }
        return  response;
    }

    @RequestMapping(value = "/baseManage/validate2",method=RequestMethod.POST)
    public String validateTest2(@RequestBody @Valid User user){
        String response = "true";
        return  response;
    }

    @RequestMapping(value = "/baseManage/validate3",method=RequestMethod.GET)
    public String validateTest3(@Valid @NotEmpty String auditId){
        String response = "true";
        return  response;
    }

    @RequestMapping(value = "/baseManage/validate4",method=RequestMethod.GET)
    public String validateTest4(@Validated @NotEmpty String auditId){
        String response = "true";
        System.out.println(auditId);
        return  response;
    }
}
