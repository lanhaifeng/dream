package com.feng.baseframework.jdk8.util;

import com.feng.baseframework.model.Student;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

/**
 * @ProjectName: baseframework
 * @Description: 工具类Optional测试
 * @Author: lanhaifeng
 * @CreateDate: 2018/5/14 22:50
 * @UpdateUser:
 * @UpdateDate: 2018/5/14 22:50
 * @UpdateRemark:
 * @Version: 1.0
 */
public class OptionalTest {

	@Test
	public void testMap(){
		Student student = null;
		if(Optional.ofNullable(student).map(e -> e.getId()).orElse(0) > 0){
			System.out.println("1.student id is not null;");
		}

		student = new Student();
		if(Optional.ofNullable(student).map(e -> e.getId()).orElse(0) > 0){
			System.out.println("2.student id is not null;");
		}

		student.setId(1);
		if(Optional.ofNullable(student).map(e -> e.getId()).orElse(0) > 0){
			System.out.println("3.student id is not null;");
		}

	}

	@Test
	public void testGet(){
		Integer id = null;
		id = Optional.ofNullable(id).orElse(1);

		Assert.assertTrue("Optional赋值失败",id == 1);
	}
}
