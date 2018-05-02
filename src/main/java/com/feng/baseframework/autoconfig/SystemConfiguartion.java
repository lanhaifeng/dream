package com.feng.baseframework.autoconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *
 * @ProjectName:    baseframework
 * @Description:    系统配置类
 * @Author:         lanhaifeng
 * @CreateDate:     2018/4/29 23:12
 * @UpdateUser:
 * @UpdateDate:     2018/4/29 23:12
 * @UpdateRemark:
 * @Version:        1.0
 */
@Configuration
@Import({MybatisConfiguration.class,RedisConfiguration.class,RestTemplateConfiguration.class})
public class SystemConfiguartion {
}
