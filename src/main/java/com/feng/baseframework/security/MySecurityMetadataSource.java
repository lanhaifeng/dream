package com.feng.baseframework.security;

import com.feng.baseframework.mapper.MenuMapper;
import com.feng.baseframework.model.Menu;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.RequestKey;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * baseframework
 * 2019/9/27 17:56
 * 自定义security资源规则处理类
 *
 * @author lanhaifeng
 * @since
 **/
@Component
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource,InitializingBean {

    @Autowired
    private MenuMapper menuMapper;
    private DefaultFilterInvocationSecurityMetadataSource defaultFilterInvocationSecurityMetadataSource;

	@Override
	public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
		return defaultFilterInvocationSecurityMetadataSource.getAttributes(o);
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return defaultFilterInvocationSecurityMetadataSource.getAllConfigAttributes();
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

    @Override
    public void afterPropertiesSet() throws Exception {
        defaultFilterInvocationSecurityMetadataSource = new DefaultFilterInvocationSecurityMetadataSource(initSecurityMetadataSource());
    }

    private Map<String, String> getMenusWithRoles(){
        List<Menu> allMenus = menuMapper.getAllMenusWithRole();
        Map<String, String> menusWithRoles = new HashMap<>();
        Optional.ofNullable(allMenus).orElse(new ArrayList<>()).forEach(menu -> {
            if(menusWithRoles.containsKey(menu.getUrl())){
                String temp = menusWithRoles.get(menu.getUrl());
                menusWithRoles.put(menu.getUrl(), temp + "," + menu.getRoleName());
            }else {
                menusWithRoles.put(menu.getUrl(), menu.getRoleName());
            }
        });

        return menusWithRoles;
    }

    private LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> initSecurityMetadataSource(){
	    LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> resources = new LinkedHashMap<>();
        Map<String, String> menusWithRoles = getMenusWithRoles();

        menusWithRoles.forEach((url, roles)->{
            RequestMatcher requestMatcher = new AntPathRequestMatcher(url);
            Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
            for (String role : roles.split(",")) {
                atts.add(new SecurityConfig(role));
            }
            resources.put(requestMatcher, atts);
        });

	    return resources;
    }
}
