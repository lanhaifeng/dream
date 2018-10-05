package com.feng.baseframework.util;

import org.springframework.util.ResourceUtils;

import java.io.*;

/**
 * baseframework
 * 2018/9/30 16:48
 * 文件操作工具类
 *
 * @author lanhaifeng
 * @since
 **/
public class FileUtils {
	/**
	 * 将文本文件中的内容读入到buffer中
	 * @param buffer buffer
	 * @param filePath 文件路径
	 * @throws IOException 异常
	 * @author cn.outofmemory
	 * @date 2013-1-7
	 */
	public static void readToBuffer(StringBuffer buffer, String filePath) throws IOException {
		InputStream is = new FileInputStream(filePath);
		//用来保存每行读取的内容
		String line;
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		//读取第一行
		line = reader.readLine();
		//如果line为空说明读完了
		while (line != null) {
			//将读到的内容添加到buffer中
			buffer.append(line);
			//添加换行符
			buffer.append("\n");
			//读取下一行
			line = reader.readLine();
		}
		reader.close();
		is.close();
	}

	/**
	 * 读取文本文件内容
	 * @param filePath 文件所在路径
	 * @return 文本内容
	 * @throws IOException 异常
	 * @author cn.outofmemory
	 * @date 2013-1-7
	 */
	public static String readFile(String filePath) throws IOException {
		StringBuffer sb = new StringBuffer();
		FileUtils.readToBuffer(sb, filePath);
		return sb.toString();
	}

	/**
	 * 2018/9/30 17:23
	 * 获取项目根路径
	 *
	 * @author lanhaifeng
	 * @return
	 */
	public static String getWebRootPath() {
		return getFilePathByRelativePath("");
	}

	/**
	 * 2018/9/30 17:23
	 * 根据相对路径获取文件
	 *
	 * @param relativePath
	 * @author lanhaifeng
	 * @return
	 */
	public static File getFileByRelativePath(String relativePath) {
		try {
			File pathFile = new File(ResourceUtils.getURL("classpath:" + relativePath).getPath());
			if(!pathFile.exists()){
				pathFile = new File(relativePath);
			}
			return pathFile;
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	/**
	 * 2018/9/30 17:23
	 * 根据相对路径获取文件绝对路径
	 *
	 * @param relativePath
	 * @author lanhaifeng
	 * @return
	 */
	public static String getFilePathByRelativePath(String relativePath){
		File pathFile = getFileByRelativePath(relativePath);
		return pathFile == null ? null : pathFile.getPath();
	}

	public static void main(String[] args) throws IOException {
		System.out.println(getWebRootPath());
		File file = ResourceUtils.getFile("classpath:rules/Tp.drl");

		if(file != null && file.exists() && file.isFile()){
			System.out.println(file.getPath());
			System.out.println(readFile(file.getPath()));
		}
	}
}
