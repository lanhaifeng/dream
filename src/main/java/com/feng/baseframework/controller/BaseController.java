package com.feng.baseframework.controller;

import com.feng.baseframework.annotation.MethodTimeAop;
import com.feng.baseframework.model.RuleTp;
import com.feng.baseframework.model.User;
import com.feng.baseframework.service.RedisService;
import com.feng.baseframework.util.DroolsUtil;
import com.feng.baseframework.util.JacksonUtil;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.client.solrj.response.CoreAdminResponse;
import org.apache.solr.common.SolrDocument;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

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

    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    private RedisService redisService;
    @Resource
    private KieSession kieSession;
	@Autowired
	private SolrClient solrClient;


	@RequestMapping(value = "/baseManage/getInfo",method= RequestMethod.GET)
    @MethodTimeAop
    @PreAuthorize("hasAnyRole('ADMIN','TEST')")
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

    @RequestMapping(value = "/anonymous/redirectMethod",method= RequestMethod.GET)
    public String anonymousMethod(HttpServletRequest request, HttpServletResponse response) throws Exception {
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


    @RequestMapping(value = "/drools/student",method={RequestMethod.GET, RequestMethod.POST})
    public String droolsTest(@RequestBody User user){
        kieSession.insert(user);
        int ruleFiredCount = kieSession.fireAllRules();

        String response = user == null || user.getUserName() == null ? "" : user.getUserName() + "触发了" + ruleFiredCount + "条规则";
        return  response;
    }

    @RequestMapping(value = "/drools/dynamicStudent",method={RequestMethod.GET, RequestMethod.POST})
    public String dynamicLoadRule(@RequestBody User user) throws CloneNotSupportedException {
    	Long start = new Date().getTime();
        KieSession kieSession = DroolsUtil.getInstance().dynamicLoadRule(loadRuleTps());
        List<User> users = new ArrayList<>();
        for(int i=0;i<1000;i++){
        	User domain = user.clone();
			kieSession.insert(domain);
			users.add(domain);
		}
        int ruleFiredCount = kieSession.fireAllRules();

        //释放资源
        for(User temp : users){
			kieSession.delete(kieSession.getFactHandle(temp));
		}
		kieSession.dispose();
		users.clear();

		Long end = new Date().getTime();
		Long time = end - start;
        String response = "总共触发了" + ruleFiredCount + "次动态加载规则,"+"耗时：" + time + "ms";
        return  response;
    }

	@RequestMapping(value = "/baseManage/getWebRootPath",method=RequestMethod.GET)
	public String getWebRootPath() {
    	return System.getProperty("projectRootPath") == null ? "" : System.getProperty("projectRootPath");
	}

    private List<RuleTp> loadRuleTps(){
        List<RuleTp> ruleTps = new ArrayList<>();
        ruleTps.add(new RuleTp("matd : User(userName != null, userName == \"admin\");","System.out.println(\"动态加载rule，admin用户!\");",0));
        ruleTps.add(new RuleTp("matd : User(userName != null, userName != \"admin\");","System.out.println(\"动态加载rule，非admin用户!\");",1));
		ruleTps.add(new RuleTp("matd : User(userName != null, userName != \"admin\");","System.out.println(\"动态加载rule，非admin用户!\");",1));
		ruleTps.add(new RuleTp("matd : User(userName != null, userName != \"admin\");","System.out.println(\"动态加载rule，非admin用户!\");",1));
		ruleTps.add(new RuleTp("matd : User(userName != null, userName != \"admin\");","System.out.println(\"动态加载rule，非admin用户!\");",1));
		ruleTps.add(new RuleTp("matd : User(userName != null, userName != \"admin\");","System.out.println(\"动态加载rule，非admin用户!\");",1));
		ruleTps.add(new RuleTp("matd : User(userName != null, userName != \"admin\");","System.out.println(\"动态加载rule，非admin用户!\");",1));
		ruleTps.add(new RuleTp("matd : User(userName != null, userName != \"admin\");","System.out.println(\"动态加载rule，非admin用户!\");",1));
		ruleTps.add(new RuleTp("matd : User(userName != null, userName != \"admin\");","System.out.println(\"动态加载rule，非admin用户!\");",1));
		ruleTps.add(new RuleTp("matd : User(userName != null, userName != \"admin\");","System.out.println(\"动态加载rule，非admin用户!\");",1));
		ruleTps.add(new RuleTp("matd : User(userName != null, userName != \"admin\");","System.out.println(\"动态加载rule，非admin用户!\");",1));
		ruleTps.add(new RuleTp("matd : User(userName != null, userName != \"admin\");","System.out.println(\"动态加载rule，非admin用户!\");",1));
		ruleTps.add(new RuleTp("matd : User(userName != null, userName != \"admin\");","System.out.println(\"动态加载rule，非admin用户!\");",1));
        return  ruleTps;
    }


	@RequestMapping(value = "/baseManage/solrSearch",method=RequestMethod.GET)
	public String solrSearch() {
		SolrDocument solrDocument = null;
		try {
			CoreAdminResponse coreAdminResponse = CoreAdminRequest.getStatus("2",solrClient);
			logger.info(coreAdminResponse.toString());
			if(coreAdminResponse == null || coreAdminResponse.getCoreStatus("2") == null || coreAdminResponse.getCoreStatus("2").size() < 1){
				CoreAdminRequest.createCore("2","2",solrClient);
			}

			logger.info(coreAdminResponse.toString());

			solrDocument = solrClient.getById("2","1");
		} catch (SolrServerException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}

		return solrDocument == null ? "" : solrDocument.toString();
	}
}
