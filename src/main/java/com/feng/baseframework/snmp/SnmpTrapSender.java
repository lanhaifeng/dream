package com.feng.baseframework.snmp;

import com.feng.baseframework.util.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.*;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * baseframework
 * 2020/8/4 19:49
 * snmp trap发送
 *
 * @author lanhaifeng
 * @since
 **/
public class SnmpTrapSender extends AbstractSnmp {

	private static Logger log = LoggerFactory.getLogger(SnmpTrapSender.class);
	private final static String ADDRESS_TEMPLATE = "udp:%s/%s";
	private Snmp snmp = null;

	public void initComm(SnmpAuth snmpAuth) throws IOException {
		TransportMapping<? extends Address> transport = new DefaultUdpTransportMapping();
		snmp = new Snmp(transport);
		if(snmpAuth.getVersion() == SnmpConstants.version3){
			USM usm = snmp.getUSM();
			if(Objects.isNull(usm)){
				usm = new USM(SecurityProtocols.getInstance(), new OctetString(MPv3.createLocalEngineID()), 0);
			}
			SecurityModels.getInstance().addSecurityModel(usm);
			usm.addUser(buildUsmUser(SecurityLevel.get(snmpAuth.getSecurityLevel()),
					snmpAuth.getUserName(), snmpAuth.getPassAuth(), snmpAuth.getPrivatePass()));
		}
		transport.listen();
	}

	/**
	 * 2020/8/5 16:37
	 * 构建目标地址
	 *
	 * @param targetIp
	 * @param targetPort
	 * @author lanhaifeng
	 * @return org.snmp4j.smi.Address
	 */
	public Address buildAddress(String targetIp, int targetPort){
		return GenericAddress.parse(String.format(ADDRESS_TEMPLATE, targetIp, String.valueOf(targetPort)));
	}


	/**
	 * 2020/8/5 16:32
	 * 构建target
	 *
	 * @param snmpAuth
	 * @param targetAddress
	 * @author lanhaifeng
	 * @return org.snmp4j.CommunityTarget
	 */
	public Target buildCommunityTarget(SnmpAuth snmpAuth, Address targetAddress) {
		// 设置 target
		Target target = null;

		int version = snmpAuth.getVersion();
		//版本1和2c基于团体名验证
		if(version == SnmpConstants.version1 || version == SnmpConstants.version2c){
			// 目标地址和团体名
			target = new CommunityTarget(targetAddress, new OctetString(snmpAuth.getCommunity()));
		}
		if(version == SnmpConstants.version3){
			target = new UserTarget();
			if(snmpAuth.getSecurityLevel() == SecurityLevel.AUTH_NOPRIV
					|| snmpAuth.getSecurityLevel() == SecurityLevel.AUTH_PRIV){
				target.setSecurityName(new OctetString(snmpAuth.getUserName()));
			}
			target.setSecurityLevel(snmpAuth.getSecurityLevel());
			target.setSecurityModel(snmpAuth.getSecurityModel());
			//设置目标地址
			target.setAddress(targetAddress);
		}

		// 通信不成功时的重试次数
		target.setRetries(2);
		// 超时时间
		target.setTimeout(1500);
		// snmp版本
		target.setVersion(version);
		return target;
	}

	/**
	 * 2020/8/5 19:33
	 * 解析返回结果
	 *
	 * @param respEvnt
	 * @author lanhaifeng
	 * @return void
	 */
	public void parseResponse(ResponseEvent respEvnt) {
		if (respEvnt != null && respEvnt.getResponse() != null) {
			List<? extends VariableBinding> recVBs = respEvnt.getResponse()
					.getVariableBindings();
			for (int i = 0; i < recVBs.size(); i++) {
				VariableBinding recVB = recVBs.get(i);
				log.info(recVB.getOid() + " : " + recVB.getVariable());
			}
		}
	}

	/**
	 * 向管理进程发送Trap 1报文
	 *
	 * @param snmpAuth
	 * @param ip
	 * @param port
	 * @author lanhaifeng
	 * @return void
	 */
	public void sendPDU(String ip, int port, SnmpAuth snmpAuth) throws IOException {
		Address address = buildAddress(ip, port);
		int version = snmpAuth.getVersion();
		Target target = buildCommunityTarget(snmpAuth, address);

		// 创建 PDU
		PDU pdu = null;
		if(version == SnmpConstants.version1){
			pdu = new PDUv1();
		}
		if(version == SnmpConstants.version2c){
			pdu = new PDU();
			pdu.setType(PDU.TRAP);
		}
		if(version == SnmpConstants.version3){
			pdu = new ScopedPDU();
			pdu.setType(PDU.NOTIFICATION);
		}
		//构造数据
		pdu.addAll(buildSendData());

		// 向Agent发送PDU，并接收Response
		ResponseEvent respEvnt = snmp.send(pdu, target);

		// 解析Response
		parseResponse(respEvnt);
	}

	/**
	 * 2020/8/5 19:37
	 * 构建发送数据
	 *
	 * @param
	 * @author lanhaifeng
	 * @return java.util.List<org.snmp4j.smi.VariableBinding>
	 */
	public List<VariableBinding> buildSendData() {
		List<VariableBinding> datas = new ArrayList<>();
		try {
			String sql = FileUtils.readFile(FileUtils.getFile("classpath:sql/snmpTest.sql").getAbsolutePath());
			OctetString sqlText = new OctetString(sql);

			datas.add(new VariableBinding(new OID(".1.3.6.1.6.3.1.1.4.6.1.0"), sqlText));
			datas.add(new VariableBinding(new OID(".1.3.6.1.6.3.1.1.4.6.1.0"), new OctetString("SnmpTrap2")));
		} catch (Exception e) {
			log.error("构建发送数据失败，错误：" + ExceptionUtils.getFullStackTrace(e));
		}

		return datas;
	}

	public static void testSendTrap(String ip, int port, int version, String community,
									 Integer securityLevel, Integer securityModel,
									 String userName, String passAuth, String prviatePass) throws IOException {
		SnmpAuth snmpAuth = new SnmpAuth();
		snmpAuth.withVersion(version).withCommunity(community);
		SnmpTrapSender sender = new SnmpTrapSender();
		if(version == SnmpConstants.version3){
			snmpAuth.withSecurityLevel(securityLevel).withSecurityModel(securityModel)
					.withUserName(userName).withPassAuth(passAuth).withPrivatePass(prviatePass);
		}
		sender.initComm(snmpAuth);

		sender.sendPDU(ip, port, snmpAuth);
	}

	public static void main(String[] args) {
		try {
			String ip = "192.168.230.206";
			int port = 162;
			String community = "hzmc+Ra2$yuL";
			int securityLevel = SecurityLevel.AUTH_PRIV;
			int securityModel = SecurityModel.SECURITY_MODEL_USM;
			String userName = "test";
			String passAuth = "098f6bcd4621d373cade4e832627b4f6";
			String prviatePass = "PzMY6G9gKK2N52wfH7aANg==";

			//test trap1
			//ip = "192.168.61.47";
			testSendTrap(ip, port, SnmpConstants.version1, community, null, null, null, null, null);
			//test trap2c
			testSendTrap(ip, port, SnmpConstants.version2c, community, null, null, null, null, null);
			//test trap3
			testSendTrap(ip, port, SnmpConstants.version3, community, securityLevel, securityModel, userName, passAuth, prviatePass);
		} catch (Exception e) {
			log.error("发送trap失败，错误：" + ExceptionUtils.getFullStackTrace(e));
		}
	}
}
