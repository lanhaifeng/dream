package com.feng.baseframework.common;

import com.feng.baseframework.model.Student;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @ProjectName:    svc-search-biz
 * @Description:    单元测试基础类，用于继承
 * @Author:         兰海峰
 * @CreateDate:     2018/4/28 17:20
 * @UpdateUser:
 * @UpdateDate:     2018/4/28 17:20
 * @UpdateRemark:
 */

@RunWith(MockitoJUnitRunner.class)
public class MockitoBaseTest {
    @Mock
    private Student student;

    @Test
    public void mockitoTest(){
        when(student.getName()).thenReturn("tom");
        Assert.assertEquals("tom", student.getName());
        verify(student).getName();
    }

}
