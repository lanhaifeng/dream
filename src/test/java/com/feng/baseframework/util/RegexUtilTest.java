package com.feng.baseframework.util;

import io.jsonwebtoken.lang.Assert;
import org.junit.Test;

public class RegexUtilTest {

	@Test
	public void regexSql() {
		String regex = "SELECT COUNT.* FROM INFORMATION_SCHEMA.*";
		String sqltext = "SELECT COUNT ( * ) FROM INFORMATION_SCHEMAtest";
		Assert.state(RegexUtil.regexSql(regex, sqltext));

		regex = "SELECT COUNT \\( \\* \\) FROM INFORMATION_SCHEMA.*";
		Assert.state(RegexUtil.regexSql(regex, sqltext));
	}
}