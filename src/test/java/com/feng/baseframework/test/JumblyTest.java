package com.feng.baseframework.test;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * baseframework
 * 2018/10/22 10:06
 * 混杂的测试类
 *
 * @author lanhaifeng
 * @since
 **/
public class JumblyTest {

	@Test
	public void collectionSortTest(){
		String[] types = new String[]{"0", "1", "2", "2", "", "3"};
		List<String> typeList = Arrays.asList(types);
		Collections.sort(typeList);

		System.out.println(typeList.get(0));
		System.out.println(typeList.get(typeList.size() -1));
	}
}
