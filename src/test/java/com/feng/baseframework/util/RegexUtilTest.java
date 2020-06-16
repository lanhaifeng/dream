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

		regex = "^TEST.*$";
		String ruleName = "TESTAff";
		Assert.state(RegexUtil.regexSql(regex, ruleName));

		regex = "SELECT(\\s)+A(\\s)+B(\\s)+FROM.*";
		sqltext = "SELECT  A  B  FROM tab";
		Assert.state(RegexUtil.regexSql(regex, sqltext));

		regex = "SELECT(\\s)+\\*(\\s)+FROM(\\s)+TAB.*";
		sqltext = "sELECT   *   FROM TAB asdf";
		Assert.state(RegexUtil.regexSql(regex, sqltext, true));

		regex = "(?i)INSERT(\\s)+INTO(\\s)+TAB(\\s)+VALUES\\(.*\\).*";
		sqltext = "insert INTO TAB VALUES(...)";
		Assert.state(RegexUtil.regexSql(regex, sqltext));

		regex = "#  ` %00 --+ -- - /* */ %09 %0A %0B  %0C %0D %A0 %20 /*! 、*/";
		sqltext = "INSERT INTO TAB VALUES(...)";
		Assert.state(!RegexUtil.regexSql(regex, sqltext));

		regex = "a\\c";
		regex = regex.replaceAll("\\\\", "\\\\\\\\");
		System.out.println(regex);
	}
}