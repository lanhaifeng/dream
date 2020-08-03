package com.feng.baseframework.util;

import com.feng.baseframework.common.MockitoBaseTest;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JavaCompilerUtilTest extends MockitoBaseTest {

	private String className;
	private String methodName;
	private StringBuffer javaCodes;

	private String classFilePath;
	private String packageName;

	private String outDir;

	@Before
	public void setUp() throws Exception {
		packageName = "com.feng.baseframework.util";
		className = packageName + "." + "JavaCompilerTest";


		javaCodes = new StringBuffer();
		javaCodes.append("package ").append(packageName).append(";");
		javaCodes.append("public class JavaCompilerTest{");
		javaCodes.append("  public static String test(String[] args){");
		javaCodes.append("    return \"hello test compile\";");
		javaCodes.append("  }");
		javaCodes.append("}");

		methodName = "test";

		classFilePath = System.getProperty("user.dir") + File.separator + "src/test/resources/compile\\JavaCompilerTest.java";

		outDir = "C:\\Users\\feng\\Desktop\\test";
	}

	@Test
	public void compile1() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
		Class cls = JavaCompilerUtil.compileByStr(className, javaCodes.toString(), outDir);
		Assert.assertNotNull(cls);
		Assert.assertTrue(className.equals(cls.getName()));

		Object obj = cls.newInstance();
		Method method = cls.getMethod(methodName, String[].class);
		Object invokeResult = method.invoke(obj, new Object[] { new String[] {} });

		Assert.assertNotNull(invokeResult);
		Assert.assertTrue("hello test compile".equals(invokeResult));

		String clsFilePaht = cls.getResource("").getPath();
		Assert.assertTrue(StringUtils.isNotBlank(clsFilePaht)
				&& clsFilePaht.contains("com/feng/baseframework/util"));
	}

	@Test
	public void compile2() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
		Class cls = JavaCompilerUtil.compileByFile(classFilePath, packageName);

		Assert.assertNotNull(cls);
		Assert.assertTrue(className.equals(cls.getName()));

		Object obj = cls.newInstance();
		Method method = cls.getMethod(methodName, String[].class);
		Object invokeResult = method.invoke(obj, new Object[] { new String[] {} });

		Assert.assertNotNull(invokeResult);
		Assert.assertTrue("hello test compile".equals(invokeResult));

		String clsFilePaht = cls.getResource("").getPath();
		Assert.assertTrue(StringUtils.isNotBlank(clsFilePaht)
				&& clsFilePaht.contains("com/feng/baseframework/util"));
	}
}