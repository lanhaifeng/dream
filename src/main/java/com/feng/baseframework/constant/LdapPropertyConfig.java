package com.feng.baseframework.constant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * baseframework
 * 2020/2/18 10:11
 * ldap属性配置
 *
 * @author lanhaifeng
 * @since
 **/
@Component
@Getter
@Setter
public class LdapPropertyConfig {

    @Value("${spring.ldap.url}")
    private String ldapUrl;
    @Value("${spring.ldap.base.dc}")
    private String baseDc;
    @Value("${spring.ldap.user.name}")
    private String ldapUserName;
    @Value("${spring.ldap.password}")
    private String ldapPassword;

}
