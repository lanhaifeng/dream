package com.feng.baseframework.common;

import com.feng.baseframework.model.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

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
@RunWith(PowerMockRunner.class)
@PrepareForTest(Student.class)
public class PowerMockitoBaseTest {

	@Mock
	private Student student = new Student();

	@Test
    public void powerMockit() throws Exception {
        student = PowerMockito.spy(student);
        PowerMockito.when(student,"check").thenReturn(true);
        String name = student.getPrivateName("tom");
        assertEquals("private 被mock 了", name);
    }

}
