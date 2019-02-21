package com.feng.baseframework.test;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * baseframework
 * 2018/12/11 16:04
 * 正则表达式测试
 *
 * @author lanhaifeng
 * @since
 **/
public class RegexTest {

	@Test
	public void test() {
		String content = "01";
		String pattern = "(-1|0|1|13)";
		boolean isMatch = Pattern.matches(pattern, content);
		System.out.println(isMatch);

		pattern = "([^<>]+|(\\s)?)";
		content = "^";
		isMatch = Pattern.matches(pattern, content);
		System.out.println(isMatch);

        pattern = "\\S\\^";
        content = "b^";
        isMatch = Pattern.matches(pattern, content);
        System.out.println(isMatch);
	}
}
