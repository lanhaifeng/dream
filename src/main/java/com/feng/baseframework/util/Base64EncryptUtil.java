package com.feng.baseframework.util;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * baseframework
 * 2019/2/21 16:40
 * base64编码解码工具类
 *
 * @author lanhaifeng
 * @since
 **/
public class Base64EncryptUtil {

	private static final Logger logger = Logger.getLogger(Base64EncryptUtil.class);

	public static void main(String[] args) {
		String sql = "SELECT T.INST_ID ||T.SID ||T.SERIAL #||T.AUDSID AS SSID FROM GV$SESSION T UNION ALL SELECT T.INST_ID ||T.SID ||T.SERIAL #||TO_CHAR ( T.LOGON_TIME , ? ) AS SSID FROM GV$SESSION T";
		System.out.println(encode(sql.getBytes()));

		sql = "IydTRUxFQ1QgKiBGUk9NIERFUFQgV0hFUkUgREVQVE5PID0gMTAgQU5EIDE0MzMgPSBEQk1TX1BJ\\r\\nUEUuUkVDRUlWRV9NRVNTQUdFICggQ0hSICggNzEgKSB8fENIUiAoIDEwMCApIHx8Q0hSICggNzAg\\r\\nKSAsIDUgKQ==";
		System.out.println(decode(sql));
	}

	/**
	 * 编码
	 *
	 * @param bstr
	 * @return String
	 */
	public static String encode(byte[] bstr) {
		try {
			return new BASE64Encoder().encode(bstr);
		} catch (Exception e) {
			logger.error("编码失败：" + ExceptionUtils.getFullStackTrace(e));
		}
		return "";
	}

	/**
	 * 解码
	 *
	 * @param str
	 * @return string
	 */
	public static String decode(String str) {
		byte[] bt = null;
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			bt = decoder.decodeBuffer(str);
			return new String(bt);
		} catch (IOException e) {
			logger.error("解码失败：" + ExceptionUtils.getFullStackTrace(e));
			return "";
		}

	}
}
