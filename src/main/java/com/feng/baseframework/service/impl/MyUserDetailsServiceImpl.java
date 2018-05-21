package com.feng.baseframework.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: baseframework
 * @Description: 用户认证业务处理类
 * @Author: lanhaifeng
 * @CreateDate: 2018/5/21 23:19
 * @UpdateUser:
 * @UpdateDate: 2018/5/21 23:19
 * @UpdateRemark:
 * @Version: 1.0
 */
@Service("myUserDetailsService")
public class MyUserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
