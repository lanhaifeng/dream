package com.feng.baseframework.util;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.List;

/**
 * baseframework
 * 2019/3/11 18:03
 * 证书工具类
 *
 * @author lanhaifeng
 * @since
 **/
public class CertificateUtil {

	private static final Log logger = LogFactory.getLog(CertificateUtil.class);

	public static final int DECODE = 0;
	public static final int ENCODE = 1;

	/**
	 * 生成签名
	 * @param keyPath		keystore文件路径
	 * @param aliasName		别名
	 * @param info			签名内容
	 * @param pfxPassword	密码
	 * @return				签名
	 */
	private static String generateSign(String keyPath, String aliasName, String info, String pfxPassword){
		String base64Sign = "";
		InputStream fis = null;
		try {
			fis = new FileInputStream(keyPath);
			KeyStore keyStore = KeyStore.getInstance("JKS");
			char[] pscs = pfxPassword.toCharArray();
			keyStore.load(fis, pscs);
			PrivateKey priKey = (PrivateKey) (keyStore.getKey(aliasName, pscs));
			// 签名
			Signature sign = Signature.getInstance("MD5WithRSA");
			sign.initSign(priKey);
			byte[] bysData = info.getBytes();
			sign.update(bysData);
			byte[] signByte = sign.sign();

			base64Sign = bytesToHexString(signByte);
		} catch (Exception e) {
			logger.error("签名出现错误：" + ExceptionUtils.getFullStackTrace(e));
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error("关闭流出现错误：" + ExceptionUtils.getFullStackTrace(e));
				}
			}
		}
		return base64Sign;
	}

	/**
	 * 对称加密解密 des
	 * @param order  加密解密 密钥
	 * @param type   加密/解密
	 * @param src    待加密/解密字符串
	 * @return
	 * @throws Exception
	 */
	private static byte[] fileDes(String order, Integer type, String src) throws Exception{
		Key key;
		// 初始化DES算法的密钥对象
		KeyGenerator generator = KeyGenerator.getInstance("DES");

		//防止linux下 随机生成key
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
		secureRandom.setSeed(order.getBytes());
		generator.init(56,secureRandom);
		key = generator.generateKey();

		// 创建Cipher对象，ECB模式的DES算法。
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		// 判断是加密还是解密。设置cipher对象为加密或解密。
		if (type.intValue()==ENCODE){
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(src.getBytes());
		}else{
			cipher.init(Cipher.DECRYPT_MODE, key);
			return cipher.doFinal(parseHexStr2Byte(src));
		}
	}

	/**
	 * 十六进制字符串转化为2进制
	 *
	 * @param hexStr
	 * @return byte[]
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1){
			return null;
		}
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/**
	 * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
	 * hexStrToByteArr(String strIn) 互为可逆的转换过程
	 *
	 * @param src 需要转换的byte数组
	 * @return 转换后的字符串
	 * @throws Exception 本方法不处理任何异常，所有异常全部抛出
	 */
	public static String bytesToHexString(byte[] src){
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString().toUpperCase();
	}

	/**
	 * 根据公匙，签名，信息验证信息的合法性（读取数字签名文件 时用）
	 * (capaa-web使用)
	 * @param signed 完整信息
	 * @param info 实际信息
	 * @param keyUrl 公钥路径
	 * @return true 验证成功 false 验证失败
	 */
	private static boolean validateSign(byte[] signed, String info, String keyUrl) {
		// 读取公匙
		PublicKey mypubkey = (PublicKey) getObjFromFile(keyUrl, 1);

		try {
			// 初始一个Signature对象,并用公钥和签名进行验证
			Signature signetcheck = Signature.getInstance("MD5WithRSA"); //DSA
			// 初始化验证签名的公钥
			signetcheck.initVerify(mypubkey);
			// 使用指定的 byte 数组更新要签名或验证的数据
			signetcheck.update(info.getBytes());
			// 验证传入的签名
			return signetcheck.verify(signed);
		} catch (Exception e) {
			logger.error("验证签名出现错误：" + ExceptionUtils.getFullStackTrace(e));
			return false;
		}
	}

	/**
	 * 返回在文件中指定位置的对象
	 *
	 * @param file
	 *            指定的文件
	 * @param i
	 *            从1开始
	 * @return
	 */
	private static Object getObjFromFile(String file, int i) {
		ObjectInputStream ois = null;
		Object obj = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			for (int j = 0; j < i; j++) {
				obj = ois.readObject();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	public static PublicKey getPublicKey() throws Exception {
		String keyStoreFile = FileUtils.getFileByRelativePath("ssl/capaa.keystore").getPath();
		String keyStorePass = "hzmcadminabc";
		KeyStore jks = KeyStore.getInstance("jks");
		String keyAlias = "tomcat";
		PublicKey publicKey;

		FileInputStream fin = new FileInputStream(keyStoreFile);
		jks.load(fin, keyStorePass.toCharArray());

		Certificate cert = jks.getCertificate(keyAlias);
		publicKey = cert.getPublicKey();

		return publicKey;
	}

	public static void exportFileByObjectStream(Object obj, String targetFile){
		ObjectOutputStream pwo = null;
		FileOutputStream fou = null;
		try{
			fou = new FileOutputStream(targetFile);
			pwo = new ObjectOutputStream(fou);
			pwo.writeObject(obj);

		}catch(Exception e){
		    logger.error("输出流到文件失败：" + ExceptionUtils.getFullStackTrace(e));
		}finally {
			try{
				if(pwo != null){
				pwo.close();
			}
			if(fou != null){
				fou.close();
			}
			}catch(Exception e){
			    logger.error("关闭流出现错误：" + ExceptionUtils.getFullStackTrace(e));
			}
		}
	}

	/**
	 * 读取整个license
	 *
	 * @param file
	 *            指定的文件
	 * @return
	 */
	private static List<Object> getAllObjFromFile(String file) {
		ObjectInputStream ois = null;
		List <Object> list=new ArrayList<Object>();
		try {
			FileInputStream fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			while(true){
				list.add(ois.readObject());
			}
		}catch (EOFException ex) { //直到读完为止
			System.out.println("End of file reached.");
		}  catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				//陈腾飞(水言Dade) 2017年3月17日08:59:53 判空
				if (ois != null)
					ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 取license内容(capaa-web使用)
	 * @param fileurl license文件路径
	 * @param order （加密命令要与解密命令一致）
	 * @param keyUrl 公钥路径
	 * @return license信息
	 */
	public static List<String> getLicenseInfo(String fileurl, String order, String keyUrl){
		List <Object> ls=getAllObjFromFile(fileurl); //将license全部读出
		List <String> list=new ArrayList<String>();
		for(int i=0; i<ls.size(); i=i+2){
			String signed=(String) ls.get(i);
			String info=(String) ls.get(i+1);
			// 利用公匙对签名进行验证。
			if (validateSign(parseHexStr2Byte(signed), info, keyUrl)) {

				try {
					byte[] decode = fileDes(order, DECODE, info);
					list.add(new String(decode)+"|"+signed);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}

			} else {//完整性验证出错
				System.out.println("tsettest2:"+signed);
				return null;
			}

		}

		return list;

	}


	public static void main(String[] args) throws Exception {
		PublicKey publicKey = getPublicKey();

		String sign = "1A0F45E41D09D24B129F757B70678B0B0FA09F81F2983F45516FD4374871D91647AE8FF2C7D7854FE3F6D38178B36B9312771C09451547E461E5AEC017F2D8AC";
		String info = "7DAD0C53DD0727C9F01D62665561585F|30|2019-06-08";
		String publicKeyPath = FileUtils.getFileByRelativePath("ssl/public.key").getPath();
		//publicKeyPath = "E:/project/test.key";
		//加密并字节数组转16进制字符串
		info = bytesToHexString(fileDes("capaa", ENCODE, info));
		String privateKeyPath = FileUtils.getFileByRelativePath("ssl/capaa.keystore").getPath();
		String aliasName = "tomcat";
		String pfxPassword = "hzmcadminabc";
		sign = generateSign(privateKeyPath, aliasName, info, pfxPassword);


		System.out.println(validateSign(parseHexStr2Byte(sign), info, publicKeyPath));

		publicKeyPath = FileUtils.getFileByRelativePath("ssl/public.key").getPath();
		List<String> licenses = getLicenseInfo("E:\\project\\226_50_register.license","capaa", "C:\\Users\\feng\\Desktop\\public.key");
		for (int i = 0; i < licenses.size(); i++) {
			System.out.println(licenses.get(i));
		}

		/*String info = "7DAD0C53DD0727C9F01D62665561585F|30|2019-06-08";
		//加密并字节数组转16进制字符串
		info = bytesToHexString(fileDes("capaa", ENCODE, info));
		System.out.println(info);

		String privateKeyPath = FileUtils.getFileByRelativePath("ssl/capaa.keystore").getPath();
		String aliasName = "tomcat";
		String pfxPassword = "hzmcadminabc";
		String sign = generateSign(privateKeyPath, aliasName, info, pfxPassword);
		System.out.println(sign);

		sign = "1A0F45E41D09D24B129F757B70678B0B0FA09F81F2983F45516FD4374871D91647AE8FF2C7D7854FE3F6D38178B36B9312771C09451547E461E5AEC017F2D8AC";
		byte[] signByte = parseHexStr2Byte(sign);
		System.out.println(Arrays.toString(signByte));
		System.out.println(signByte.length);

		String publicKeyPath = FileUtils.getFileByRelativePath("ssl/public.key").getPath();
		System.out.println(validateSign(parseHexStr2Byte(sign), info, publicKeyPath));*/
	}
}
