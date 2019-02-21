package com.feng.baseframework.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

/**
 * baseframework
 * 2019/2/21 16:34
 * 输入内存信息工具类
 *
 * @author lanhaifeng
 * @since
 **/
public class MemoryUtil {

	private static final Logger logger = Logger.getLogger(MemoryUtil.class);

	public static void consoleMemoryInfo(String message){
		MemoryMXBean memorymbean = ManagementFactory.getMemoryMXBean();
		MemoryUsage heapUsage = memorymbean.getHeapMemoryUsage();
		MemoryUsage nonHeapUsage = memorymbean.getNonHeapMemoryUsage();
		if(StringUtils.isNotBlank(message)){
			logger.info(message + ",Memory info console start!");
		}else {
			logger.info("Memory info console start!");
		}

		//heap
		logger.info("init heap:" + heapUsage.getInit()/1024/1024 + "MB");
		logger.info("max heap:" + heapUsage.getMax()/1024/1024 + "MB");
		logger.info("used heap:" + heapUsage.getUsed()/1024/1024 + "MB");
		logger.info("heap memory usage:" + heapUsage.toString());

		//non-heap
		logger.info("init non-heap:" + nonHeapUsage.getInit()/1024/1024 + "MB");
		logger.info("max non-heap:" + nonHeapUsage.getMax()/1024/1024 + "MB");
		logger.info("used non-heap:" + nonHeapUsage.getUsed()/1024/1024 + "MB");
		logger.info("non-heap memory usage:" +nonHeapUsage.toString());

		if(StringUtils.isNotBlank(message)){
			logger.info(message + ",Memory info console end!");
		}else {
			logger.info("Memory info console end!");
		}
	}

	public static void consoleMemoryInfo(){
		consoleMemoryInfo(null);
	}
}
