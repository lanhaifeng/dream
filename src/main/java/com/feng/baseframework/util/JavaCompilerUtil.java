package com.feng.baseframework.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.*;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

/**
 * baseframework
 * 2020/7/29 15:51
 * java编译工具类
 *
 * @author lanhaifeng
 * @since
 **/
public class JavaCompilerUtil {

	static Logger logger = LoggerFactory.getLogger(JavaCompilerUtil.class);

	/**
	 * 2020/7/29 16:49
	 * 将字符串编译成class文件并加载
	 *
	 * @param className	类完整名
	 * @param javaCodes	代码内容
	 * @param outDir	输出路径，默认放在源文件路径下
	 * @author lanhaifeng
	 * @return java.lang.Class<?>
	 */
	public static Class<?> compileByStr(String className, String javaCodes, String outDir){
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics,null, null);

		StrSrcJavaObject srcObject = new StrSrcJavaObject(className, javaCodes);
		Iterable<? extends JavaFileObject> fileObjects = Arrays.asList(srcObject);

		String flag = "-d";
		outDir = getOutputDir(outDir);
		Iterable<String> options = Arrays.asList(flag, outDir);

		JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, options, null, fileObjects);
		boolean result = task.call();
		if (result == true) {
			try {
				return Class.forName(className);
			} catch (ClassNotFoundException e) {
				logger.error("加载class文件失败，错误：" + ExceptionUtils.getFullStackTrace(e));
			}
		}
		return null;
	}

	private static String getOutputDir(String outDir) {
		String currentDir = "";
		try {
			File classPath = new File(Thread.currentThread().getContextClassLoader().getResource("").toURI());
			currentDir = classPath.getAbsolutePath() + File.separator;
		} catch (URISyntaxException e) {
			logger.error("获取当前类路径失败，错误：" + ExceptionUtils.getFullStackTrace(e));
		}
		if(StringUtils.isNotBlank(outDir)){
			File outDirFile = new File(outDir);
			if(!outDirFile.exists()){
				outDirFile.mkdirs();
			}
			if(!outDirFile.isDirectory()){
				outDir = currentDir;
				outDirFile.delete();
			}
		}
		return outDir;
	}

	/**
	 * 2020/7/30 10:55
	 * 将.java文件编译成class文件并加载
	 *
	 * @param classFileName
	 * @param packageName
	 * @author lanhaifeng
	 * @return java.lang.Class<?>
	 */
	public static Class<?> compileByFile(String classFileName, String packageName, String outDir) {
		if(StringUtils.isBlank(classFileName) || !classFileName.endsWith(".java")) return null;
		outDir = getOutputDir(outDir);
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		int result = compiler.run(null, null, null, "-d" , outDir, classFileName);
		if(result == 0){
			int separatorIndex = classFileName.lastIndexOf(File.separator);
			if(separatorIndex == -1) separatorIndex = classFileName.lastIndexOf("\\");

			try {
				String className = classFileName.substring(separatorIndex + 1, classFileName.length() - 5);
				URL[] urls = new URL[]{new URL("file:/" + classFileName.substring(0, separatorIndex + 1))};
				URLClassLoader classLoader = new URLClassLoader(urls);
				if(StringUtils.isNotBlank(packageName)){
					className = packageName + "." + className;
				}

				return classLoader.loadClass(className);
			} catch (Exception e) {
				logger.error("加载class文件失败，错误：" + ExceptionUtils.getFullStackTrace(e));
			}
		}

		return null;
	}

	private static class StrSrcJavaObject extends SimpleJavaFileObject {
		private String content;
		public StrSrcJavaObject(String name, String content) {
			super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
			this.content = content;
		}
		public CharSequence getCharContent(boolean ignoreEncodingErrors) {
			return content;
		}
	}
}
