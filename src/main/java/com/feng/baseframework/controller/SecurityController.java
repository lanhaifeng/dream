package com.feng.baseframework.controller;

import com.feng.baseframework.annotation.ClassLevelAdviceTag;
import com.feng.baseframework.annotation.MethodAdvice;
import com.feng.baseframework.annotation.MethodTimeAop;
import com.feng.baseframework.service.RedisService;
import com.feng.baseframework.util.JacksonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * baseframework
 * 2019/7/26 16:54
 * spring security测试
 *
 * @author lanhaifeng
 * @since
 **/
@RestController
@ClassLevelAdviceTag
public class SecurityController {

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private RedisService redisService;

	@RequestMapping(value = "/baseManage/getInfo",method= RequestMethod.GET)
	@MethodTimeAop
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEST')")
	public String baseMethod(){
		logger.info("测试基于内存的简单认证");
		Map<String,String> data = new HashMap<>();
		data.put("name","tom");
		data.put("age","12");
		String str = JacksonUtil.mapToJson(data);
		redisService.set("testAop",str);
		test();
		return str;
	}

	@RequestMapping(value = "/baseManage/testSession",method= RequestMethod.GET)
	@MethodTimeAop
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEST')")
	@MethodAdvice
	public void testSession(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(session != null){
			session.invalidate();
		}
	}

	@RequestMapping(value = {"/anonymous/redirectMethod","/anonymous/redirectMethod/{id}"},method= RequestMethod.GET)
	public String anonymousMethod(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "id", required = false)String id) throws Exception {
		logger.info("测试匿名认证");
		Map<String,String> data = new HashMap<>();
		data.put("name","tom");
		data.put("age","12");
		String str = JacksonUtil.mapToJson(data);
		return str;
	}

	public void test(){
		logger.info("测试同类内是否会aop");
	}
}
