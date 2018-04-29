package com.feng.baseframework;

import com.feng.baseframework.autoconfig.SystemConfiguartion;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 *
 * @ProjectName:    baseframework
 * @Description:    项目入口
 * @Author:         lanhaifeng
 * @CreateDate:     2018/4/29 23:11
 * @UpdateUser:
 * @UpdateDate:     2018/4/29 23:11
 * @UpdateRemark:
 * @Version:        1.0
 */
@SpringBootApplication
@Import(SystemConfiguartion.class)
public class BaseframeworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaseframeworkApplication.class, args);
	}
}
