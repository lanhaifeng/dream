package com.feng.baseframework.util;

import io.jsonwebtoken.lang.Assert;
import org.junit.Test;

public class EhcacheUtilTest {

	@Test
	public void put() {
		EhcacheUtil.getInstance().put(EhcacheUtil.cacheName, "test", "test");
		Assert.state("test".equals(EhcacheUtil.getInstance().get(EhcacheUtil.cacheName, "test")));
	}

}