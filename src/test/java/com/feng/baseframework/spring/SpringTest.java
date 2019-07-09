package com.feng.baseframework.spring;

import com.feng.baseframework.common.JunitBaseTest;
import com.feng.baseframework.util.SpringUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContextAware;

import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * @ProjectName: baseframework
 * @Description: spring test
 * @Author: lanhaifeng
 * @CreateDate: 2019/7/6 19:04
 * @UpdateUser:
 * @UpdateDate: 2019/7/6 19:04
 * @UpdateRemark:
 * @Version: 1.0
 */
public class SpringTest extends JunitBaseTest {

    //@Autowired(required = false)
    //会报错，提示没有对应BeanDefinition
    private ApplicationContextAware applicationContextAware;

    @Test
    public void testIgnoreDependencyInterface(){
        Assert.assertNull( "该接口未忽略自动注入", applicationContextAware);
        boolean result = false;
        try{
        	SpringUtil.getBeanByName("springUtil");
        }catch(NoSuchBeanDefinitionException e){
            result = true;
        }
        Assert.assertTrue("该接口未忽略自动注入，未报期待的NoSuchBeanDefinitionException异常", result);
    }

    @Test
    public void messageResourceTest(){
        //还未初始化资源，设置默认Locale有效果
        Locale.setDefault(Locale.CANADA_FRENCH);
        Object[] params = {"John", new GregorianCalendar().getTime()};
        String str3 = SpringUtil.getApplicationContext().getMessage("test", params, Locale.CANADA_FRENCH);
        Assert.assertTrue("国际化失败，实际：" + str3, "default".equals(str3));

        //已经初始化资源，设置默认Locale无效
        Locale.setDefault(Locale.CHINA);
        str3 = SpringUtil.getApplicationContext().getMessage("test", params, Locale.CANADA_FRENCH);
        Assert.assertTrue( "国际化失败，实际：" + str3, !"测试".equals(str3));
        Assert.assertTrue("国际化失败，实际：" + Locale.getDefault().toString(), "zh_CN".equals(Locale.getDefault().toString()));

        String str1 = SpringUtil.getApplicationContext().getMessage("test", params, Locale.CHINA);
        String str2 = SpringUtil.getApplicationContext().getMessage("test", params, Locale.US);
        Assert.assertTrue("国际化失败，实际：" + str1, "测试".equals(str1) );
        Assert.assertTrue( "国际化失败，实际：" + str2, "test".equals(str2));

    }
}
