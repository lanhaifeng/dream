package com.feng.baseframework.jdk.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @ProjectName: baseframework
 * @Description: Collection的测试类
 * @Author: lanhaifeng
 * @CreateDate: 2018/10/24 23:18
 * @UpdateUser:
 * @UpdateDate: 2018/10/24 23:18
 * @UpdateRemark:
 * @Version: 1.0
 */
public class CollectionTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testMapKeySet(){
        Map<String,String> map = new HashMap<>();
        Set<String> set = map.keySet();
    }

    @Test
    public void testSetSize(){
        Set<String> set = new HashSet<>();
        set.add("a");
        set.add("b");
        set.add("");
        set.add(null);

        logger.info("set actual size:" + set.size());
        logger.info("set to array:" + set.toArray());
    }
}
