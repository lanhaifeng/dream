package com.feng.baseframework.listener;

import com.feng.baseframework.event.CustomizeApplicationEvent;
import javafx.fxml.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * baseframework
 * 2020/9/25 18:27
 * 自定义spring监听处理器
 *
 * @author lanhaifeng
 * @since
 **/
@Component
public class CustomizeApplicationEventListener implements ApplicationContextAware,
		ApplicationListener<CustomizeApplicationEvent>,
		ApplicationRunner,
		InitializingBean {

	private static Logger logger = LoggerFactory.getLogger(CustomizeApplicationEventListener.class);
	private ApplicationContext applicationContext;

	@Override
	public void onApplicationEvent(CustomizeApplicationEvent customizeApplicationEvent) {
		Optional.ofNullable(customizeApplicationEvent).ifPresent(event -> {
			logger.info("监听自定义事件:" + customizeApplicationEvent.getClass());
			if(customizeApplicationEvent.getSource() instanceof String){
				logger.info("事件源:" + customizeApplicationEvent.getSource());
			}
		});
	}

	@Override
	public void run(ApplicationArguments applicationArguments) throws Exception {
		applicationContext.publishEvent(new CustomizeApplicationEvent("ApplicationRunner run"));
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		applicationContext.publishEvent(new CustomizeApplicationEvent("InitializingBean afterPropertiesSet"));
	}

	@PostConstruct
	public void postConstruct(){
		applicationContext.publishEvent(new CustomizeApplicationEvent("@PostConstruct"));
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
