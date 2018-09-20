package com.feng.baseframework.autoconfig;

import com.feng.baseframework.constant.GlobalPropertyConfig;
import com.feng.baseframework.constant.SecurityModeEnum;
import com.feng.baseframework.security.MyAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @ProjectName: baseframework
 * @Description: 安全验证配置类
 * @Author: lanhaifeng
 * @CreateDate: 2018/5/21 23:14
 * @UpdateUser:
 * @UpdateDate: 2018/5/21 23:14
 * @UpdateRemark:
 * @Version: 1.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyAuthenticationProvider myAuthenticationProvider;
    @Autowired
    private GlobalPropertyConfig globalPropertyConfig;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //自定义认证
        if (SecurityModeEnum.CUSTOM_AUTHENTICATION.toString().equals(globalPropertyConfig.getSecurityMode())) {
            auth.authenticationProvider(myAuthenticationProvider);
        }
        //默认认证
        if (SecurityModeEnum.DEFAULT_AUTHENTICATION.toString().equals(globalPropertyConfig.getSecurityMode())) {
            auth.inMemoryAuthentication().withUser("admin").password("admin").
                    roles("ADMIN").and().withUser("user").password("user").roles("USER").
                    and().withUser("test").password("test").roles("TEST");
        }
        //无认证
        if (SecurityModeEnum.NO_AUTHENTICATION.toString().equals(globalPropertyConfig.getSecurityMode()));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //自定义认证
        if (SecurityModeEnum.CUSTOM_AUTHENTICATION.toString().equals(globalPropertyConfig.getSecurityMode())) {
            http
                    .authorizeRequests()
                    .antMatchers("/", "/index").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                    .logout()
                    .logoutSuccessUrl("/login")
                    .permitAll()
                    .invalidateHttpSession(true)
                    .and()
                    .rememberMe()
                    .tokenValiditySeconds(1209600);
        }
        //默认认证
        if (SecurityModeEnum.DEFAULT_AUTHENTICATION.toString().equals(globalPropertyConfig.getSecurityMode())) {
            http.formLogin()          // 定义当需要用户登录时候，转到的登录页面。
                    .and()
                    .authorizeRequests()    // 定义哪些URL需要被保护、哪些不需要被保护
                    .antMatchers("/anonymous/**").anonymous()  //定义那些url匿名认证
                    .anyRequest()        // 任何请求,登录后可以访问
                    .authenticated();
        }
        //无认证
        if (SecurityModeEnum.NO_AUTHENTICATION.toString().equals(globalPropertyConfig.getSecurityMode()));
    }

}
