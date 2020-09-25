package com.feng.baseframework.listener;

import com.feng.baseframework.event.CustomizeApplicationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * baseframework
 * 2020/9/25 18:27
 * 自定义spring监听处理器
 *
 * @author lanhaifeng
 * @since
 **/
@Component
public class CustomizeApplicationEventListener implements ApplicationListener<CustomizeApplicationEvent> {

	private static Logger logger = LoggerFactory.getLogger(CustomizeApplicationEventListener.class);

	@Override
	public void onApplicationEvent(CustomizeApplicationEvent customizeApplicationEvent) {
		Optional.ofNullable(customizeApplicationEvent).ifPresent(event -> {
			logger.info("监听自定义事件:" + customizeApplicationEvent.getClass());
		});
	}
}
