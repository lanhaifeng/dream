package com.feng.baseframework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @ProjectName: svc-search-biz
 * @description: 解析properties工具类
 * @author: lanhaifeng
 * @create: 2018-05-14 11:32
 * @UpdateUser:
 * @UpdateDate: 2018/5/14 11:32
 * @UpdateRemark:
 **/
public class PropertyUtil {
    private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);
    private static Properties props;

    static {
        loadProps();
    }

    synchronized static private void loadProps() {
        if(logger.isDebugEnabled()) {
            logger.debug("开始加载system.properties文件内容.......");
        }
        props = new Properties();
        InputStream in = null;

        try {
            in = PropertyUtil.class.getClassLoader().getResourceAsStream("system.properties");
            props.load(in);
        } catch (FileNotFoundException e) {
            logger.error("system.properties文件未找到");
        } catch (IOException e) {
            logger.error("出现IOException");
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("system.properties文件流关闭出现异常");
            }
        }
        if(logger.isDebugEnabled()) {
            logger.debug("加载system.properties文件内容完成...........");
            logger.debug("system.properties文件内容：" + props);
        }
    }

    public static String getProperty(String key) {
        if (null == props) {
            loadProps();
        }
        return props.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        if (null == props) {
            loadProps();
        }
        return props.getProperty(key, defaultValue);
    }
}
