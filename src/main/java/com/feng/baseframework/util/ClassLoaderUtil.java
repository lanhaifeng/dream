package com.feng.baseframework.util;

import com.feng.baseframework.constant.FileTypeEnum;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * baseframework
 * 2020/8/4 14:52
 * 类加载工具类
 *
 * @author lanhaifeng
 * @since
 **/
public class ClassLoaderUtil {

	static Logger log = LoggerFactory.getLogger(ClassLoaderUtil.class);
	private final static String __LOAD_PRE_PATH = "file:";

	/**
	 * 2020/8/4 15:05
	 * 加载class文件
	 *
	 * @param fullClassName
	 * @param sourcePath
	 * @author lanhaifeng
	 * @return java.lang.Class<?>
	 */
	public static Class<?> loadClassByPath(String fullClassName, String sourcePath) {
		if (StringUtils.isBlank(fullClassName) || StringUtils.isBlank(sourcePath)) {
			throw new RuntimeException("参数非法");
		}

		String loadPath = parseSourcePath(sourcePath);
		try {
			URL[] urls = new URL[]{new URL(loadPath)};
			URLClassLoader classLoader = new URLClassLoader(urls, ClassLoader.getSystemClassLoader());
			return classLoader.loadClass(fullClassName);
		} catch (Exception e) {
			log.error(String.format("加载%s失败：", fullClassName) + ExceptionUtils.getFullStackTrace(e));
			return null;
		}
	}

	/**
	 * 2020/8/4 16:19
	 * 加载指定目录下的class文件
	 *
	 * @param rootPath		class根目录，不包括包目录
	 * @param sourcePath	资源目录，查找class文件的目录
	 * @author lanhaifeng
	 * @return java.util.Collection<java.lang.Class<?>>
	 */
	public static Collection<Class<?>> loadDirClassByPath(String rootPath, String sourcePath, String packageName){
		if (StringUtils.isBlank(rootPath)) {
			throw new RuntimeException("参数非法");
		}
		if (StringUtils.isBlank(sourcePath)) {
			sourcePath = rootPath;
		}
		Set<Class<?>> classes = new LinkedHashSet<>();

		try {
			if (packageName == null) packageName = "";
			File root = new File(sourcePath);
			if (!root.exists()) return classes;
			String loadPath = parseSourcePath(rootPath);
			URL[] urls = new URL[]{new URL(loadPath)};
			URLClassLoader classLoader = new URLClassLoader(urls, ClassLoader.getSystemClassLoader());

			if (root.isDirectory()) {
				if (Objects.isNull(root.listFiles())) return classes;
				for (File file : root.listFiles()) {
					if (file.isDirectory()) {
						classes.addAll(loadDirClassByPath(rootPath,
								sourcePath + File.separator + file.getName(),
								StringUtils.isBlank(packageName) ? file.getName() : packageName + "." + file.getName()));
					}
					if (file.isFile() && file.getAbsolutePath().endsWith(FileTypeEnum.CLASS.getFileSuffix())) {
						classes.add(classLoader.loadClass(JavaCompilerUtil.parseClassName(file.getAbsolutePath(), packageName)));
					}
				}

			}
		} catch (Exception e) {
			log.error("加载目录下class失败：" + ExceptionUtils.getFullStackTrace(e));
		}

		return classes;
	}

	/**
	 * 2020/8/4 16:16
	 * 加载jar包中的class文件
	 *
	 * @param sourcePath
	 * @author lanhaifeng
	 * @return java.util.Collection<java.lang.Class<?>>
	 */
	public static Collection<Class<?>> loadJarClassByPath(String sourcePath){
		if (StringUtils.isBlank(sourcePath)) {
			throw new RuntimeException("参数非法");
		}
		Set<Class<?>> classes = new LinkedHashSet<>();

		try {
			JarFile jarFile = new JarFile(new File(sourcePath));

			URL url = new URL(parseSourcePath(sourcePath));
			ClassLoader loader = new URLClassLoader(new URL[]{url});

			Enumeration<JarEntry> es = jarFile.entries();
			while (es.hasMoreElements()) {
				JarEntry jarEntry = es.nextElement();
				String name = jarEntry.getName();
				FileTypeEnum fileTypeEnum = FileTypeEnum.getFileEnum(name);
				if(Objects.nonNull(fileTypeEnum)){
					switch (fileTypeEnum){
						case CLASS:
							Class<?> cls = loader.loadClass(
									name.replace("/", ".").substring(0,name.length() - 6));
							classes.add(cls);
							break;
						case JAR:
							classes.addAll(loadJarClassByPath(name));
							break;
						case PROPERTY:
							break;
					}
				}
			}
		} catch (Exception e) {
			log.error("加载jar中class失败：" + ExceptionUtils.getFullStackTrace(e));
		}

		return classes;
	}

	/**
	 * 2020/8/4 14:57
	 * 处理加载路径，使用URLClassLoader加载class文件时，如果路径不是以分隔符结果，Windows环境下将加载失败，linux未测试
	 *
	 * @param sourcePath
	 * @author lanhaifeng
	 * @return java.lang.String
	 */
	private static String parseSourcePath(String sourcePath){
		sourcePath = __LOAD_PRE_PATH + sourcePath;
		if(!sourcePath.endsWith("/") && !sourcePath.endsWith("\\")){
			sourcePath += File.separator;
		}

		return sourcePath;
	}

}
