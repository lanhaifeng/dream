package com.feng.baseframework.processor;

import com.feng.baseframework.annotation.CustomOnProfileCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Iterator;

/**
 * baseframework
 * 2020/7/2 17:31
 * spring post processor学习
 *
 * @author lanhaifeng
 * @since
 **/
@Component
//@CustomOnProfileCondition("dev")等同于@ConditionalOnProperty(prefix = "spring.profiles", name = "active", havingValue = "dev")
@CustomOnProfileCondition("dev")
public class PrintBeanFactoryPostPorcessor implements BeanFactoryPostProcessor {

	private Logger logger = LoggerFactory.getLogger(PrintBeanFactoryPostPorcessor.class);

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
		Iterator<String> beanNames = configurableListableBeanFactory.getBeanNamesIterator();
		while (beanNames.hasNext()){
			String beanName = beanNames.next();
			if(logger.isDebugEnabled()){
				logger.debug("beanName：" + beanName);
			}else {
				System.out.println("beanName：" + beanName);
			}
			if(configurableListableBeanFactory.containsBeanDefinition(beanName)){
				BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition(beanName);
				if(beanDefinition.getBeanClassName() != null){
					if(logger.isDebugEnabled()) {
						logger.debug("beanClassName：" + beanDefinition.getBeanClassName());
					}else {
						System.out.println("beanClassName：" + beanDefinition.getBeanClassName());
					}
				}else {
					if(logger.isDebugEnabled()){
						logger.debug("factoryBeanName：" + beanDefinition.getFactoryBeanName() + ",factoryMethodName："
								+ beanDefinition.getFactoryMethodName());
					}else {
						System.out.println("factoryBeanName：" + beanDefinition.getFactoryBeanName() + ",factoryMethodName："
								+ beanDefinition.getFactoryMethodName());
					}
				}
			}
		}
	}
}
