package com.feng.baseframework.log;

import com.feng.baseframework.common.MockitoBaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

/**
 * baseframework
 * 2020/9/4 11:02
 * 测试日志
 *
 * @author lanhaifeng
 * @since
 **/
public class TestLog extends MockitoBaseTest {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void testJul1() {
		java.util.logging.Logger julLogger = java.util.logging.Logger.getLogger(getClass().toString());
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(new ConsoleLogFormatter());
		julLogger.addHandler(consoleHandler);
		julLogger.setUseParentHandlers(false);

		julLogger.log(Level.INFO, "test jul log");
		julLogger.log(Level.INFO, "test jul log");
	}

	//profile=jul
	@Test
	public void testJul2() {
		logger.info("test jul log");
		logger.info("test jul log");
	}

	//profile=jcl
	@Test
	public void testJcl1() {
		logger.info("test jcl log");
		logger.info("test jcl log");
	}

	//profile=jcl
	@Test
	public void testJcl2() {
		logger.info("test jcl log");
		logger.info("test jcl log");
	}

	//profile=log4j
	//test -P SlowTests -P log4j -Dtest=com.feng.baseframework.log.TestLog#testLog4j1
	@Test
	public void testLog4j1() {
		System.setProperty("log4jConfigLocation", "classpath:log/log4j.properties");
		logger.info("test log4j log");
		logger.info("test log4j log");
	}

	//profile=log4j
	@Test
	public void testLog4j2() {
		System.out.println(logger.getClass().toString());
		logger.info("test log4j log");
		logger.info("test log4j log");
	}
}
