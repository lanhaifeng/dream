package com.feng.baseframework.autoconfig;

/**
 * baseframework
 * 2019/8/8 23:22
 * 初始化系统属性
 *
 * @author lanhaifeng
 * @since
 **/
public class SystemPropertyInit {

	public static void initSystemProperty(){
		System.setProperty("jasypt.encryptor.password", "feng");
	}
}
