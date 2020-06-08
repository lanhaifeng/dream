package com.feng.baseframework.util;

import org.junit.Test;

public class RegexUtilTest {

	@Test
	public void regexSql() {
		String regex = "SELECT COUNT.* FROM INFORMATION_SCHEMA.*";
		String sqltext = "SELECT COUNT ( * ) FROM INFORMATION_SCHEMAtest";
		System.out.println(RegexUtil.regexSql(regex, sqltext));
	}
}